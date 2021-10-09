package com.midyh.bookme.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.google.firebase.auth.FirebaseAuth
import com.midyh.bookme.R
import com.midyh.bookme.api.UsersApi
import com.midyh.bookme.data.Result
import com.midyh.bookme.data.authentication.AuthenticationRepository
import com.midyh.bookme.data.authentication.User
import com.midyh.bookme.data.authentication.UserDatabase
import com.midyh.bookme.data.authentication.login.LoginFormState
import com.midyh.bookme.data.authentication.login.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val api: UsersApi,
    private val db: UserDatabase
) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _actionAllowed = MutableLiveData(false)
    val actionAllowed: LiveData<Boolean> = _actionAllowed

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String, viewModel: LoginViewModel) {
        _actionAllowed.value = false
        AuthenticationRepository.login(username, password, auth, viewModel)
    }

    fun resultChanged(result: Result<String>) {
        if (result is Result.Success) {
            _loginResult.value = LoginResult(true)
            saveUser(auth.currentUser!!.uid)
        } else if (result is Result.Error) {
            _loginResult.value = LoginResult(error = result.exception)
        }
    }

    private fun saveUser(userUid: String) {
        val dao = db.userDao()

        viewModelScope.launch {
            val userToCache = api.getUser(userUid)
            userToCache.uid = auth.currentUser!!.uid
            _user.value = userToCache

            db.withTransaction {
                dao.deleteUser()
                dao.insertUser(userToCache)
            }

            _actionAllowed.value = true
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
            _actionAllowed.value = false
        } else if (password.length < 6 || password.isEmpty()) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
            _actionAllowed.value = false
        } else {
            _actionAllowed.value = true
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }
}
