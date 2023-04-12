package com.example.mysubmissionawal.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingFactory(private val mApplication: ThemePreference) : ViewModelProvider.NewInstanceFactory()  {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Uknown ViewModel class:" + modelClass.name)
    }
}