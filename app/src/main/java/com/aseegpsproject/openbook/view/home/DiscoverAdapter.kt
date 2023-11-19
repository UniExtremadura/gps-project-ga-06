package com.aseegpsproject.openbook.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.databinding.DiscoverItemListBinding

class DiscoverAdapter(
    private var works: List<Work>,
    private val onClick: (work: Work) -> Unit,
    private val onLongClick: (work: Work) -> Unit
) : RecyclerView.Adapter<DiscoverAdapter.BookViewHolder>() {

    class BookViewHolder(
        private val binding: DiscoverItemListBinding,
        private val onClick: (work: Work) -> Unit,
        private val onLongClick: (work: Work) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(work: Work) {
            with(binding) {
                workTitle.text = work.title
                workAuthor.text = work.authors.joinToString(", ")
                workYear.text = work.firstPublishYear.toString()
                workCover.setImageResource(/*work.coverPaths[0]*/ 0)
                clItem.setOnClickListener {
                    onClick(work)
                }
                clItem.setOnLongClickListener {
                    onLongClick(work)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding =
            DiscoverItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding, onClick, onLongClick)
    }

    override fun getItemCount() = works.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(works[position])
    }


    fun updateData(works: List<Work>) {
        this.works = works
    }
}