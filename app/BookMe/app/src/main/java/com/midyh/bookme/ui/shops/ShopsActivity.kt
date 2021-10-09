package com.midyh.bookme.ui.shops

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.midyh.bookme.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shops)

        val category = intent.extras?.getString("category")

        supportActionBar?.title = when (category?.lowercase()) {
            "restaurants" -> getString(R.string.restaurants)
            "supermarkets" -> getString(R.string.supermarkets)
            "self care" -> getString(R.string.self_care)
            "clothing" -> getString(R.string.clothing)
            else -> "BookMe"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
