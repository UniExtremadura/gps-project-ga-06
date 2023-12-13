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
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.api.getNetworkService
import com.aseegpsproject.openbook.data.Repository
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.data.model.WorkList
import com.aseegpsproject.openbook.database.OpenBookDatabase
import com.aseegpsproject.openbook.databinding.FragmentWorkDetailBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkDetailFragment : Fragment() {

    private val args: WorkDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentWorkDetailBinding
    private lateinit var adapter: ProfileAdapter
    private lateinit var listener: ProfileFragment.OnWorklistClickListener
    private lateinit var work: Work
    private lateinit var db: OpenBookDatabase
    private lateinit var user: User
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        db = OpenBookDatabase.getInstance(requireContext())!!
        repository = Repository.getInstance(
            db.workDao(),
            getNetworkService()
        )
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
        user = activity?.intent?.getSerializableExtra(HomeActivity.USER_INFO) as User
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWorkDetailBinding.bind(view)
        work = args.work

        setUpUI()
        setUpListeners()
    }

    private fun setUpUI() {
        with (binding) {
            lifecycleScope.launch {
                val _work = repository.fetchWorkDetails(work)
                workTitle.text = _work.title
                workAuthor.text = _work.authorNames?.get(0)
                Glide.with(requireContext())
                    .load(_work.coverPaths.get(0))
                    .into(workCover)
                workDescription.text = _work.description
                workRating.text = _work.rating

                btnFavorite.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        // Eliminar el observador después de que la vista esté lista para evitar llamadas múltiples
                        btnFavorite.viewTreeObserver.removeOnGlobalLayoutListener(this)

                        if (work.isFavorite) {
                            btnFavorite.compoundDrawables[0].setTint(resources.getColor(R.color.yellow))
                        }
                    }
                })
            }
        }
    }

    private fun setUpListeners() {
        with(binding) {
            btnFavorite.setOnClickListener {
                changeFavoriteWork(work)
            }
            btnAddToWorklist.setOnClickListener {
                lifecycleScope.launch {
                    val worklists = db.workListDao().getUserWithWorkLists(user.userId!!).workLists
                    if (worklists.isEmpty()) {
                        Toast.makeText(requireContext(), resources.getText(R.string.no_workLists), Toast.LENGTH_SHORT).show()
                    } else {
                        rvWorklistList.visibility = View.VISIBLE
                        setUpRecyclerView(worklists)
                    }
                }
            }
        }
    }

    private fun changeFavoriteWork(work: Work) {
        lifecycleScope.launch {
            if (work.isFavorite) {
                work.isFavorite = false
                repository.deleteWorkFromLibrary(work, user.userId!!)
                Toast.makeText(context, R.string.remove_fav, Toast.LENGTH_SHORT).show()
            } else {
                work.isFavorite = true
                repository.workToLibrary(work, user.userId!!)
                Toast.makeText(context, R.string.add_fav, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpRecyclerView(workLists: List<WorkList>) {
        adapter = ProfileAdapter(
            workLists,
            { worklist -> addToWorklist(worklist) },
            { worklist -> listener.onWorklistClick(worklist) },
            context
        )
        with (binding) {
            rvWorklistList.layoutManager = GridLayoutManager(context, 3)
            rvWorklistList.adapter = adapter
        }
    }

    private fun addToWorklist(worklist: WorkList) {
        val workKeys = worklist.works.map { it.workKey }.toSet()
        if (workKeys.contains(work.workKey)) {
            Toast.makeText(requireContext(), resources.getText(R.string.already_in_worklist), Toast.LENGTH_SHORT).show()
        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                worklist.works = worklist.works + work
                db.workListDao().insertAndRelate(worklist, user.userId!!)
            }
            Toast.makeText(
                requireContext(),
                resources.getText(R.string.added_to_worklist),
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.rvWorklistList.visibility = View.GONE
    }
}