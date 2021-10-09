package com.midyh.bookme.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    val name: String? = null,
    val phone: String? = null,
) : Parcelable
