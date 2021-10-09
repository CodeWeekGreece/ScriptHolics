package com.midyh.bookme.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.midyh.bookme.databinding.FragmentHomeBinding
import com.midyh.bookme.ui.shops.ShopsActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val restaurants = binding.restaurants
        val supermarkets = binding.supermarkets
        val selfCare = binding.selfCare
        val clothing = binding.clothing

        // =======================Initialize Listeners======================
        restaurants.setOnClickListener {
            val i = Intent(requireContext(), ShopsActivity::class.java).putExtra(
                "category",
                "Restaurants"
            )
            startActivity(i)
        }

        supermarkets.setOnClickListener {
            val i = Intent(requireContext(), ShopsActivity::class.java).putExtra(
                "category",
                "Supermarkets"
            )
            startActivity(i)
        }

        selfCare.setOnClickListener {
            val i = Intent(requireContext(), ShopsActivity::class.java).putExtra(
                "category",
                "Self Care"
            )
            startActivity(i)
        }

        clothing.setOnClickListener {
            val i = Intent(requireContext(), ShopsActivity::class.java).putExtra(
                "category",
                "Clothing"
            )
            startActivity(i)
        }
        // ===================================================================

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
