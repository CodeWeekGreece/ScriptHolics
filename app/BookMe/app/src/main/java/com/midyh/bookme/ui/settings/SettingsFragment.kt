package com.midyh.bookme.ui.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth
import com.midyh.bookme.R
import com.midyh.bookme.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val logoutBtn: Preference? = findPreference("logout")
        val langSelector: ListPreference? = findPreference("language")

        logoutBtn?.setOnPreferenceClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finishAffinity()
            true
        }

        langSelector?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, _ ->
                requireActivity().finish()
                startActivity(requireActivity().intent)
                true
            }
    }
}
