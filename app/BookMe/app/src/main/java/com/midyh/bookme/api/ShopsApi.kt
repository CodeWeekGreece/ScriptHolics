package com.midyh.bookme.api

import com.google.gson.internal.LinkedTreeMap
import com.midyh.bookme.data.Shop
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ShopsApi {
    @GET("/shops/{category}.json")
    suspend fun getShops(
        @Path("category") category: String
    ): LinkedTreeMap<String, Shop>

    @PUT("/shops/{category}/{shopId}/ratings/{userId}.json")
    suspend fun rate(
        @Path("category") category: String,
        @Path("shopId") shopId: String,
        @Path("userId") userId: String,
        @Body rating: Int
    ): Response<*>
}
