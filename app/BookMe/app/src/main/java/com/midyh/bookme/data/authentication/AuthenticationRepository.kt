package com.midyh.bookme.data.authentication

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.midyh.bookme.api.UsersApi
import com.midyh.bookme.data.Result
import com.midyh.bookme.ui.login.LoginViewModel
import com.midyh.bookme.ui.register.RegisterViewModel
import kotlinx.coroutines.launch

object AuthenticationRepository {
    fun login(username: String, password: String, auth: FirebaseAuth, viewModel: LoginViewModel) {
        try {
            auth.signInWithEmailAndPassword(username, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val fireUser = auth.currentUser!!
                    val user = fireUser.email!!
                    viewModel.resultChanged(Result.Success(user))
                } else {
                    val error = task.exception
                    if (error is FirebaseAuthInvalidUserException || error is FirebaseAuthInvalidCredentialsException) {
                        viewModel.resultChanged(Result.Error("InvalidUser"))
                    }
                }
            }
        } catch (e: Throwable) {
            viewModel.resultChanged(Result.Error("Unknown Error"))
        }
    }

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String,
        auth: FirebaseAuth,
        api: UsersApi,
        db: UserDatabase,
        viewModel: RegisterViewModel
    ) {
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = User(
                        auth.currentUser!!.uid,
                        firstName,
                        lastName,
                        email,
                        phone,
                        "user"
                    )
                    val dao = db.userDao()

                    viewModel.viewModelScope.launch {
                        api.putUser(auth.currentUser!!.uid, user)
                        db.withTransaction {
                            dao.deleteUser()
                            dao.insertUser(user)
                        }
                        viewModel.resultChanged(Result.Success(user))
                    }
                } else {
                    when (task.exception) {
                        is FirebaseAuthUserCollisionException -> viewModel.resultChanged(
                            Result.Error(
                                "UserCollision"
                            )
                        )
                    }
                }
            }
        } catch (e: Throwable) {
            viewModel.resultChanged(Result.Error("Unknown Error") )
        }
    }
}
