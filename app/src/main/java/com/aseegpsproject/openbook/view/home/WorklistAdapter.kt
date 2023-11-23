package com.aseegpsproject.openbook.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.databinding.DiscoverItemListBinding
import com.bumptech.glide.Glide

class WorklistAdapter(
    private var works: List<Work>,
    private val onClick: (work: Work) -> Unit,
    private val onLongClick: (work: Work) -> Unit,
    private val context: Context?
) : RecyclerView.Adapter<WorklistAdapter.WorklistViewHolder>() {

    class WorklistViewHolder(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorklistViewHolder {
        val binding =
            DiscoverItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorklistViewHolder(binding, onClick, onLongClick, context)
    }

    override fun getItemCount() = works.size

    override fun onBindViewHolder(holder: WorklistViewHolder, position: Int) {
        holder.bind(works[position])
    }

    fun updateData(works: List<Work>) {
        this.works = works
        notifyDataSetChanged()
    }
}