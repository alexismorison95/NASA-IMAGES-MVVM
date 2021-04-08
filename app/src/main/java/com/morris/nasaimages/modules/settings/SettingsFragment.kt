package com.morris.nasaimages.modules.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.morris.nasaimages.R
import com.morris.nasaimages.utils.Utils

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity())

        setListeners(sharedPreferences)

        // TODO: Agregar cache size
    }

    private fun setListeners(sharedPreferences: SharedPreferences) {

        sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->

            if (key == "theme") {

                sharedPreferences.getString(key, "")?.let { Utils.setTheme(it) }
            }
        }

        val btnCache = findPreference<Preference>("btncache")

        btnCache?.setOnPreferenceClickListener {

            val result = context?.cacheDir!!.deleteRecursively()

            if (result) {

                Toast.makeText(requireContext(), "Delete cache successfully", Toast.LENGTH_SHORT).show()
            }

            true
        }
    }
}