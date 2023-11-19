package com.aseegpsproject.openbook.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aseegpsproject.openbook.api.APICallback
import com.aseegpsproject.openbook.api.APIError
import com.aseegpsproject.openbook.api.getNetworkService
import com.aseegpsproject.openbook.data.apimodel.TrendingWork
import com.aseegpsproject.openbook.data.dummyWorks
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.data.toWork
import com.aseegpsproject.openbook.databinding.FragmentDiscoverBinding
import java.util.concurrent.Executors

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

    private lateinit var listener: OnBookClickListener

    private var _works = listOf<Work>()

    val BACKGROUND = Executors.newFixedThreadPool(2)

    interface OnBookClickListener {
        fun onBookClick(work: Work)
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
        if (context is OnBookClickListener) {
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        if (_works.isEmpty()) {
            binding.spinner.visibility = View.VISIBLE

            fetchTrendingBooks(object : APICallback<List<TrendingWork>> {
                override fun onSuccess(result: List<TrendingWork>) {
                    Log.d("DiscoverFragment", "APICallback onCompleted")
                    val works = result.map {
                        it.toWork()
                    }
                    // Update the UI on the main thread
                    activity?.runOnUiThread {
                        _works = works // ?: dummyWorks
                        adapter.updateData(_works)
                        binding.spinner.visibility = View.GONE
                    }
                }

                override fun onError(cause: Throwable) {
                    Log.e("DiscoverFragment", "APICallback onError")
                    // Update the UI on the main thread
                    activity?.runOnUiThread {
                        Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
                        binding.spinner.visibility = View.GONE
                    }
                }
            }
            )
        }
    }

    private fun fetchTrendingBooks(apiCallback: APICallback<List<TrendingWork>>) {
        BACKGROUND.submit{
            try {
                // Make network request using a blocking call
                val result = getNetworkService().getDailyTrendingBooks().execute()

                if (result.isSuccessful)
                    apiCallback.onSuccess(result.body()!!.trendingWorks)
                else
                    apiCallback.onError(APIError("API Response error ${result.errorBody()}", null))
            } catch (cause: Throwable) {
                // Update the UI on the main thread if something goes wrong
                // Bad modularization, we should not know about the UI thread here
                activity?.runOnUiThread {
                    Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show()
                    binding.spinner.visibility = View.GONE
                }
                Log.e("DiscoverFragment", "APICallback connection error")
                // If anything throws an exception, inform the caller
                throw APIError("Unable to fetch data from API", cause)
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = DiscoverAdapter(works = dummyWorks, onClick = {
            listener.onBookClick(it)
        },
            onLongClick = {
                Toast.makeText(context, "long click on: " + it.title, Toast.LENGTH_SHORT).show()
            }
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