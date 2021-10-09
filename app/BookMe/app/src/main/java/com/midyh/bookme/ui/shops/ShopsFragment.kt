package com.midyh.bookme.ui.shops

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.midyh.bookme.R
import com.midyh.bookme.adapters.ShopsAdapter
import com.midyh.bookme.data.Shop
import com.midyh.bookme.databinding.FragmentShopsBinding
import com.midyh.bookme.ui.filter.FilterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopsFragment :
    Fragment(),
    ShopsAdapter.OnShopListener {
    private lateinit var adapter: ShopsAdapter
    private var category: String? = null
    private var defaultTime: String? = null
    private var defaultFilter: String? = null

    private val viewModel: ShopsViewModel by viewModels()

    private var _binding: FragmentShopsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        val root = binding.root
        val shopsView = binding.shopsView
        val fab = binding.floatingActionButton
        val searchBar = binding.searchBar

        shopsView.layoutManager = LinearLayoutManager(context)
        enableSearchView(searchBar, false)
        fab.visibility = View.GONE

        searchBar.isSubmitButtonEnabled = true
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    adapter.filter.filter(it.lowercase())
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    adapter.filter.filter(it.lowercase())
                }
                return false
            }
        })

        fab.setOnClickListener {
            val fm = parentFragmentManager
            val dialog = FilterFragment()
            fm.setFragmentResultListener(
                "requestKey",
                viewLifecycleOwner
            ) { key, bundle ->
                if (key == "requestKey") {
                    val mainFilter = bundle.getString("mainFilter")!!
                    val time = bundle.getString("time")

                    viewModel.changeFilter(mainFilter)
                    defaultFilter = mainFilter
                    time?.let {
                        defaultTime = it
                    }

                    viewModel.shops.value?.let { array ->
                        val sortedShops: MutableList<Shop> =
                            array.sortedBy {
                                var earliest = 10
                                var bestOne = 0
                                for (i in 0 until it.slotsArray!!.size) {
                                    val hours = time.toString().substring(0, 2)
                                    if (hours.toInt() < earliest) {
                                        earliest = hours.toInt()
                                        bestOne = i
                                    }
                                }
                                it.slotsArray[bestOne].startTime
                            }.toMutableList()
                        viewModel.changeShops(sortedShops as ArrayList<Shop>)
                    }
                }
            }
            dialog.show(fm, "filterDialog")
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val main = activity as ShopsActivity
        category =
            requireActivity().intent.extras?.let { ShopsFragmentArgs.fromBundle(it).category }

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        main.setSupportActionBar(toolbar)
        main.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        main.supportActionBar?.title = category

        var isOnline = viewModel.isOnline.value ?: false

        val shopsView = binding.shopsView
        val searchBar = binding.searchBar
        val fab = binding.floatingActionButton
        val emptyText = binding.emptyText
        val noConnectionText = binding.noConnectionText
        val noConnectionImage = binding.noConnectionImage
        val refresh = binding.refresh
        val progress = binding.progress

        refresh.setOnClickListener {
            viewModel.checkConnectivity()
        }

        viewModel.isOnline.observe(
            viewLifecycleOwner,
            { value ->
                isOnline = value
                if (isOnline) {
                    noConnectionText.visibility = View.GONE
                    noConnectionImage.visibility = View.GONE
                    refresh.visibility = View.GONE
                    searchBar.visibility = View.VISIBLE
                    progress.visibility = View.VISIBLE

                    viewModel.getShops(category!!.replace(" ", "").lowercase(), defaultFilter)

                    viewModel.shops.observe(
                        viewLifecycleOwner,
                        { shops ->
                            binding.progress.visibility = View.GONE
                            enableSearchView(searchBar, true)
                            fab.visibility = View.VISIBLE

                            adapter = ShopsAdapter(requireContext(), shops, this, emptyText)
                            shopsView.adapter = adapter
                        }
                    )

                    viewModel.filter.observe(
                        viewLifecycleOwner,
                        Observer {
                            val filter = it ?: return@Observer

                            when (filter.lowercase()) {
                                "rating ↓" -> {
                                    viewModel.shops.value?.let { array ->
                                        val sortedShops: MutableList<Shop> =
                                            array.sortedByDescending { list -> list.rating }
                                                .toMutableList()
                                        defaultFilter = "rating ↓"
                                        viewModel.changeShops(sortedShops as ArrayList<Shop>)
                                    }
                                }

                                "rating ↑" -> {
                                    viewModel.shops.value?.let { array ->
                                        val sortedShops: MutableList<Shop> =
                                            array.sortedBy { list -> list.rating }.toMutableList()
                                        defaultFilter = "rating ↑"
                                        viewModel.changeShops(sortedShops as ArrayList<Shop>)
                                    }
                                }

                                "price ↓" -> {
                                    viewModel.shops.value?.let { array ->
                                        val sortedShops: MutableList<Shop> =
                                            array.sortedByDescending { list -> list.price?.length }
                                                .toMutableList()
                                        defaultFilter = "price ↓"
                                        viewModel.changeShops(sortedShops as ArrayList<Shop>)
                                    }
                                }

                                "price ↑" -> {
                                    viewModel.shops.value?.let { array ->
                                        val sortedShops: MutableList<Shop> =
                                            array.sortedBy { list -> list.price?.length }
                                                .toMutableList()
                                        defaultFilter = "price ↑"
                                        viewModel.changeShops(sortedShops as ArrayList<Shop>)
                                    }
                                }
                            }
                        }
                    )
                } else {
                    noConnectionText.visibility = View.VISIBLE
                    noConnectionImage.visibility = View.VISIBLE
                    refresh.visibility = View.VISIBLE
                    searchBar.visibility = View.GONE
                    progress.visibility = View.GONE
                }
            }
        )


    }

    override fun onShopClick(position: Int) {
        viewModel.shops.value?.let {
            val action = ShopsFragmentDirections.actionShopsToOverview(it[position], category)
            findNavController().navigate(action)
        }
    }

    private fun enableSearchView(view: View, enabled: Boolean) {
        view.isEnabled = enabled
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                enableSearchView(child, enabled)
            }
        }
    }

    companion object {
        val filterList = arrayListOf("rating ↓", "rating ↑", "price ↑", "price ↓")
    }
}
