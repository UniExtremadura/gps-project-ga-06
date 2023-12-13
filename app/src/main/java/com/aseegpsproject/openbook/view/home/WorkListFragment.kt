package com.aseegpsproject.openbook.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.aseegpsproject.openbook.databinding.FragmentWorkListBinding

class WorkListFragment : Fragment() {
    private val args: WorkListFragmentArgs by navArgs()
    private var _binding: FragmentWorkListBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val viewModel: WorkListViewModel by viewModels { WorkListViewModel.Factory }
    private lateinit var adapter: WorkListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.workList = args.workList
        viewModel.toast.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }

        subscribeUI()
        setUpRecyclerView()
    }

    private fun subscribeUI() {
        viewModel.works.observe(viewLifecycleOwner) { workList ->
            adapter.updateData(workList.works)
        }
    }

    private fun setUpRecyclerView() {
        adapter = WorkListAdapter(
            listOf(),
            { work -> homeViewModel.onWorkClick(work) },
            { work -> viewModel.removeWorkFromWorklist(work) },
            requireContext()
        )
        with(binding) {
            rvWorklistList.layoutManager = LinearLayoutManager(context)
            rvWorklistList.adapter = adapter
        }
    }
}