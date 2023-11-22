package com.aseegpsproject.openbook.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.api.getNetworkService
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.databinding.FragmentAuthorDetailsBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AuthorDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AuthorDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val args: AuthorDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentAuthorDetailsBinding
    private lateinit var author: Author

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_author_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthorDetailsBinding.bind(view)
        author = args.author

        with (binding) {
            tvAuthorName.text = author.name
            tvAuthorDates.text = author.birthDate
            tvAuthorDates2.text = author.deathDate
            lifecycleScope.launch {
                tvAuthorBio.text = getNetworkService().getAuthorInfo(author.authorKey).bio
            }
            Glide.with(requireContext())
                .load(author.photoPath)
                .into(ivAuthorPhoto)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AuthorDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AuthorDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}