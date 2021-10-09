package com.midyh.bookme.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.midyh.bookme.R
import com.midyh.bookme.data.Slot

class BookingAdapter(
    context: Context,
    private var data: ArrayList<Slot>,
    private val listener: OnSlotListener,
) : RecyclerView.Adapter<BookingAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    init {
        data = data.sortedBy { it.startTime }.toMutableList() as ArrayList<Slot>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.recyclerview_slots, parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val slot = holder.slot
        slot.text = String.format(
            holder.itemView.context.getString(R.string.slot),
            (position + 1).toString(),
            data[position].startTime,
            data[position].endTime
        )

        if (!data[position].available) {
            slot.paintFlags = slot.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            slot.setTextColor(Color.GRAY)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        itemView: View,
        onSlotListener: OnSlotListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val listener = onSlotListener
        val slot: TextView = itemView.findViewById(R.id.slot)

        init {
            slot.setPadding(10)
            slot.textSize = 16f

            slot.setOnClickListener {
                listener.onSlotClick(data.indexOf(data[layoutPosition]))
            }
        }
    }

    interface OnSlotListener {
        fun onSlotClick(position: Int)
    }
}
