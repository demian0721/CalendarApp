package com.calendar.app.ui.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.calendar.app.databinding.ItemScheduleViewBinding
import com.calendar.app.model.Schedule

class ScheduleItemAdapter(
    private val viewModel: CalendarViewModel
) :
    ListAdapter<Schedule, ScheduleItemAdapter.ScheduleItemViewHolder>(ScheduleItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleItemViewHolder {
        val binding =
            ItemScheduleViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ScheduleItemViewHolder(private val binding: ItemScheduleViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: Schedule) {
            binding.viewModel = viewModel
            binding.schedule = schedule
            binding.executePendingBindings()
        }
    }
}

class ScheduleItemDiffCallback : DiffUtil.ItemCallback<Schedule>() {
    override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
        return oldItem.subject == newItem.subject
    }

    override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
        return oldItem == newItem
    }

}