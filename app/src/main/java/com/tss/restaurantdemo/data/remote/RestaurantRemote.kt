package com.tss.restaurantdemo.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RestaurantRemote(
    @SerialName("r_id")
    val id: Int,
    @SerialName("r_title")
    val title: String,
    @SerialName("r_description")
    val description: String,
    @SerialName("is_shutdown")
    var isShutdown: Boolean,
)
