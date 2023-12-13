package com.aseegpsproject.openbook.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.databinding.DiscoverItemListBinding
import com.bumptech.glide.Glide

class DiscoverAdapter(
    private var works: List<Work>,
    private val onClick: (work: Work) -> Unit,
    private val onLongClick: (work: Work) -> Unit,
    private val context: Context?
) : RecyclerView.Adapter<DiscoverAdapter.BookViewHolder>() {

    class BookViewHolder(
        private val binding: DiscoverItemListBinding,
        private val onClick: (work: Work) -> Unit,
        private val onLongClick: (work: Work) -> Unit,
        private val context: Context?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(work: Work) {
            with(binding) {
                workTitle.text = work.title
                workAuthor.text = work.authorNames?.joinToString(", ") ?: "Unknown"
                workYear.text = work.firstPublishYear.toString()
                context?.let {
                    Glide.with(context)
                        .load(work.coverPaths[0])
                        .placeholder(R.drawable.placeholder)
                        .into(workCover)
                }
                discoverClItem.setOnClickListener {
                    onClick(work)
                }
                discoverClItem.setOnLongClickListener {
                    onLongClick(work)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding =
            DiscoverItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding, onClick, onLongClick, context)
    }

    override fun getItemCount() = works.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(works[position])
    }


    fun updateData(works: List<Work>) {
        this.works = works
        notifyDataSetChanged()
    }
}