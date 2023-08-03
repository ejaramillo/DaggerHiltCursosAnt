package com.example.xpenses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.xpenses.databinding.ItemRecordBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class RecordAdapter @Inject constructor(
    @ActivityContext private val context: Context,
    private val utils: CommonUtils
) : ListAdapter<WorkDay, RecyclerView.ViewHolder>(WorkDayDiff()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_record, parent, false))
    }

    override fun onBindViewHolder(
        holder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
        position: Int
    ) {
        val workDay = getItem(position)
        with((holder as ViewHolder).binding){
            workDay.date?.let {
                tvDate.text = utils.getFormatDate(it.toDate())
                tvExpenses.text = context.getString(R.string.item_record_expenses, workDay.expenses)
                tvStartCapital.text = context.getString(R.string.item_record_start_capital, workDay.startCapital)
                tvFinalCapital.text = context.getString(R.string.item_record_final_capital, workDay.finalCapital)
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemRecordBinding.bind(view)
    }

    private class WorkDayDiff : DiffUtil.ItemCallback<WorkDay>() {
        override fun areItemsTheSame(oldItem: WorkDay, newItem: WorkDay): Boolean =
            oldItem.uid == newItem.uid

        override fun areContentsTheSame(oldItem: WorkDay, newItem: WorkDay) = oldItem == newItem
    }

}
