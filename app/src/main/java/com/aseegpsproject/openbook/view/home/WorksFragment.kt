package com.aseegpsproject.openbook.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aseegpsproject.openbook.databinding.FragmentWorksBinding

class WorksFragment : Fragment() {
    private var _binding: FragmentWorksBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WorksViewModel by viewModels { WorksViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var adapter: WorksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWorksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }

        viewModel.toast.observe(viewLifecycleOwner) { text ->
            text?.let {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }

        setUpRecyclerView()
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: WorksAdapter) {
        viewModel.worksInLibrary.observe(viewLifecycleOwner) { worksInLibrary ->
            adapter.updateData(worksInLibrary.works)
        }
    }

    private fun setUpRecyclerView() {
        adapter = WorksAdapter(
            emptyList(),
            { work -> homeViewModel.onWorkClick(work) },
            { work -> viewModel.changeFavoriteWork(work) },
            requireContext()
        )
        with(binding) {
            rvBooksList.layoutManager = LinearLayoutManager(context)
            rvBooksList.adapter = adapter
        }
    }
}