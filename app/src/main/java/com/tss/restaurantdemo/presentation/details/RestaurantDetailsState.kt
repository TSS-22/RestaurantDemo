package com.tss.restaurantdemo.presentation.details

import com.tss.restaurantdemo.domain.Restaurant

data class RestaurantDetailsState(
    val restaurant: Restaurant? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
)
