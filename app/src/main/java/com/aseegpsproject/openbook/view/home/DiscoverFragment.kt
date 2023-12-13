package com.aseegpsproject.openbook.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aseegpsproject.openbook.api.APIError
import com.aseegpsproject.openbook.api.getNetworkService
import com.aseegpsproject.openbook.data.apimodel.Doc
import com.aseegpsproject.openbook.data.apimodel.TrendingWork
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.data.toWork
import com.aseegpsproject.openbook.databinding.FragmentDiscoverBinding
import kotlinx.coroutines.launch

class DiscoverFragment : Fragment() {
    private val viewModel: DiscoverViewModel by viewModels { DiscoverViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var trendingFreq: String
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpSearchView()

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }

        viewModel.spinner.observe(viewLifecycleOwner) { value ->
            binding.spinner.visibility = if (value) View.VISIBLE else View.GONE
        }

        viewModel.toast.observe(viewLifecycleOwner) { text ->
            text?.let {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }

        subscribeUI(adapter)
    }

    private fun subscribeUI(adapter: DiscoverAdapter) {
        viewModel.works.observe(viewLifecycleOwner) { works ->
            adapter.updateData(works.filter { it.isDiscover })
        }
    }

    private fun setUpSearchView() {
        binding.discoverSearchView.setOnClickListener {
            binding.discoverSearchView.isIconified = false
        }
        binding.discoverSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handleSearch(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() == true) {
                    viewModel.refreshWorks()
                }
                return true
            }
        })
    }

    fun handleSearch(query: String) {
        lifecycleScope.launch {
            showLoading()
            runCatching {
                if (query.isNotEmpty()) {
                    fetchSearchBooksByTitle(query).map { it.toWork() }
                } else {
                    fetchTrendingBooks().map { it.toWork() }
                }
            }.onSuccess { works ->
                adapter.updateData(works)
            }.onFailure { cause ->
                Log.e("DiscoverFragment", "Error fetching data", cause)
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
            }
            hideLoading()
        }
    }

    private fun showLoading() {
        binding.rvBookList.visibility = View.GONE
        binding.spinner.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.rvBookList.scrollToPosition(0)
        binding.spinner.visibility = View.GONE
        binding.rvBookList.visibility = View.VISIBLE
    }

    private suspend fun fetchSearchBooksByTitle(title: String): List<Doc> {
        val searchWorks: List<Doc>
        try {
            searchWorks = getNetworkService().getSearchBooksByTitle(title, 1).docs
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
        return searchWorks
    }

    private suspend fun fetchTrendingBooks(): List<TrendingWork> {
        var trendingWorks: List<TrendingWork> = listOf()
        try {
            trendingWorks = getNetworkService().getDailyTrendingBooks(trendingFreq).trendingWorks
        } catch (cause: Throwable) {
            Log.e("DiscoverFragment", "Error fetching data", cause)
        }
        return trendingWorks
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