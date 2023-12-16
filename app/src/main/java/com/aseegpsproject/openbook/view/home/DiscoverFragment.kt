package com.aseegpsproject.openbook.view.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aseegpsproject.openbook.databinding.FragmentDiscoverBinding

class DiscoverFragment : Fragment() {
    private lateinit var preferences: SharedPreferences
    private val viewModel: DiscoverViewModel by viewModels { DiscoverViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DiscoverAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val trendingFreq = preferences.getString("trending", "daily") ?: "daily"
        viewModel.setTrendingFreq(trendingFreq)
        viewModel.refreshWorks()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpSearchView()
        subscribeUI(adapter)
    }

    private fun subscribeUI(adapter: DiscoverAdapter) {
        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }

        viewModel.spinner.observe(viewLifecycleOwner) { value ->
            binding.rvBookList.visibility = if (value) View.GONE else View.VISIBLE
            binding.spinner.visibility = if (value) View.VISIBLE else View.GONE
        }

        viewModel.toast.observe(viewLifecycleOwner) { text ->
            text?.let {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }

        viewModel.works.observe(viewLifecycleOwner) { works ->
            adapter.updateData(works.filter { it.enabled })
        }
    }

    private fun setUpSearchView() {
        with(binding) {
            discoverSearchView.isIconified = false
            discoverSearchView.clearFocus()
            discoverSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    handleSearch(query ?: "")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

                fun handleSearch(query: String) {
                    if (query.isNotEmpty()) {
                        viewModel.searchWorks(query)
                    }
                }
            })
            discoverSearchView.setOnCloseListener {
                viewModel.refreshWorks(true)
                (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(
                    requireView().windowToken,
                    0
                )
                discoverSearchView.clearFocus()
                true
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = DiscoverAdapter(
            works = emptyList(),
            onClick = {
                homeViewModel.onWorkClick(it)
            },
            onLongClick = {
                viewModel.changeFavoriteWork(it)
            },
            context = context
        )
        with(binding) {
            rvBookList.layoutManager = LinearLayoutManager(context)
            rvBookList.adapter = adapter
        }
        Log.d("DiscoverFragment", "setUpRecyclerView")
    }
}