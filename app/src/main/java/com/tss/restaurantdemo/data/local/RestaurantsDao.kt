package com.tss.restaurantdemo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tss.restaurantdemo.data.local.PartialRestaurantLocal
import com.tss.restaurantdemo.data.local.RestaurantLocal

@Dao
interface RestaurantsDao {
    @Query("SELECT * FROM restaurants;")
    suspend fun getAll(): List<RestaurantLocal>

    @Query("SELECT * FROM restaurants WHERE r_id=:id;")
    suspend fun getRestaurant(id: Int): List<RestaurantLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(restaurants: List<RestaurantLocal>)

    @Update(entity = RestaurantLocal::class)
    suspend fun update(partialRestaurant: PartialRestaurantLocal)

    @Update(entity = RestaurantLocal::class)
    suspend fun updateAll(partialRestaurants: List<PartialRestaurantLocal>)

    @Query("SELECT * FROM restaurants WHERE is_favorite = 1;")
    suspend fun getAllFavorited(): List<RestaurantLocal>
}