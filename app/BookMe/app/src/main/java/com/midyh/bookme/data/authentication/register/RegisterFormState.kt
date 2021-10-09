package com.midyh.bookme.data.authentication.register

data class RegisterFormState(
    val firstNameError: Int? = null,
    val lastNameError: Int? = null,
    val emailError: Int? = null,
    val phoneError: Int? = null,
    val passwordError: Int? = null,
    val passwordConfError: Int? = null,
    val isDataValid: Boolean = false
)
