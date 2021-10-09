package com.midyh.bookme.ui.reservations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.midyh.bookme.R
import com.midyh.bookme.adapters.ReservationsAdapter
import com.midyh.bookme.databinding.FragmentReservationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationsFragment : Fragment(), ReservationsAdapter.OnReservationListener {
    private var _binding: FragmentReservationsBinding? = null
    private val viewModel: ReservationsViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var adapter: ReservationsAdapter
        _binding = FragmentReservationsBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val resView = binding.resView
        val noRes = binding.noRes
        val progress = binding.progressHorizontal

        progress.isVisible = true
        noRes.isVisible = true
        viewModel.getReservations()

        resView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.reservations.observe(
            viewLifecycleOwner,
            {
                progress.isVisible = false
                noRes.isVisible = it.isEmpty()
                adapter = ReservationsAdapter(requireContext(), it, this)
                resView.adapter = adapter
            }
        )

        return root
    }

    override fun onReservationClick(position: Int) {
        val progress = binding.progressHorizontal
        viewModel.reservations.value?.let {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.cancel_res))
                .setMessage(getString(R.string.cancel_res_sure))
                .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                    dialog.dismiss()
                    progress.isVisible = true
                    viewModel.deleteReservation(it[position])
                }
                .show()
        }
    }
}
