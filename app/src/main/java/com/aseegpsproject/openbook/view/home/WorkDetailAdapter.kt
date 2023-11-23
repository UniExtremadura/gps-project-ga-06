package com.aseegpsproject.openbook.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.data.model.Worklist
import com.aseegpsproject.openbook.databinding.WorklistItemListBinding

class WorkDetailAdapter(
    private var worklists: List<Worklist>,
    private val onClick: (workList: Worklist) -> Unit,
    private val onLongClick: (workList: Worklist) -> Unit,
    private val context: Context?
) : RecyclerView.Adapter<WorkDetailAdapter.WorkListViewHolder>() {

    class WorkListViewHolder(
        private val binding: WorklistItemListBinding,
        private val onClick: (workList: Worklist) -> Unit,
        private val onLongClick: (workList: Worklist) -> Unit,
        private val context: Context?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workList: Worklist) {
            with(binding) {
                tvWorklistName.text = workList.name
                ivWorklist.setImageResource(R.drawable.list_cover)
                cvWorklistItem.setOnClickListener {
                    onClick(workList)
                }
                cvWorklistItem.setOnLongClickListener {
                    onLongClick(workList)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkListViewHolder {
        val binding =
            WorklistItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkListViewHolder(binding, onClick, onLongClick, context)
    }

    override fun getItemCount() = worklists.size

    override fun onBindViewHolder(holder: WorkListViewHolder, position: Int) {
        holder.bind(worklists[position])
    }

    fun updateData(worklists: List<Worklist>) {
        this.worklists = worklists
        notifyDataSetChanged()
    }
}