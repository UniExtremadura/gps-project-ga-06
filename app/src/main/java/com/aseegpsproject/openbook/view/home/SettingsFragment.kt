package com.aseegpsproject.openbook.view.home

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.data.model.User

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference<Preference>("remember_me")?.setOnPreferenceChangeListener { _: Preference, newValue: Any ->
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(
                context ?: return@setOnPreferenceChangeListener true
            )
            val editor = sharedPref.edit()
            if (newValue as Boolean) {
                val user = activity?.intent?.getSerializableExtra("USER_INFO") as User
                editor.putString("username", user.username)
                editor.putString("password", user.password)
            } else {
                editor.remove("username")
                editor.remove("password")
            }
            editor.apply()
            true
        }
    }
}