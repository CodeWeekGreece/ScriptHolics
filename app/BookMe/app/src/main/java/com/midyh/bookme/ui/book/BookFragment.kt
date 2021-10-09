package com.midyh.bookme.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.midyh.bookme.adapters.BookingAdapter
import com.midyh.bookme.data.Slot
import com.midyh.bookme.databinding.FragmentBookBinding

class BookFragment(
    private var data: ArrayList<Slot>?
) : DialogFragment(), BookingAdapter.OnSlotListener {
    private var _binding: FragmentBookBinding? = null

    private val binding get() = _binding!!

    init {
        data = data?.sortedBy { it.startTime }?.toMutableList() as ArrayList<Slot>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookBinding.inflate(inflater, container, false)

        val root = binding.root
        val slotsView = binding.slotsView
        slotsView.layoutManager = LinearLayoutManager(requireContext())

        data?.let {
            val adapter = BookingAdapter(requireContext(), it, this)
            slotsView.adapter = adapter
        }

        return root
    }

    override fun onSlotClick(position: Int) {
        data?.let { data ->
            if (data[position].available) {
                val bundle = Bundle()
                bundle.putParcelable("slot", data[position])
                bundle.putString("slotNum", data.indexOf(data[position]).toString())
                bundle.putString("slotUid", data[position].uid)
                parentFragmentManager.setFragmentResult("requestKey", bundle)
                dismiss()
            }
        }
    }
}
