package com.aseegpsproject.openbook.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.api.getNetworkService
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.data.model.Worklist
import com.aseegpsproject.openbook.data.toStr
import com.aseegpsproject.openbook.database.OpenBookDatabase
import com.aseegpsproject.openbook.databinding.FragmentWorkDetailBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WorkDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorkDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val args: WorkDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentWorkDetailBinding
    private lateinit var adapter: ProfileAdapter
    private lateinit var listener: ProfileFragment.OnWorklistClickListener
    private lateinit var work: Work
    private lateinit var db: OpenBookDatabase
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        if (context is ProfileFragment.OnWorklistClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnWorkClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWorkDetailBinding.inflate(inflater, container, false)
        db = OpenBookDatabase.getInstance(requireContext())!!
        user = activity?.intent?.getSerializableExtra(HomeActivity.USER_INFO) as User
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWorkDetailBinding.bind(view)
        work = args.work

        with (binding) {
            workTitle.text = work.title
            workAuthor.text = work.authorNames?.get(0)
            Glide.with(requireContext())
                .load(work.coverPaths.get(0))
                .into(workCover)
            lifecycleScope.launch {
                workRating.text = getNetworkService().getWorkRatings(work.workKey).toStr()
                workDescription.text = getNetworkService().getWorkInfo(work.workKey).description
            }

            btnFavorite.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    // Eliminar el observador después de que la vista esté lista para evitar llamadas múltiples
                    btnFavorite.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    if (work.isFavorite) {
                        btnFavorite.compoundDrawables[0].setTint(resources.getColor(R.color.yellow))
                    }
                }
            })

            // Check if work in worklist
            lifecycleScope.launch {
                val worklists = db.worklistDao().getUserWithWorkLists(user.userId!!).worklists
                if (worklists.isEmpty()) {
                    btnAddToWorklist.visibility = View.GONE
                } else {
                    val workKeys = worklists.map { it.works.map { it.workKey } }.flatten().toSet()
                    if (workKeys.contains(work.workKey)) {
                        btnAddToWorklist.visibility = View.GONE
                    }
                }
            }
        }

        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            btnFavorite.setOnClickListener {
                work.isFavorite = !work.isFavorite
                if (work.isFavorite) {
                    btnFavorite.compoundDrawables[0].setTint(resources.getColor(R.color.yellow))
                    lifecycleScope.launch {
                        db.workDao().insertAndRelate(work, user.userId!!)
                    }
                    Toast.makeText(requireContext(), resources.getText(R.string.add_fav), Toast.LENGTH_SHORT).show()
                } else {
                    btnFavorite.compoundDrawables[0].setTint(resources.getColor(R.color.white))
                    lifecycleScope.launch {
                        db.workDao().delete(work)
                    }
                    Toast.makeText(requireContext(), resources.getText(R.string.remove_fav), Toast.LENGTH_SHORT).show()
                }
            }
            btnAddToWorklist.setOnClickListener {
                lifecycleScope.launch {
                    val worklists = db.worklistDao().getUserWithWorkLists(user.userId!!).worklists
                    if (worklists.isEmpty()) {
                        Toast.makeText(requireContext(), resources.getText(R.string.no_worklists), Toast.LENGTH_SHORT).show()
                    } else {
                        rvWorklistList.visibility = View.VISIBLE
                        setUpRecyclerView(worklists)
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView(worklists: List<Worklist>) {
        adapter = ProfileAdapter(
            worklists,
            { worklist -> addToWorklist(worklist) },
            { worklist -> listener.onWorklistClick(worklist) },
            context
        )
        with (binding) {
            rvWorklistList.layoutManager = GridLayoutManager(context, 3)
            rvWorklistList.adapter = adapter
        }
    }

    private fun addToWorklist(worklist: Worklist) {
        val workKeys = worklist.works.map { it.workKey }.toSet()
        if (workKeys.contains(work.workKey)) {
            Toast.makeText(requireContext(), resources.getText(R.string.already_in_worklist), Toast.LENGTH_SHORT).show()
        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                worklist.works = worklist.works + work
                db.worklistDao().insertAndRelate(worklist, user.userId!!)
            }
            Toast.makeText(
                requireContext(),
                resources.getText(R.string.added_to_worklist),
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.rvWorklistList.visibility = View.GONE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WorkDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}