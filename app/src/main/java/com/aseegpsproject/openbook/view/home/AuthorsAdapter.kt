package com.aseegpsproject.openbook.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.databinding.AuthorsItemListBinding
import com.bumptech.glide.Glide

class AuthorsAdapter(
    private var authors: List<Author>,
    private val onClick: (author: Author) -> Unit,
    private val onLongClick: (author: Author) -> Unit,
    private val context: Context?
) : RecyclerView.Adapter<AuthorsAdapter.AuthorViewHolder>() {

    class AuthorViewHolder(
        private val binding: AuthorsItemListBinding,
        private val onClick: (author: Author) -> Unit,
        private val onLongClick: (author: Author) -> Unit,
        private val context: Context?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(author: Author) {
            with(binding) {
                authorName.text = author.name
                authorFullName.text = author.fullName
                authorBirthDate.text = author.birthDate
                context?.let {
                    Glide.with(context)
                        .load(author.photoPath)
                        .placeholder(R.drawable.placeholder)
                        .into(authorPhoto)
                }
                authorClItem.setOnClickListener {
                    onClick(author)
                }
                authorClItem.setOnLongClickListener {
                    onLongClick(author)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val binding =
            AuthorsItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AuthorViewHolder(binding, onClick, onLongClick, context)
    }

    override fun getItemCount() = authors.size

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        holder.bind(authors[position])
    }


    fun updateData(authors: List<Author>) {
        this.authors = authors
        notifyDataSetChanged()
    }
}