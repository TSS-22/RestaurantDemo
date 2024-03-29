package com.tss.restaurantdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tss.restaurantdemo.presentation.details.RestaurantDetailsViewModel
import com.tss.restaurantdemo.presentation.list.RestaurantsViewModel
import com.tss.restaurantdemo.presentation.details.RestaurantDetailsScreen
import com.tss.restaurantdemo.presentation.list.RestaurantsScreen
import com.tss.restaurantdemo.ui.theme.RestaurantDemoTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RestaurantDemoTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "restaurants"
                ) {
                    composable(route = "restaurants") {
                        val restaurantViewModel: RestaurantsViewModel = hiltViewModel()
                        RestaurantsScreen(
                            state = restaurantViewModel.state.value,
                            onItemClick = {id ->
                                navController.navigate("restaurants/$id")
                            },
                            onFavoriteClick = {id, isFavorite->
                                restaurantViewModel.toggleFavorite(id, isFavorite)
                            }
                        )
                    }
                    composable(
                        route = "restaurants/{restaurant_id}",
                        arguments = listOf(navArgument("restaurant_id") {
                            type = NavType.IntType
                        }),
//                        deepLinks = listOf(navDeepLink {
//                            uriPattern = "www.restaurantsapp.details.com/{restaurant_id}"
//                        })
                    ) {
                        val restaurantDetailsViewModel: RestaurantDetailsViewModel = hiltViewModel()
                        RestaurantDetailsScreen(
                            restaurantId = it.arguments?.getInt("restaurant_id"),
                            state = restaurantDetailsViewModel.state.value,
                            setId = restaurantDetailsViewModel::fetchRestaurantData
                        )
                    }
                }
            }
        }
    }
}
