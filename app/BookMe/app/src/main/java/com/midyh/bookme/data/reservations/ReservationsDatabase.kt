package com.midyh.bookme.data.reservations

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Reservation::class], version = 1,)
abstract class ReservationsDatabase : RoomDatabase() {
    abstract fun reservationsDao(): ReservationsDao
}
