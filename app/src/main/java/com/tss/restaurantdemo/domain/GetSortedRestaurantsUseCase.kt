package com.tss.restaurantdemo.domain

import com.tss.restaurantdemo.data.RestaurantsRepository
import javax.inject.Inject

class GetSortedRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository
) {
    suspend operator fun invoke(): List<Restaurant>{
        return repository.getRestaurants().sortedBy { it.title }
    }
}