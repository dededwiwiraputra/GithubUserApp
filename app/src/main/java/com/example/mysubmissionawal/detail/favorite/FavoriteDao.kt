package com.example.mysubmissionawal.detail.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(user: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity WHERE FavoriteEntity.id = :id")
    fun removeFavorite(id: Int)

    @Query("SELECT * FROM FavoriteEntity ORDER BY login ASC")
    fun getAllUser(): LiveData<List<FavoriteEntity>>
}