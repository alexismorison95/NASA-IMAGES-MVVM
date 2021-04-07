package com.morris.nasaimages.modules.settings

import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.morris.nasaimages.R
import com.morris.nasaimages.utils.Utils

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity())

        sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->

            if (key == "theme") {

                sharedPreferences.getString(key, "")?.let { Utils.setTheme(it) }
            }
        }
    }
}