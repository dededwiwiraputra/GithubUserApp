package com.example.mysubmissionawal.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ThemeViewModel (private val valueData: ThemePreference) : ViewModel()  {
    fun getThemeSettings(): LiveData<Boolean> {
        return valueData.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            valueData.saveThemeSetting(isDarkModeActive)
        }
    }
}