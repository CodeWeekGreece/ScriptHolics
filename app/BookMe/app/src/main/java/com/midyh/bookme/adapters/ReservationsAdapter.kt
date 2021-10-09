package com.midyh.bookme.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.midyh.bookme.R
import com.midyh.bookme.data.reservations.Reservation

class ReservationsAdapter(
    context: Context,
    private val data: List<Reservation>,
    private val reservationListener: OnReservationListener
) : RecyclerView.Adapter<ReservationsAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.recyclerview_reservations, parent, false)
        return ViewHolder(view, reservationListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.shopTitle.text = data[position].title
        holder.shopTime.text = String.format(
            holder.itemView.context.getString(R.string.time),
            data[position].startTime,
            data[position].endTime,
        )
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        itemView: View,
        onReservationListener: OnReservationListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val listener = onReservationListener
        private val close: ImageView = itemView.findViewById(R.id.close_btn)
        val shopTitle: TextView = itemView.findViewById(R.id.shop_title)
        val shopTime: TextView = itemView.findViewById(R.id.shop_time)

        init {
            close.setOnClickListener {
                listener.onReservationClick(data.indexOf(data[adapterPosition]))
            }
        }
    }

    interface OnReservationListener {
        fun onReservationClick(position: Int)
    }
}
