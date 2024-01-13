package com.tss.restaurantdemo.presentation.list

import com.tss.restaurantdemo.domain.Restaurant

data class RestaurantsScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null,
)
