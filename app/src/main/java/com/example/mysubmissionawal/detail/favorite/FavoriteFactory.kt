package com.example.mysubmissionawal.detail.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysubmissionawal.model.MainViewModel

class FavoriteFactory constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavoriteFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoriteFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteFactory::class.java) {
                    INSTANCE = FavoriteFactory(application)
                }
            }
            return INSTANCE as FavoriteFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Uknown ViewModel class: ${modelClass.name}")
    }
}