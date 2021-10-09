package com.midyh.bookme.data.reservations

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "reservations")
data class Reservation(
    @PrimaryKey val uid: String = UUID.randomUUID().toString(),
    val title: String? = null,
    val startTime: String? = null,
    val endTime: String? = null,
    val category: String? = null,
    val shopUid: String? = null,
    val slotUid: String? = null,
)
