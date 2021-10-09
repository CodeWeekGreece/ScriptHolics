package com.midyh.bookme.data.reservations

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationsDao {
    @Query("SELECT * FROM reservations")
    fun getAllReservations(): Flow<List<Reservation>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReservations(reservations: List<Reservation>?)

    @Query("DELETE FROM reservations")
    suspend fun deleteReservations()

    @Query("DELETE FROM reservations WHERE uid=:uid")
    suspend fun deleteReservation(uid: String)
}
