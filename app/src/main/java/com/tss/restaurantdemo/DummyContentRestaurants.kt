package com.tss.restaurantdemo

import com.tss.restaurantdemo.domain.Restaurant

object DummyContentRestaurants {
    fun getDomainRestaurants() = arrayListOf<Restaurant>(
        Restaurant(0, "title0", "description0", false),
        Restaurant(1, "title1", "description1", false),
        Restaurant(2, "title2", "description2", false),
        Restaurant(3, "title3", "description3", false),
        Restaurant(4, "title4", "description4", false),
        Restaurant(5, "title5", "description5", false),
    )
}