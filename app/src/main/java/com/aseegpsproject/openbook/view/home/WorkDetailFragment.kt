package com.aseegpsproject.openbook.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.databinding.FragmentWorkDetailBinding
import com.bumptech.glide.Glide

class WorkDetailFragment : Fragment() {
    private val args: WorkDetailFragmentArgs by navArgs()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val viewModel: WorkDetailViewModel by viewModels { WorkDetailViewModel.Factory }
    private lateinit var binding: FragmentWorkDetailBinding
    private lateinit var adapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWorkDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWorkDetailBinding.bind(view)

        viewModel.work = args.work
        homeViewModel.user.observe(viewLifecycleOwner) {
            viewModel.user = it
        }
        viewModel.toast.observe(viewLifecycleOwner) { text ->
            text?.let {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }
        viewModel.workListDialog.observe(viewLifecycleOwner) { show ->
            if (show) {
                binding.rvWorklistList.visibility = View.VISIBLE
            } else {
                binding.rvWorklistList.visibility = View.GONE
            }
        }

        subscribeUI()
        setUpListeners()
        setUpRecyclerView()
    }

    private fun subscribeUI() {
        viewModel.workListsInLibrary.observe(viewLifecycleOwner) { userWithWorkLists ->
            adapter.updateData(userWithWorkLists.workLists)
        }
        viewModel.workDetail.observe(viewLifecycleOwner) { work ->
            work?.let { setUpBinding(work) }
        }
    }

    private fun setUpBinding(work: Work) {
        with (binding) {
            workTitle.text = work.title
            workAuthor.text = work.authorNames?.get(0)
            Glide.with(requireContext())
                .load(work.coverPaths[0])
                .into(workCover)
            workDescription.text = work.description
            workRating.text = work.rating

            binding.workDetailSpinner.visibility = View.GONE
            binding.workDetails.visibility = View.VISIBLE
            binding.btnAddToWorklist.visibility = View.VISIBLE
            binding.btnFavorite.visibility = View.VISIBLE
        }
    }

    private fun setUpListeners() {
        with(binding) {
            btnFavorite.setOnClickListener {
                viewModel.changeFavoriteWork()
            }
            btnAddToWorklist.setOnClickListener {
                viewModel.showWorkListDialog()
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = ProfileAdapter(
            emptyList(),
            { worklist -> viewModel.addToWorklist(worklist) },
            { worklist -> homeViewModel.onWorkListClick(worklist) },
            context
        )
        with (binding) {
            rvWorklistList.layoutManager = GridLayoutManager(context, 3)
            rvWorklistList.adapter = adapter
        }
    }
}