package com.aseegpsproject.openbook.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.aseegpsproject.openbook.api.getNetworkService
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.databinding.FragmentAuthorDetailBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class AuthorDetailFragment : Fragment() {
    private val args: AuthorDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentAuthorDetailBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val viewModel: AuthorDetailViewModel by viewModels { AuthorDetailViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAuthorDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthorDetailBinding.bind(view)
        viewModel.author = args.author

        setUpUI()
        setUpListeners()
        subscribeUI()
    }

    private fun setUpListeners() {
        with(binding) {
            btnFavoriteAuthor.setOnClickListener {
                viewModel.changeFavoriteAuthor()
            }
        }
    }

    private fun setUpUI() {
        with (binding) {
            spinnerAuthorDetail.visibility = View.VISIBLE
            svAuthorDetail.visibility = View.GONE
        }
    }

    private fun subscribeUI() {
        homeViewModel.user.observe(viewLifecycleOwner) {
            viewModel.user = it
        }
        viewModel.toast.observe(viewLifecycleOwner) { text ->
            text?.let {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }
        viewModel.authorDetail.observe(viewLifecycleOwner) { author ->
            author?.let { setUpBinding(it) }
        }
    }

    private fun setUpBinding(author: Author) {
        with (binding) {
            tvAuthorName.text = author.name
            tvAuthorDates.text = author.birthDate
            tvAuthorDates2.text = author.deathDate
            tvAuthorBio.text = author.bio
            Glide.with(requireContext())
                .load(author.photoPath)
                .into(ivAuthorPhoto)

            spinnerAuthorDetail.visibility = View.GONE
            svAuthorDetail.visibility = View.VISIBLE
        }
    }
}