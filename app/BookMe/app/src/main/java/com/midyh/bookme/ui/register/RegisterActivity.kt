package com.midyh.bookme.ui.register

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.midyh.bookme.R
import com.midyh.bookme.databinding.ActivityRegisterBinding
import com.midyh.bookme.ui.MainActivity
import com.midyh.bookme.ui.login.LoginActivity
import com.midyh.bookme.ui.login.afterTextChanged
import com.midyh.bookme.util.ContextUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firstName = binding.firstName
        val firstNameContainer = binding.firstNameContainer

        val lastName = binding.lastName
        val lastNameContainer = binding.lastNameContainer

        val email = binding.email
        val emailContainer = binding.emailContainer

        val phone = binding.phone
        val phoneContainer = binding.phoneContainer

        val password = binding.password
        val passwordContainer = binding.passwordContainer

        val passwordConf = binding.passwordConf
        val passwordConfContainer = binding.passwordConfContainer

        val registerButton = binding.registerButton
        val alreadyAccount = binding.alreadyAccount
        val loading = binding.loading

        val alreadyAccountText = SpannableStringBuilder()
        alreadyAccountText.append("Already have an account? ")
        val start = alreadyAccountText.length
        alreadyAccountText.append("Sign up!")
        alreadyAccountText.setSpan(
            ForegroundColorSpan(0xFF6200EE.toInt()),
            start,
            alreadyAccountText.length,
            0
        )

        alreadyAccount.text = alreadyAccountText

        val isSecond = intent.getBooleanExtra("isSecond", false)

        if (isSecond) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            alreadyAccount.visibility = View.GONE
        }

        viewModel.registerForm.observe(
            this@RegisterActivity,
            Observer {
                val regState = it ?: return@Observer

                registerButton.isEnabled = regState.isDataValid

                if (regState.firstNameError != null) {
                    firstNameContainer.error = getString(R.string.invalid_fname)
                } else firstNameContainer.error = null

                if (regState.lastNameError != null) {
                    lastNameContainer.error = getString(R.string.invalid_lname)
                } else lastNameContainer.error = null

                if (regState.emailError != null) {
                    emailContainer.error = getString(R.string.invalid_email)
                } else emailContainer.error = null

                if (regState.passwordError != null) {
                    passwordContainer.error = getString(R.string.invalid_password)
                    passwordContainer.errorIconDrawable = null
                } else {
                    passwordContainer.error = null
                    passwordContainer.errorIconDrawable = null
                }

                if (regState.passwordConfError != null) {
                    passwordConfContainer.error = getString(R.string.invalid_passConf)
                    passwordConfContainer.errorIconDrawable = null
                } else {
                    passwordConfContainer.error = null
                    passwordConfContainer.errorIconDrawable = null
                }

                if (regState.phoneError != null) {
                    phoneContainer.error = getString(R.string.invalid_phone)
                } else phoneContainer.error = null
            }
        )

        viewModel.registerResult.observe(
            this@RegisterActivity,
            Observer {
                val registerResult = it ?: return@Observer

                loading.visibility = View.GONE
                if (registerResult.error != null) {
                    when (registerResult.error) {
                        "UserCollision" -> emailContainer.error = "Email already in use"
                    }

                    registerButton.isEnabled = true
                } else {
                    emailContainer.error = null
                }

                if (registerResult.success != null) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
                setResult(Activity.RESULT_OK)
            }
        )

        firstName.afterTextChanged {
            viewModel.registerDataChanged(
                firstName.text.toString(),
                lastName.text.toString(),
                email.text.toString(),
                phone.text.toString(),
                password.text.toString(),
                passwordConf.text.toString(),
            )
        }

        lastName.afterTextChanged {
            viewModel.registerDataChanged(
                firstName.text.toString(),
                lastName.text.toString(),
                email.text.toString(),
                phone.text.toString(),
                password.text.toString(),
                passwordConf.text.toString(),
            )
        }

        email.afterTextChanged {
            viewModel.registerDataChanged(
                firstName.text.toString(),
                lastName.text.toString(),
                email.text.toString(),
                phone.text.toString(),
                password.text.toString(),
                passwordConf.text.toString(),
            )
        }

        phone.afterTextChanged {
            viewModel.registerDataChanged(
                firstName.text.toString(),
                lastName.text.toString(),
                email.text.toString(),
                phone.text.toString(),
                password.text.toString(),
                passwordConf.text.toString(),
            )
        }

        password.afterTextChanged {
            viewModel.registerDataChanged(
                firstName.text.toString(),
                lastName.text.toString(),
                email.text.toString(),
                phone.text.toString(),
                password.text.toString(),
                passwordConf.text.toString(),
            )
        }

        passwordConf.apply {
            afterTextChanged {
                viewModel.registerDataChanged(
                    firstName.text.toString(),
                    lastName.text.toString(),
                    email.text.toString(),
                    phone.text.toString(),
                    password.text.toString(),
                    passwordConf.text.toString(),
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        loading.visibility = View.VISIBLE
                        registerButton.isEnabled = false
                        viewModel.register(
                            firstName.text.toString(),
                            lastName.text.toString(),
                            email.text.toString(),
                            phone.text.toString(),
                            password.text.toString(),
                            viewModel
                        )
                    }
                }
                false
            }

            registerButton.setOnClickListener {
                loading.visibility = View.VISIBLE
                registerButton.isEnabled = false
                viewModel.register(
                    firstName.text.toString(),
                    lastName.text.toString(),
                    email.text.toString(),
                    phone.text.toString(),
                    password.text.toString(),
                    viewModel
                )
            }
        }

        alreadyAccount.setOnClickListener {
            if (!isSecond) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("isSecond", true)
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }

    override fun attachBaseContext(newBase: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(newBase)
        val language = prefs.getString("language", "en").toString()
        val localeToSwitchTo = Locale(language)
        val localeUpdatedContext: ContextWrapper =
            ContextUtils.updateLocale(newBase, localeToSwitchTo)
        super.attachBaseContext(localeUpdatedContext)
    }
}
