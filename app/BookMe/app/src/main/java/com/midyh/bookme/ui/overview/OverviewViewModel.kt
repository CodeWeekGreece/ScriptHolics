package com.midyh.bookme.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.google.firebase.auth.FirebaseAuth
import com.midyh.bookme.api.ReservationsApi
import com.midyh.bookme.data.Owner
import com.midyh.bookme.data.Slot
import com.midyh.bookme.data.authentication.UserDatabase
import com.midyh.bookme.data.reservations.Reservation
import com.midyh.bookme.domain.model.Rate
import com.midyh.bookme.domain.usecases.RateUseCase
import com.midyh.bookme.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val api: ReservationsApi,
    private val auth: FirebaseAuth,
    private val userDb: UserDatabase,
    private val rateUseCase: RateUseCase,
) : ViewModel() {
    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _slots = MutableLiveData<ArrayList<Slot>>()
    val slots: LiveData<ArrayList<Slot>> = _slots

    private val _userId = MutableLiveData(auth.currentUser!!.uid)
    val userId: LiveData<String> = _userId

    private val _rateState = MutableLiveData(Rate(isLoading = false))
    val rateState: LiveData<Rate> = _rateState

    fun setSlots(slots: ArrayList<Slot>) {
        _slots.value = slots
    }

    fun rateRestaurant(
        category: String,
        shopId: String,
        rating: Int
    ) {
        rateUseCase(category, shopId, auth.currentUser!!.uid, rating).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _rateState.value = Rate(isLoading = false)
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                    _rateState.value = Rate(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun putReservation(
        reservation: Reservation,
        slot: Slot,
        category: String,
        shopId: String,
    ) {
        viewModelScope.launch {
            val dao = userDb.userDao()

            val uid = UUID.randomUUID().toString()
            val readyReservation = Reservation(
                uid,
                reservation.title,
                reservation.startTime,
                reservation.endTime,
                category.lowercase(),
                shopId,
                slot.uid,
            )

            userDb.withTransaction {
                val user = dao.getUser()
                val readySlot = Slot(
                    slot.uid,
                    false,
                    slot.startTime,
                    slot.endTime,
                    Owner(
                        "${user.firstName} ${user.lastName}",
                        user.phone
                    ),
                )
                api.addReservation(auth.currentUser!!.uid, uid, readyReservation)
                api.bookReservation(
                    category.lowercase(),
                    shopId,
                    slot.uid!!,
                    readySlot
                )
            }

            _success.value = when (_success.value) {
                true -> false
                false -> true
                else -> true
            }
        }
    }
}
