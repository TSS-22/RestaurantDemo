package com.tss.restaurantdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RestaurantLocal::class],
    version = 5,
    exportSchema = false
)
abstract class RestaurantsDb : RoomDatabase() {
    abstract val dao: RestaurantsDao
}