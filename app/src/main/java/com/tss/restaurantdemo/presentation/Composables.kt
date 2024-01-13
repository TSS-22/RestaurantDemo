package com.tss.restaurantdemo.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.tss.restaurantdemo.domain.Restaurant

@Composable
fun RestaurantIcon(
    icon: ImageVector,
    modifier: Modifier,
    restaurant: Restaurant? = null,
    onClick: (Int, Boolean) -> Unit = {_, _ ->}
) {
    Image(
        imageVector = icon,
        contentDescription = "Restaurant icon",
        modifier = modifier
            .padding(8.dp)
            .clickable {
                if(restaurant != null) {
                    onClick(restaurant.id, restaurant.isFavorite)
                }
            }
    )
}

@Composable
fun RestaurantDetails(
    title: String,
    description: String,
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
        // https://stackoverflow.com/questions/72574071/unable-to-change-text-emphasis-using-localcontentalpha-in-material-design-3
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}