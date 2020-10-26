package ru.bmstu.iu9.rk1

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.*


class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var settingsFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        settingsFragment = SettingsFragment()
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_settings, settingsFragment)
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, s: String?) {
        val preference: Preference? = s?.let { settingsFragment.findPreference(it) }
        if (preference != null) {
            if (preference is ListPreference || preference is EditTextPreference) {
                val value: String? = sharedPreferences?.getString(preference.key, "")
                if (value != null) {
                    setPreferenceSummary(preference, value)
                }
            }
        }
    }

    private fun setPreferenceSummary(
        preference: Preference,
        value: String
    ) {
        if (preference is ListPreference) {
            val listPreference: ListPreference = preference
            val prefIndex: Int = listPreference.findIndexOfValue(value)
            if (prefIndex >= 0) {
                listPreference.summary = listPreference.entries[prefIndex]
            }
            Toast.makeText(this, "Please, restart app!", Toast.LENGTH_LONG).show()
        } else if (preference is EditTextPreference) {
            val ep: EditTextPreference = preference
            ep.summary = ep.text
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        private var sharedPreferences: SharedPreferences? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_fragment)

            val editPreference: EditTextPreference? =
                preferenceManager.findPreference("text") as EditTextPreference?
            editPreference?.onPreferenceChangeListener = object :
                Preference.OnPreferenceChangeListener {
                override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                    val value = Integer.parseInt(newValue as String)
                    if (value < 0) {
                        Toast.makeText(context, "Enter positive number", Toast.LENGTH_SHORT).show()
                        return false
                    }
                    return true
                }
            }
            val preferenceScreen: PreferenceScreen = preferenceScreen
            sharedPreferences = preferenceScreen.sharedPreferences
            editPreference.setPreferenceSummary("")
        }

        private fun Preference?.setPreferenceSummary(
            value: String?
        ) {
            if (this != null) {
                if (this is ListPreference) {
                    val listPreference: ListPreference = this
                    val prefIndex: Int = listPreference.findIndexOfValue(value)
                    if (prefIndex >= 0) {
                        listPreference.summary = listPreference.entries[prefIndex]
                    }
                } else if (this is EditTextPreference) {
                    val editTextPreference: EditTextPreference = this
                    val string: String = editTextPreference.text
                    editTextPreference.summary = string
                }
            }
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//            setPreferencesFromResource(R.xml.pref_fragment, rootKey)
        }
    }

}