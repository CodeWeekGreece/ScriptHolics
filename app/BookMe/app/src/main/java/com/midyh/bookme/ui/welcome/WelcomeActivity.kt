package com.midyh.bookme.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.appcompat.app.AppCompatActivity
import com.midyh.bookme.databinding.ActivityWelcomeBinding
import com.midyh.bookme.ui.login.LoginActivity
import com.midyh.bookme.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getStarted = binding.getStarted
        val loginText = binding.loginText

        getStarted.setOnClickListener {
            startActivity(
                Intent(
                    this, RegisterActivity::class.java
                ).putExtra("isSecond", false)
            )
            finish()
        }

        val loginSpannable = SpannableStringBuilder()
        loginSpannable.append("Already have an account? ")
        val start = loginSpannable.length
        loginSpannable.append("Sign In")
        loginSpannable.setSpan(
            ForegroundColorSpan(0xFF3700B3.toInt()),
            start,
            loginSpannable.length,
            0
        )
        loginSpannable.setSpan(UnderlineSpan(), start, loginSpannable.length, 0)
        loginText.text = loginSpannable

        loginText.setOnClickListener {
            startActivity(
                Intent(
                    this, LoginActivity::class.java
                ).putExtra("isSecond", false)
            )
            finish()
        }
    }
}
