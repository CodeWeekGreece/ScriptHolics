package com.midyh.bookme.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.midyh.bookme.R
import com.midyh.bookme.ui.MainActivity
import com.midyh.bookme.ui.login.LoginActivity
import com.midyh.bookme.ui.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val sharedPreferences = this.getSharedPreferences("com.midyh.bookme", 0)

                if (sharedPreferences.getBoolean("FIRST", true)) {
                    startActivity(Intent(this, WelcomeActivity::class.java))
                    finish()
                    sharedPreferences.edit().putBoolean("FIRST", false).apply()
                } else {
                    if (auth.currentUser != null) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            },
            100
        )
    }
}
