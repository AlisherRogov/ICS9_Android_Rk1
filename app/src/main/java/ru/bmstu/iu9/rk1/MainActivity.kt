package ru.bmstu.iu9.rk1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = this.findNavController(R.id.test_nav_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.test_nav_fragment)
        return navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, p1: String?) {
        CURRENT_CURRENCY = sharedPreferences?.getString("list", "USD").toString()
        if (sharedPreferences != null) {
            DAYS_TO_SHOW_NUMBER =
                Integer.parseInt(sharedPreferences.getString("text", "10").toString())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    companion object {
        private var CURRENT_CURRENCY: String = "USD"
        private var DAYS_TO_SHOW_NUMBER: Int = 10

        fun getCurrentCurrency() = CURRENT_CURRENCY
        fun getDaysNumber() = DAYS_TO_SHOW_NUMBER
    }

}