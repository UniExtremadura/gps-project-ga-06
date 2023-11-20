package com.aseegpsproject.openbook.view.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aseegpsproject.openbook.api.APIError
import com.aseegpsproject.openbook.api.getNetworkService
import com.aseegpsproject.openbook.data.apimodel.Doc
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.data.toAuthor
import com.aseegpsproject.openbook.data.toWork
import com.aseegpsproject.openbook.database.OpenBookDatabase
import com.aseegpsproject.openbook.databinding.FragmentAuthorsBinding
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AuthorsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AuthorsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentAuthorsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AuthorsAdapter
    private lateinit var listener: OnAuthorClickListener

    private var _authors = listOf<Author>()
    private var favAuthors = listOf<Author>()
    private lateinit var db: OpenBookDatabase

    interface OnAuthorClickListener {
        fun onAuthorClick(author: Author)
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
        if (context is OnAuthorClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnBookClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorsBinding.inflate(inflater, container, false)
        db = OpenBookDatabase.getInstance(requireContext())!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        lifecycleScope.launch {
            if (favAuthors.isEmpty()) {
                loadFavoriteAuthors()
            }

            adapter.updateData(favAuthors)
        }

        setUpSearchView()
    }

    private fun setUpSearchView() {
        binding.authorSearchView.setOnClickListener { binding.authorSearchView.isIconified = false }
        binding.authorSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    lifecycleScope.launch {
                        if (query.isNotEmpty()) {
                            binding.rvAuthorList.visibility = View.GONE
                            binding.authorSpinner.visibility = View.VISIBLE
                            try {
                                val searchAuthors = fetchSearchAuthorsByName(query)
                                _authors = searchAuthors.map { it.toAuthor() }
                                adapter.updateData(_authors)
                            } catch (cause: Throwable) {
                                Log.e("AuthorsFragment", "Error fetching data", cause)
                                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT)
                                    .show()
                            } finally {
                                binding.rvAuthorList.scrollToPosition(0)
                                binding.authorSpinner.visibility = View.GONE
                                binding.rvAuthorList.visibility = View.VISIBLE
                            }
                        } else {
                            lifecycleScope.launch {
                                loadFavoriteAuthors()
                            }
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isEmpty()) {
                        loadFavoriteAuthors()
                    }
                }
                return false
            }
        })
    }

    private suspend fun fetchSearchAuthorsByName(name: String): List<Doc> {
        val searchAuthors: List<Doc>
        try {
            searchAuthors = getNetworkService().getSearchAuthorsByName(name, 1).docs
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
        return searchAuthors
    }

    private fun loadFavoriteAuthors() {
        lifecycleScope.launch {
            binding.authorSearchView.visibility = View.GONE
            binding.authorSpinner.visibility = View.VISIBLE

            favAuthors = db.userDao().getByUsername("admin")?.userId?.let {
                db.authorDao().getUserWithAuthors(
                    it
                ).authors
            }!!

            binding.authorSpinner.visibility = View.GONE
            binding.authorSearchView.visibility = View.VISIBLE
        }
    }

    private fun setUpRecyclerView() {
        adapter = AuthorsAdapter(
            authors = _authors,
            onClick = {
                addAuthor(it)
                listener.onAuthorClick(it)
            },
            onLongClick = {
                Toast.makeText(context, "long click on: " + it.name, Toast.LENGTH_SHORT).show()
            },
            context = context
        )
        with(binding) {
            rvAuthorList.layoutManager = LinearLayoutManager(context)
            rvAuthorList.adapter = adapter
        }
        Log.d("DiscoverFragment", "setUpRecyclerView")
    }

    private fun addAuthor(author: Author) {
        lifecycleScope.launch {
            db.authorDao().insert(author)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AuthorsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AuthorsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}