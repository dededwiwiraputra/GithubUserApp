package com.example.mysubmissionawal.detail.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteUserDao()
    }

    fun getAllFavorites(): LiveData<List<FavoriteEntity>> = mFavoriteDao.getAllUser()

    fun insert(user: FavoriteEntity) {
        executorService.execute { mFavoriteDao.insertFavorite(user) }
    }

    fun delete(id: Int) {
        executorService.execute { mFavoriteDao.removeFavorite(id) }
    }
}