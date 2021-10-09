package com.midyh.bookme.api

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.midyh.bookme.data.authentication.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsersApi {
    @GET("/users/{userUid}.json")
    suspend fun getUser(@Path("userUid") uid: String): User

    @PUT("/users/{userUid}.json")
    suspend fun putUser(@Path("userUid") uid: String, @Body user: User)
}
