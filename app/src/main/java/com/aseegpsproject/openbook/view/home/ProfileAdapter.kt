package com.aseegpsproject.openbook.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.data.model.WorkList
import com.aseegpsproject.openbook.databinding.WorklistItemListBinding

class ProfileAdapter(
    private var workLists: List<WorkList>,
    private val onClick: (workList: WorkList) -> Unit,
    private val onLongClick: (workList: WorkList) -> Unit,
    private val context: Context?
) : RecyclerView.Adapter<ProfileAdapter.WorkListViewHolder>() {

    class WorkListViewHolder(
        private val binding: WorklistItemListBinding,
        private val onClick: (workList: WorkList) -> Unit,
        private val onLongClick: (workList: WorkList) -> Unit,
        private val context: Context?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workList: WorkList) {
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

    override fun getItemCount() = workLists.size

    override fun onBindViewHolder(holder: WorkListViewHolder, position: Int) {
        holder.bind(workLists[position])
    }

    fun updateData(workLists: List<WorkList>) {
        this.workLists = workLists
        notifyDataSetChanged()
    }
}