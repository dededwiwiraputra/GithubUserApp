package com.example.mysubmissionawal.theme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mysubmissionawal.R
import com.example.mysubmissionawal.databinding.ActivityFavoriteBinding
import com.example.mysubmissionawal.databinding.ActivityMainBinding
import com.example.mysubmissionawal.databinding.ActivityThemeBinding
import com.example.mysubmissionawal.model.MainViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThemeActivity : AppCompatActivity() {
    private lateinit  var _binding: ActivityThemeBinding
    private lateinit var themeViewModel: ThemeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        supportActionBar?.title = "Setting"

        val preference = ThemePreference.getInstance(dataStore)

        themeViewModel = ViewModelProvider(
            this,
            SettingFactory(preference)
        )[ThemeViewModel::class.java]

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        themeViewModel.getThemeSettings().observe(this) {
            _binding.apply {
                if (it) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            }
        }

        _binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}