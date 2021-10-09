package com.midyh.bookme.data

import android.os.Parcelable
import com.midyh.bookme.data.Dev.lorem
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shop(
    val id: String = "",
    val title: String = "",
    val location: String = "",
    val ratings: HashMap<String, Float>? = null,
    val rating: Float = 0f,
    val imageUrl: String = "",
    val description: String = lorem,
    val price: String = "",
    val slots: HashMap<String, Slot>? = null,
    val slotsArray: ArrayList<Slot>? = null
) : Parcelable
