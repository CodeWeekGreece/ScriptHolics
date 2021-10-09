package com.midyh.bookme.ui.shops

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midyh.bookme.api.ShopsApi
import com.midyh.bookme.data.Shop
import com.midyh.bookme.data.Slot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopsViewModel @Inject constructor(
    application: Application,
    private val api: ShopsApi
) : AndroidViewModel(application) {
    private val context
        get() = getApplication<Application>()

    private val _shops = MutableLiveData<ArrayList<Shop>>()
    val shops: LiveData<ArrayList<Shop>> = _shops

    private val _isOnline = MutableLiveData<Boolean>()
    val isOnline: LiveData<Boolean> = _isOnline

    private val staticShops = ArrayList<Shop>()

    private val _filter = MutableLiveData<String>()
    val filter: LiveData<String> = _filter

    init {
        checkConnectivity()
    }

    fun checkConnectivity() {
        _isOnline.value = isOnline()
    }

    fun changeShops(array: ArrayList<Shop>) {
        _shops.value = array
    }

    fun changeFilter(flt: String) {
        _filter.value = flt
    }

    fun changeShopsOnTime(time: String) {
        val shopsOnTime = ArrayList<Shop>()
        val hours = time.substring(0, 2).toInt()

        for (shop in _shops.value!!) {
            for (slot in shop.slotsArray!!) {
                if (slot.startTime.substring(0, 2).toInt() >= hours) {
                    shopsOnTime.add(shop)
                }
            }
        }

        _shops.value = shopsOnTime
    }

    fun getShops(category: String, currentFilter: String?) {
        viewModelScope.launch {
            val shops = api.getShops(category)
            val shopsArray = ArrayList<Shop>()

            // Converting the LinkedTreeMap to ArrayList for easier handling
            for ((title, value) in shops) {
                val slotsArray = ArrayList<Slot>()

                // Copying the slots HashMap<String, Slot> into an ArrayList for
                // the RecyclerView, while keeping the HashMap for
                // faster identification through UUID
                value.slots?.let { slots ->
                    for ((uid, slot) in slots) {
                        val newSlot = Slot(
                            uid,
                            slot.available,
                            slot.startTime,
                            slot.endTime,
                            slot.owner,
                        )

                        slotsArray.add(newSlot)
                    }
                }

                val shop = Shop(
                    title,
                    value.title,
                    value.location,
                    value.ratings,
                    average(value.ratings),
                    value.imageUrl,
                    value.description,
                    value.price,
                    value.slots,
                    slotsArray
                )
                shopsArray.add(shop)
                staticShops.add(shop)
            }
            _shops.value = shopsArray
            currentFilter?.let { _filter.value = it }
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            capabilities?.let {
                return true
            }
        }

        return false
    }

    private fun average(ratings: HashMap<String, Float>?): Float {
        ratings?.let {
            var averageRating = 0f

            for ((_, rating) in it) {
                averageRating += rating
            }

            return averageRating / it.size
        } ?: return 0f
    }
}
