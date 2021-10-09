package com.midyh.bookme.data.reservations

import com.midyh.bookme.data.Owner

data class ClearReservation(val available: Boolean = true, val owner: Owner? = Owner())
