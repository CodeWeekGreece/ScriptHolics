package com.midyh.bookme.data.authentication.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: Boolean? = null,
    val error: String? = null
)
