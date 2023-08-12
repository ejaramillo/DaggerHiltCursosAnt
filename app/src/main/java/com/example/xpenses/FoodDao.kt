package com.example.xpenses

import androidx.room.Insert
import androidx.room.Query

interface FoodDao {
    @Query("SELECT * FROM FoodEntity")
    suspend fun getAllFood(): List<FoodEntity>

    @Insert
    suspend fun addFood(foodEntity: FoodEntity): Long
}
