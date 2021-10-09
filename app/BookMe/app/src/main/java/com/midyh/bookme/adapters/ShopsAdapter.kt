package com.midyh.bookme.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.midyh.bookme.R
import com.midyh.bookme.data.Shop

class ShopsAdapter(
    context: Context,
    private val data: ArrayList<Shop>,
    private val shopListener: OnShopListener,
    val emptyText: TextView
) : RecyclerView.Adapter<ShopsAdapter.ViewHolder>(), Filterable {
    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private var filtered = ArrayList<Shop>(data)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.recyclerview_shops, parent, false)
        return ViewHolder(view, shopListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = filtered[position].title
        holder.rating.rating = filtered[position].rating
        holder.image.load(filtered[position].imageUrl)
    }

    override fun getItemCount(): Int = filtered.size

    override fun getFilter(): Filter = newFilter

    private val newFilter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredMap = ArrayList<Shop>()

            if (p0 != null) {
                if (p0.toString().isEmpty()) {
                    filteredMap.addAll(data)
                } else {
                    for (shop: Shop in data) {
                        if (shop.title.lowercase().contains(p0.toString())) {
                            filteredMap.add(shop)
                        }
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredMap
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            filtered.clear()
            if (p1 != null) {
                val newValues = p1.values as ArrayList<Shop>
                if (newValues.isEmpty()) {
                    emptyText.visibility = View.VISIBLE
                } else {
                    filtered = newValues
                    emptyText.visibility = View.GONE
                }
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(
        itemView: View,
        onShopListener: OnShopListener
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val listener = onShopListener
        val title: TextView = itemView.findViewById(R.id.title)
        val rating: RatingBar = itemView.findViewById(R.id.rating)
        val image: ImageView = itemView.findViewById(R.id.image)

        init {
            itemView.setOnClickListener {
                listener.onShopClick(data.indexOf(filtered[adapterPosition]))
            }
        }
    }

    interface OnShopListener {
        fun onShopClick(position: Int)
    }
}
