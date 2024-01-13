package com.tss.restaurantdemo.domain

import com.tss.restaurantdemo.data.RestaurantsRepository
import javax.inject.Inject

class ToggleRestaurantUseCase @Inject constructor(
    private val repository: RestaurantsRepository,
    private val getSortedRestaurantsUseCase: GetSortedRestaurantsUseCase
) {
    suspend operator fun invoke(
        id: Int,
        isFavorite: Boolean
    ): List<Restaurant> {
        repository.toggleFavoriteRestaurant(id, isFavorite.not())
        return getSortedRestaurantsUseCase()
    }
}