package com.midyh.bookme.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Slot(
    val uid: String? = null,
    var available: Boolean = true,
    val startTime: String = "09:00",
    val endTime: String = "10:00",
    val owner: Owner? = null,
) : Parcelable
