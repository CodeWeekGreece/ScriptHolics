package com.midyh.bookme.api

import com.google.gson.internal.LinkedTreeMap
import com.midyh.bookme.data.Slot
import com.midyh.bookme.data.reservations.ClearReservation
import com.midyh.bookme.data.reservations.Reservation
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReservationsApi {
    @GET("/users/{userUid}/reservations.json")
    suspend fun getReservations(
        @Path("userUid") userUid: String
    ): Response<LinkedTreeMap<String, Reservation>?>?

    @DELETE("/users/{userUid}/reservations/{resUid}.json")
    suspend fun deleteReservation(
        @Path("userUid") userUid: String,
        @Path("resUid") resUid: String
    ): Response<*>

    @PUT("/users/{userUid}/reservations/{resUid}.json")
    suspend fun addReservation(
        @Path("userUid") userUid: String,
        @Path("resUid") resUid: String,
        @Body reservation: Reservation
    ): Response<*>

    @PATCH("/shops/{category}/{shopId}/slots/{slotUid}.json")
    suspend fun bookReservation(
        @Path("category") category: String,
        @Path("shopId") shopId: String,
        @Path("slotUid") slotUid: String,
        @Body slot: Slot,
    ): Response<*>

    @PATCH("/shops/{category}/{shopUid}/slots/{slotUid}.json")
    suspend fun cancelReservation(
        @Path("category") category: String,
        @Path("shopUid") shopUid: String,
        @Path("slotUid") slotUid: String,
        @Body slot: ClearReservation,
    ): Response<*>
}
