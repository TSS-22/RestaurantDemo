package com.tss.restaurantdemo.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.tss.restaurantdemo.domain.Restaurant
import com.tss.restaurantdemo.presentation.Description
import com.tss.restaurantdemo.presentation.RestaurantDetails
import com.tss.restaurantdemo.presentation.RestaurantIcon


@Composable
fun RestaurantsScreen(
    state: RestaurantsScreenState,
    onItemClick: (id: Int) -> Unit,
    onFavoriteClick: (id: Int, isFavorite: Boolean) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                vertical = 8.dp,
                horizontal = 8.dp
            )
        ) {
            items(state.restaurants) { restaurant ->
                RestaurantItem(
                    item = restaurant,
                    onFavoriteClick = { id, isFavorite -> onFavoriteClick(id, isFavorite) },
                    onItemClick = {
                        onItemClick(restaurant.id)
                    }
                )
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.semantics {
                    this.contentDescription = Description.RESTAURANTS_LOADING
                }
            )
        }
        if (state.error != null) {
            Text(state.error)
        }
    }
}

@Composable
fun RestaurantItem(
    item: Restaurant,
    onFavoriteClick: (id: Int, isFavorite: Boolean) -> Unit = { _, _ -> },
    onItemClick: (id: Int) -> Unit
) {
    val iconFavorite = if (item.isFavorite) {
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onItemClick(item.id)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            RestaurantIcon(
                Icons.Filled.Place,
                Modifier.weight(0.15f),
            )
            RestaurantDetails(
                item.title,
                item.description,
                Modifier.weight(0.70f)
            )
            RestaurantIcon(
                iconFavorite,
                Modifier.weight(0.15f),
                item,
                onFavoriteClick,
            )
        }
    }
}