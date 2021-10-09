package com.midyh.bookme.ui.reservations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.internal.LinkedTreeMap
import com.midyh.bookme.api.ReservationsApi
import com.midyh.bookme.data.reservations.ClearReservation
import com.midyh.bookme.data.reservations.Reservation
import com.midyh.bookme.data.reservations.ReservationsDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationsViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val api: ReservationsApi,
    private val db: ReservationsDatabase
) : ViewModel() {
    private val _reservations = MutableLiveData<ArrayList<Reservation>>()
    val reservations: LiveData<ArrayList<Reservation>> = _reservations

    fun getReservations() {
        viewModelScope.launch {
            val res = api.getReservations(auth.currentUser!!.uid)
            res?.body()?.let { data ->
                _reservations.value = toArrayList(data)!!
            } ?: run {
                _reservations.value = ArrayList()
            }
        }
    }

    fun deleteReservation(res: Reservation) {
        viewModelScope.launch {
            api.deleteReservation(auth.currentUser!!.uid, res.uid)
            api.cancelReservation(
                res.category!!,
                res.shopUid!!,
                res.slotUid!!,
                ClearReservation()
            )

            db.withTransaction {
                db.reservationsDao().deleteReservation(res.uid)
            }

            getReservations()
        }
    }

    private fun toArrayList(map: LinkedTreeMap<String, Reservation>?): ArrayList<Reservation>? {
        val arrayList = ArrayList<Reservation>()

        map?.let {
            for ((id, value) in it) {
                val reservation = Reservation(
                    id,
                    value.title,
                    value.startTime,
                    value.endTime,
                    value.category,
                    value.shopUid,
                    value.slotUid,
                )

                arrayList.add(reservation)
            }

            return arrayList
        } ?: return null
    }
}
