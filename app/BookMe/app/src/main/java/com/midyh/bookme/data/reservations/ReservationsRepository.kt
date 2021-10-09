package com.midyh.bookme.data.reservations

import androidx.room.withTransaction
import com.google.gson.internal.LinkedTreeMap
import com.midyh.bookme.api.ReservationsApi
import com.midyh.bookme.api.UsersApi
import com.midyh.bookme.data.authentication.UserDatabase
import com.midyh.bookme.util.networkBoundResource
import javax.inject.Inject

class ReservationsRepository @Inject constructor(
    private val api: ReservationsApi,
    private val userApi: UsersApi,
    private val db: ReservationsDatabase,
    private val userDb: UserDatabase,
) {
    private val reservationsDao = db.reservationsDao()
    private val userDao = userDb.userDao()

    fun getReservations(userUid: String) =
        networkBoundResource(
            query = {
                reservationsDao.getAllReservations()
            },
            fetch = {
                treeToList(api.getReservations(userUid)?.body())
            },
            saveFetchResult = { reservations ->
                db.withTransaction {
                    reservationsDao.deleteReservations()
                    reservations?.let {
                        reservationsDao.insertReservations(it)
                    }
                }
            }
        )

    private fun treeToList(tree: LinkedTreeMap<String, Reservation>?): List<Reservation>? {
        if (tree == null) return null

        val arrList = ArrayList<Reservation>()

        for ((key, value) in tree) {
            val reservation = Reservation(
                key,
                value.title,
                value.startTime,
                value.endTime
            )

            arrList.add(reservation)
        }

        return arrList.toList()
    }
}
