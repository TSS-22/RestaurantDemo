package com.tss.restaurantdemo.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class PartialRestaurantLocal (
    @ColumnInfo(name = "r_id")
    val id: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
)