package com.aseegpsproject.openbook.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.data.model.WorkList
import com.aseegpsproject.openbook.database.OpenBookDatabase
import com.aseegpsproject.openbook.databinding.FragmentWorklistBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WorklistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorklistFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val args: WorklistFragmentArgs by navArgs()
    private var _binding: FragmentWorklistBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: WorklistAdapter
    private lateinit var listener: DiscoverFragment.OnWorkClickListener
    private lateinit var db: OpenBookDatabase
    private lateinit var user: User

    private var _workList: WorkList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        if (context is DiscoverFragment.OnWorkClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnWorkClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorklistBinding.inflate(inflater, container, false)
        db = OpenBookDatabase.getInstance(requireContext())!!
        user = activity?.intent?.getSerializableExtra(HomeActivity.USER_INFO) as User
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _workList = args.worklist
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        adapter = _workList?.let {
            WorklistAdapter(
                it.works.ifEmpty { listOf() },
                { work -> listener.onWorkClick(work) },
                { work -> removeWorkFromWorklist(work) },
                requireContext()
            )
        }!!
        with(binding) {
            rvWorklistList.layoutManager = LinearLayoutManager(context)
            rvWorklistList.adapter = adapter
        }
    }

    private fun removeWorkFromWorklist(work: Work) {
        var works = _workList!!.works
        works = works.filter { it.workKey != work.workKey }
        _workList!!.works = works
        lifecycleScope.launch(Dispatchers.IO) {
            db.workListDao().updateAndRelate(_workList!!, user.userId!!)
        }
        adapter.updateData(works)
        Toast.makeText(requireContext(), R.string.removed_from_worklist, Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WorkListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorklistFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}