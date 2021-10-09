package com.midyh.bookme.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.midyh.bookme.R
import com.midyh.bookme.adapters.SlotsAdapter
import com.midyh.bookme.data.Shop
import com.midyh.bookme.data.Slot
import com.midyh.bookme.data.reservations.Reservation
import com.midyh.bookme.databinding.FragmentOverviewBinding
import com.midyh.bookme.ui.book.BookFragment
import com.midyh.bookme.ui.shops.ShopsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment() {
    private val viewModel: OverviewViewModel by viewModels()
    private lateinit var adapter: SlotsAdapter
    private var _binding: FragmentOverviewBinding? = null
    private val args: OverviewFragmentArgs by navArgs()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        val shop: Shop = args.shop
        val category: String = args.category.toString().lowercase().replace(" ", "")
        val userId = viewModel.userId.value

        val root = binding.root
        val image = binding.toolbarImage
        val collapsingToolbar = binding.collapsingToolbar
        val description = binding.description
        val rating = binding.rating
        val slotsView = binding.slotsView
        val mainFab = binding.mainFab
        val progress = binding.progressHorizontal
        val userRating = binding.useRating

        slotsView.layoutManager = LinearLayoutManager(requireContext())
        shop.slotsArray?.let { slots ->
            viewModel.setSlots(slots)
        }

        image.load(shop.imageUrl)
        collapsingToolbar.title = shop.title
        description.text = shop.description
        rating.rating = shop.rating

        shop.ratings?.let { ratings ->
            for ((id, ratingValue) in ratings) {
                if (id == userId.toString()) {
                    userRating.rating = ratingValue
                }
            }
        }

        mainFab.setOnClickListener {
            val dialog = BookFragment(shop.slotsArray)
            parentFragmentManager.setFragmentResultListener(
                "requestKey",
                viewLifecycleOwner
            ) { key, bundle ->
                if (key == "requestKey") {
                    progress.visibility = View.VISIBLE
                    val slot = bundle.getParcelable<Slot>("slot")!!
                    val slotNum = bundle.getString("slotNum")!!

                    val reservation = Reservation(
                        "", // Will be added in the ViewModel so the UUID is the same
                        shop.title,
                        slot.startTime,
                        slot.endTime,
                        category,
                        shop.id,
                        slot.uid,
                    )

                    val slots =
                        shop.slotsArray!!.sortedBy { it.startTime }.toMutableList() as ArrayList
                    slots[slotNum.toInt()].available = false
                    viewModel.setSlots(slots)

                    viewModel.putReservation(
                        reservation,
                        slot,
                        category,
                        shop.id
                    )
                }
            }

            dialog.show(parentFragmentManager, "BookDialog")
        }

        userRating.setOnRatingBarChangeListener { _, ratingValue, _ ->
            viewModel.rateRestaurant(category, shop.id, ratingValue.toInt())
        }

        viewModel.success.observe(
            viewLifecycleOwner,
            {
                progress.visibility = View.GONE
                Snackbar.make(requireView(), getString(R.string.res_booked), Snackbar.LENGTH_SHORT)
                    .show()
            }
        )

        viewModel.slots.observe(
            viewLifecycleOwner,
            {
                adapter = SlotsAdapter(requireContext(), it)
                slotsView.adapter = adapter
            }
        )

        viewModel.rateState.observe(
            viewLifecycleOwner,
            {
                userRating.isEnabled = !it.isLoading
            }
        )

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.collapsing_toolbar)
        val main = requireActivity() as ShopsActivity
        main.setSupportActionBar(toolbar)
        main.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
