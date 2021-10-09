package com.midyh.bookme.ui.filter

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.midyh.bookme.databinding.FragmentFilterBinding
import com.midyh.bookme.ui.shops.ShopsFragment

class FilterFragment(
    private val defaultFilter: String? = null,
    private val defaultTime: String? = null
) : DialogFragment() {
    private var time: String? = null

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        val root = binding.root
        val applyButton = binding.applyBtn
        val cancelButton = binding.cancelBtn
        val spinner = binding.spinner
        val choose = binding.chooseTime

        defaultFilter?.let {
            spinner.setSelection(ShopsFragment.filterList.indexOf(it))
        }

        defaultTime?.let {
            choose.text = it
            time = it
        }

        cancelButton.setOnClickListener {
            dismiss()
        }

        applyButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("mainFilter", spinner.selectedItem.toString())
            bundle.putString("time", time)
            parentFragmentManager.setFragmentResult("requestKey", bundle)
            dismiss()
        }

        choose.setOnClickListener {
            val isSystem24Hour = is24HourFormat(requireContext())
            val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Choose slot time")
                .build()

            picker.show(parentFragmentManager, "PICKER")

            picker.addOnPositiveButtonClickListener {
                choose.text = "${picker.hour}:${picker.minute}"
                time = choose.text.toString()
            }
        }

        return root
    }
}
