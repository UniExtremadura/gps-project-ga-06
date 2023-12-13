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
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.api.APIError
import com.aseegpsproject.openbook.api.getNetworkService
import com.aseegpsproject.openbook.data.Repository
import com.aseegpsproject.openbook.data.apimodel.Doc
import com.aseegpsproject.openbook.data.apimodel.TrendingWork
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.data.toWork
import com.aseegpsproject.openbook.database.OpenBookDatabase
import com.aseegpsproject.openbook.databinding.FragmentDiscoverBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DiscoverFragment : Fragment() {

    private lateinit var listener: OnWorkClickListener
    private var _works = listOf<Work>()
    private lateinit var db: OpenBookDatabase
    private lateinit var user: User
    private lateinit var repository: Repository
    private lateinit var trendingFreq: String

    interface OnWorkClickListener {
        fun onWorkClick(work: Work)
    }

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DiscoverAdapter

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        db = OpenBookDatabase.getInstance(context)!!
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        trendingFreq = prefs.getString("trending", "daily") ?: "daily"
        repository = Repository.getInstance(
            db.workDao(),
            getNetworkService()
        )
        repository.setTrendingFreq(trendingFreq)
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
        user = activity?.intent?.getSerializableExtra("USER_INFO") as User
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpSearchView()

        subscribeUi(adapter)
        launchDataLoad { repository.tryUpdateRecentWorksCache() }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return lifecycleScope.launch {
            try {
                binding.spinner.visibility = View.VISIBLE
                block()
            } catch (error: APIError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            } finally {
                binding.spinner.visibility = View.GONE
            }
        }
    }

    private fun subscribeUi(adapter: DiscoverAdapter) {
        repository.works.observe(viewLifecycleOwner) { works ->
            adapter.updateData(works.filter { it.isDiscover })
        }
    }

    private fun setUpSearchView() {
        binding.discoverSearchView.setOnClickListener {
            binding.discoverSearchView.isIconified = false
        }
        binding.discoverSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchQuery = query ?: ""
                lifecycleScope.launch {
                    handleSearch(searchQuery)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() == true) {
                    launchDataLoad { repository.tryUpdateRecentWorksCache() }
                }
                return true
            }
        })
    }

    fun handleSearch(query: String) {
        showLoading()
        lifecycleScope.launch {
            runCatching {
                if (query.isNotEmpty()) {
                    fetchSearchBooksByTitle(query).map { it.toWork() }
                } else {
                    fetchTrendingBooks().map { it.toWork() }
                }
            }.onSuccess { works ->
                _works = works
                adapter.updateData(_works)
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
            works = _works,
            onClick = {
                listener.onWorkClick(it)
            },
            onLongClick = {
                changeFavoriteWork(it)
            },
            context = context
        )
        with(binding) {
            rvBookList.layoutManager = LinearLayoutManager(context)
            rvBookList.adapter = adapter
        }
        Log.d("DiscoverFragment", "setUpRecyclerView")
    }

    private fun changeFavoriteWork(work: Work) {
        lifecycleScope.launch {
            if (work.isFavorite) {
                work.isFavorite = false
                repository.deleteWorkFromLibrary(work, user.userId!!)
                Toast.makeText(context, R.string.remove_fav, Toast.LENGTH_SHORT).show()
            } else {
                work.isFavorite = true
                repository.workToLibrary(work, user.userId!!)
                Toast.makeText(context, R.string.add_fav, Toast.LENGTH_SHORT).show()
            }
        }
    }
}