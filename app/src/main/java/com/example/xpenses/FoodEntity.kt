package com.example.xpenses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FoodEntity")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    var price: Double = 0.0,
    var name: String = "",
    var type: Long = 0
)
