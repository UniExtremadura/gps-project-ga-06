package com.aseegpsproject.openbook.view.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.aseegpsproject.openbook.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val viewModel: ProfileViewModel by viewModels { ProfileViewModel.Factory }
    private lateinit var adapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
            binding.tvUsername.text = user.username
        }
        viewModel.toast.observe(viewLifecycleOwner) { text ->
            text?.let {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }


        subscribeUI()
        setUpListeners()
    }

    private fun subscribeUI() {
        viewModel.workLists.observe(viewLifecycleOwner) { workLists ->
            workLists?.let { adapter.updateData(workLists.workLists) }
        }
    }

    private fun setUpListeners() {
        with(binding) {
            btnAddWorklist.setOnClickListener {
                cvCreateWorklist.visibility = View.VISIBLE
                etWorklistName.requestFocus()
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(
                    binding.etWorklistName,
                    InputMethodManager.SHOW_IMPLICIT
                )
            }
            btnCreateWorklist.setOnClickListener {
                viewModel.createWorklist(etWorklistName.text.toString())
                (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(
                    requireView().windowToken,
                    0
                )
                cvCreateWorklist.visibility = View.GONE
                etWorklistName.text.clear()
            }
            etWorklistName.setOnEditorActionListener { _, _, _ ->
                btnCreateWorklist.performClick()
                true
            }
            btnSettings.setOnClickListener {
                homeViewModel.onSettingsClick()
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = ProfileAdapter(
            emptyList(),
            { workList -> homeViewModel.onWorkListClick(workList) },
            { workList -> viewModel.deleteWorklist(workList) },
            context
        )
        with(binding) {
            rvWorklistList.layoutManager = GridLayoutManager(context, 3)
            rvWorklistList.adapter = adapter
        }
    }
}