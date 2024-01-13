package com.tss.restaurantdemo.domain

import com.tss.restaurantdemo.data.RestaurantDetailsRepository
import javax.inject.Inject

class GetRestaurantById @Inject constructor(
    private val repository: RestaurantDetailsRepository,
    ) {
    suspend operator fun invoke(
        restaurantId: Int,
    ): Restaurant {
        return repository.getRestaurant(restaurantId)
    }
}