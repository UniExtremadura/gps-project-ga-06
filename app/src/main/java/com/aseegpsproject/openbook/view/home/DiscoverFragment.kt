package com.aseegpsproject.openbook.view.home

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
import com.aseegpsproject.openbook.data.apimodel.TrendingWork
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.data.toWork
import com.aseegpsproject.openbook.database.OpenBookDatabase
import com.aseegpsproject.openbook.databinding.FragmentDiscoverBinding
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DiscoverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiscoverFragment : Fragment() {

    private lateinit var listener: OnWorkClickListener

    private var _works = listOf<Work>()
    private lateinit var db: OpenBookDatabase

    interface OnWorkClickListener {
        fun onWorkClick(work: Work)
    }

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DiscoverAdapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        if (context is OnWorkClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnBookClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        db = OpenBookDatabase.getInstance(requireContext())!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        lifecycleScope.launch {
            if (_works.isEmpty()) {
                binding.searchView.visibility = View.GONE
                binding.spinner.visibility = View.VISIBLE

                try {
                    val trendingWorks = fetchTrendingBooks()
                    _works = trendingWorks.map { it.toWork() }
                    adapter.updateData(_works)
                } catch (cause: Throwable) {
                    Log.e("DiscoverFragment", "Error fetching data", cause)
                    Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
                } finally {
                    binding.spinner.visibility = View.GONE
                    binding.searchView.visibility = View.VISIBLE
                }
            }
        }

        setUpSearchView()
    }

    private fun setUpSearchView() {
        binding.searchView.setOnClickListener { binding.searchView.isIconified = false }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    lifecycleScope.launch {
                        if (query.isNotEmpty()) {
                            binding.rvBookList.visibility = View.GONE
                            binding.spinner.visibility = View.VISIBLE
                            try {
                                val searchWorks = fetchSearchBooksByTitle(query)
                                _works = searchWorks.map { it.toWork() }
                                adapter.updateData(_works)
                            } catch (cause: Throwable) {
                                Log.e("DiscoverFragment", "Error fetching data", cause)
                                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT)
                                    .show()
                            } finally {
                                binding.rvBookList.scrollToPosition(0)
                                binding.spinner.visibility = View.GONE
                                binding.rvBookList.visibility = View.VISIBLE
                            }
                        } else {
                            lifecycleScope.launch {
                                if (_works.isEmpty()) {
                                    binding.rvBookList.visibility = View.GONE
                                    binding.spinner.visibility = View.VISIBLE
                                    try {
                                        val trendingWorks = fetchTrendingBooks()
                                        _works = trendingWorks.map { it.toWork() }
                                        adapter.updateData(_works)
                                    } catch (cause: Throwable) {
                                        Log.e("DiscoverFragment", "Error fetching data", cause)
                                        Toast.makeText(
                                            context,
                                            "Error fetching data",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } finally {
                                        binding.spinner.visibility = View.GONE
                                        binding.rvBookList.visibility = View.VISIBLE
                                    }
                                }
                            }
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isEmpty()) {
                        lifecycleScope.launch {
                            binding.rvBookList.visibility = View.GONE
                            binding.spinner.visibility = View.VISIBLE
                            try {
                                val trendingWorks = fetchTrendingBooks()
                                _works = trendingWorks.map { it.toWork() }
                                adapter.updateData(_works)
                            } catch (cause: Throwable) {
                                Log.e("DiscoverFragment", "Error fetching data", cause)
                                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT)
                                    .show()
                            } finally {
                                binding.spinner.visibility = View.GONE
                                binding.rvBookList.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                return false
            }
        })
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
            trendingWorks = getNetworkService().getDailyTrendingBooks().trendingWorks
        } catch (cause: Throwable) {
            Log.e("DiscoverFragment", "Error fetching data", cause)
        }
        return trendingWorks
    }

    private fun setUpRecyclerView() {
        adapter = DiscoverAdapter(
            works = _works,
            onClick = {
                listener.onWorkClick(it)
            },
            onLongClick = {
                Toast.makeText(context, "long click on: " + it.title, Toast.LENGTH_SHORT).show()
            },
            context = context
        )
        with(binding) {
            rvBookList.layoutManager = LinearLayoutManager(context)
            rvBookList.adapter = adapter
        }
        Log.d("DiscoverFragment", "setUpRecyclerView")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DiscoverFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiscoverFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}