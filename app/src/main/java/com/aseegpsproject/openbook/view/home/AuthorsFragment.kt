package com.aseegpsproject.openbook.view.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aseegpsproject.openbook.databinding.FragmentAuthorsBinding

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

        setUpSearchView()
        setUpRecyclerView()
        subscribeUI()
    }

    private fun subscribeUI() {
        homeViewModel.user.observe(viewLifecycleOwner) {
            viewModel.user = it
        }
        viewModel.toast.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }
        viewModel.spinner.observe(viewLifecycleOwner) { value ->
            value.let { show ->
                binding.rvAuthorList.visibility = if (!show) View.VISIBLE else View.GONE
                binding.authorSpinner.visibility = if (show) View.VISIBLE else View.GONE
            }
        }
        viewModel.authors.observe(viewLifecycleOwner) { authors ->
            if (viewModel.isSearch) adapter.updateData(authors.filter { it.enabled })
        }
        viewModel.favAuthors.observe(viewLifecycleOwner) { favAuthors ->
            if (!viewModel.isSearch) adapter.updateData(favAuthors.authors)
        }
    }

    private fun setUpSearchView() {
        with(binding) {
            authorsSearchView.isIconified = false
            authorsSearchView.clearFocus()
            authorsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    handleSearch(query ?: "")
                    viewModel.isSearch = true
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

                fun handleSearch(query: String) {
                    if (query.isNotEmpty()) {
                        viewModel.searchAuthors(query)
                    }
                }
            })
            authorsSearchView.setOnCloseListener {
                viewModel.isSearch = false
                viewModel.reloadAuthors()
                viewModel.favAuthors.value?.let { adapter.updateData(it.authors) }
                (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(
                    requireView().windowToken,
                    0
                )
                authorsSearchView.clearFocus()
                true
            }
        }
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