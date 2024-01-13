package com.tss.restaurantdemo.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tss.restaurantdemo.presentation.RestaurantDetails
import com.tss.restaurantdemo.presentation.RestaurantIcon

@Composable
fun RestaurantDetailsScreen(
    restaurantId: Int?,
    state: RestaurantDetailsState,
    setId: (Int) -> Unit
) {
    if(restaurantId != null) {
        setId(restaurantId)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        if (state.restaurant != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RestaurantIcon(
                    icon = Icons.Filled.Place,
                    modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
                )
                RestaurantDetails(
                    title = state.restaurant.title,
                    description = state.restaurant.description,
                    modifier = Modifier.padding(bottom = 32.dp),
                    Alignment.CenterHorizontally
                )
                Text(text = "More info coming soon!")
            }
        }
        if (state.isLoading){
            CircularProgressIndicator()
        }
        if (state.error != null){
            Text(text = state.error)
        }
    }
}