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
import com.aseegpsproject.openbook.data.checkPhotoPath
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.data.toAuthor
import com.aseegpsproject.openbook.databinding.FragmentAuthorsBinding
import kotlinx.coroutines.launch

class AuthorsFragment : Fragment() {
    private var _binding: FragmentAuthorsBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val viewModel: AuthorsViewModel by viewModels { AuthorsViewModel.Factory }
    private lateinit var adapter: AuthorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpSearchView()

        homeViewModel.user.observe(viewLifecycleOwner) {
            viewModel.user = it
        }

        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.authors.observe(viewLifecycleOwner) { authors ->
            adapter.updateData(authors.authors)
        }
    }

    private fun setUpSearchView() {
        binding.authorsSearchView.setOnClickListener {
            binding.authorsSearchView.isIconified = false
        }
        binding.authorsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handleSearch(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() == true) {
                    viewModel.loadFavoriteAuthors()
                }
                return true
            }
        })
    }

    private fun handleSearch(query: String) {
        lifecycleScope.launch {
            binding.rvAuthorList.visibility = View.GONE
            binding.authorSpinner.visibility = View.VISIBLE
            runCatching {
                if (query.isNotEmpty()) {
                    val authors = fetchSearchAuthorsByName(query)
                    adapter.updateData(authors)
                }
            }.onFailure { cause ->
                Log.e("AuthorsFragment", "Error fetching data", cause)
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
            }
            binding.rvAuthorList.visibility = View.VISIBLE
            binding.authorSpinner.visibility = View.GONE
        }
    }

    private suspend fun fetchSearchAuthorsByName(name: String): List<Author> {
        val searchAuthors: List<Author>
        try {
            searchAuthors =
                getNetworkService().getSearchAuthorsByName(name, 1).docs
                    .map { it.toAuthor() }
                    .filter { it.birthDate != null }
                    .filter { it.numWorks != 0 }
                    .filter { it.checkPhotoPath() }
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
        return searchAuthors
    }

    private fun setUpRecyclerView() {
        adapter = AuthorsAdapter(
            authors = emptyList(),
            onClick = {
                homeViewModel.onAuthorClick(it)
            },
            onLongClick = {
                viewModel.changeFavoriteAuthor(it)
            },
            context = context
        )
        with(binding) {
            rvAuthorList.layoutManager = LinearLayoutManager(context)
            rvAuthorList.adapter = adapter
        }
    }
}