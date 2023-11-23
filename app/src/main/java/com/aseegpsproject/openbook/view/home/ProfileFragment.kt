package com.aseegpsproject.openbook.view.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.data.model.Worklist
import com.aseegpsproject.openbook.database.OpenBookDatabase
import com.aseegpsproject.openbook.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProfileAdapter
    private lateinit var listener: OnWorklistClickListener
    private lateinit var db: OpenBookDatabase
    private lateinit var user: User

    private var _worklists = listOf<Worklist>()

    interface OnWorklistClickListener {
        fun onWorklistClick(workList: Worklist)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnWorklistClickListener) {
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
        _binding =
            FragmentProfileBinding.inflate(inflater, container, false)
        db = OpenBookDatabase.getInstance(requireContext())!!
        user = activity?.intent?.getSerializableExtra(HomeActivity.USER_INFO) as User
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        if (_worklists.isEmpty()) {
            loadWorkLists()
        }

        with(binding) {
            tvUsername.text = user.username
            cvCreateWorklist.visibility = View.GONE
        }

        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            btnAddWorklist.setOnClickListener {
                cvCreateWorklist.visibility = View.VISIBLE
                etWorklistName.requestFocus()
                val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(binding.etWorklistName, InputMethodManager.SHOW_IMPLICIT)
            }
            btnCreateWorklist.setOnClickListener {
                createWorklist()
                lifecycleScope.launch(Dispatchers.IO) {
                    loadWorkLists()
                }
            }
            etWorklistName.setOnEditorActionListener { _, _, _ ->
                createWorklist()
                lifecycleScope.launch(Dispatchers.IO) {
                    loadWorkLists()
                }
                true
            }
        }
    }

    private fun createWorklist() {
        with(binding) {
            lifecycleScope.launch(Dispatchers.IO) {
                val worklistName = etWorklistName.text.toString()
                val worklist = Worklist(null, worklistName, listOf())

                val worklistId = db.worklistDao().insert(worklist)
                worklist.worklistId = worklistId

                db.worklistDao().insertAndRelate(worklist, user.userId!!)
            }
            (requireContext().getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager?)?.hideSoftInputFromWindow(requireView().windowToken, 0)
            cvCreateWorklist.visibility = View.GONE
            etWorklistName.text.clear()
        }
        Toast.makeText(context, R.string.worklist_created, Toast.LENGTH_SHORT).show()
    }

    private fun loadWorkLists() {
        lifecycleScope.launch {
            _worklists = user.userId?.let { db.worklistDao().getUserWithWorkLists(it).worklists }!!
            withContext(Dispatchers.Main) {
                adapter.updateData(_worklists)
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = ProfileAdapter(
            _worklists,
            { workList -> listener.onWorklistClick(workList) },
            { workList -> deleteWorklist(workList)},
            context
        )
        with (binding) {
            rvWorklistList.layoutManager = GridLayoutManager(context, 3)
            rvWorklistList.adapter = adapter
        }
    }

    private fun deleteWorklist(workList: Worklist) {
        lifecycleScope.launch(Dispatchers.IO) {
            db.worklistDao().delete(workList)
            loadWorkLists()
        }
        Toast.makeText(context, R.string.worklist_deleted, Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}