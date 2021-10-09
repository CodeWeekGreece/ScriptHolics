package com.midyh.bookme.data.authentication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey var uid: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val userType: String,
)
