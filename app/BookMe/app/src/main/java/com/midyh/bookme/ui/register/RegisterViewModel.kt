package com.midyh.bookme.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.midyh.bookme.R
import com.midyh.bookme.api.UsersApi
import com.midyh.bookme.data.Result
import com.midyh.bookme.data.authentication.AuthenticationRepository
import com.midyh.bookme.data.authentication.User
import com.midyh.bookme.data.authentication.UserDatabase
import com.midyh.bookme.data.authentication.register.RegisterFormState
import com.midyh.bookme.data.authentication.register.RegisterResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val api: UsersApi,
    private val auth: FirebaseAuth,
    private val db: UserDatabase
) : ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerForm: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun registerDataChanged(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String,
        passwordConf: String,
    ) {
        if (firstName.isEmpty()) {
            _registerForm.value = RegisterFormState(firstNameError = R.string.invalid_fname)
        } else if (lastName.isEmpty()) {
            _registerForm.value = RegisterFormState(lastNameError = R.string.invalid_lname)
        } else if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
        } else if (phone.length > 10) {
            _registerForm.value = RegisterFormState(phoneError = R.string.invalid_phone)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else if (password != passwordConf) {
            _registerForm.value = RegisterFormState(passwordConfError = R.string.invalid_passConf)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String,
        registerViewModel: RegisterViewModel
    ) {
        AuthenticationRepository.register(
            firstName,
            lastName,
            email,
            phone,
            password,
            auth,
            api,
            db,
            registerViewModel,
        )
    }

    fun resultChanged(result: Result<User>) {
        if (result is Result.Success) {
            _registerResult.value = RegisterResult(success = "success")
        } else {
            _registerResult.value = RegisterResult(error = (result as Result.Error).exception)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 8
    }
}
