package com.midyh.bookme.ui.login

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.midyh.bookme.databinding.ActivityLoginBinding
import com.midyh.bookme.ui.MainActivity
import com.midyh.bookme.ui.register.RegisterActivity
import com.midyh.bookme.util.ContextUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isSecond = intent.getBooleanExtra("isSecond", false)
        var actionAllowed = false

        val email = binding.email
        val emailContainer = binding.emailContainer
        val password = binding.password
        val passwordContainer = binding.passwordContainer
        val login = binding.loginBtn
        val loading = binding.loading
        val noAccount = binding.noAccount

        val noAccountText = SpannableStringBuilder()
        noAccountText.append("Don't have an account? ")
        val start = noAccountText.length
        noAccountText.append("Sign up!")
        noAccountText.setSpan(
            ForegroundColorSpan(0xFF6200EE.toInt()),
            start,
            noAccountText.length,
            0
        )

        noAccount.text = noAccountText

        if (isSecond) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            noAccount.visibility = View.GONE
        }

        viewModel.actionAllowed.observe(
            this@LoginActivity,
            {
                actionAllowed = it
                login.isEnabled = it
            }
        )

        viewModel.user.observe(
            this@LoginActivity,
            {
                loading.visibility = View.GONE

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        )

        viewModel.loginFormState.observe(
            this@LoginActivity,
            Observer {
                val loginState = it ?: return@Observer

                if (loginState.emailError != null) {
                    emailContainer.error = getString(loginState.emailError)
                } else emailContainer.error = null

                if (loginState.passwordError != null) {
                    passwordContainer.error = getString(loginState.passwordError)
                } else passwordContainer.error = null
            }
        )

        viewModel.loginResult.observe(
            this@LoginActivity,
            Observer {
                val loginResult = it ?: return@Observer

                if (loginResult.error != null) {
                    loading.visibility = View.GONE
                    if (loginResult.error == "InvalidUser") {
                        emailContainer.error = "Email or password may be invalid"
                    }
                } else {
                    emailContainer.error = null
                }

                setResult(Activity.RESULT_OK)
            }
        )

        email.afterTextChanged {
            viewModel.loginDataChanged(
                email.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                viewModel.loginDataChanged(
                    email.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                if (actionAllowed) {
                    when (actionId) {
                        EditorInfo.IME_ACTION_DONE -> {
                            loading.visibility = View.VISIBLE
                            login.isEnabled = false
                            viewModel.login(
                                email.text.toString(),
                                password.text.toString(),
                                viewModel
                            )
                        }
                    }
                }
                false
            }
        }

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            viewModel.login(
                email.text.toString(),
                password.text.toString(),
                viewModel
            )
        }

        noAccount.setOnClickListener {
            if (!isSecond) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
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

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
