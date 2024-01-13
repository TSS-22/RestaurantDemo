package com.tss.restaurantdemo

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.tss.restaurantdemo.presentation.Description
import com.tss.restaurantdemo.presentation.list.RestaurantsScreen
import com.tss.restaurantdemo.presentation.list.RestaurantsScreenState
import com.tss.restaurantdemo.ui.theme.RestaurantDemoTheme
import org.junit.Rule
import org.junit.Test

class RestaurantsScreenTest {
    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun initialState_isRendered() {
        testRule.setContent {
            RestaurantDemoTheme {
                RestaurantsScreen(
                    state = RestaurantsScreenState(
                        restaurants = emptyList(),
                        isLoading = true
                    ),
                    onItemClick = {},
                    onFavoriteClick = { _: Int, _: Boolean -> }
                )
            }
        }
        testRule.onNodeWithContentDescription(
            Description.RESTAURANTS_LOADING
        ).assertIsDisplayed()
    }

    @Test
    fun stateWithContent_isRendered() {
        val restaurants = DummyContentRestaurants.getDomainRestaurants()
        testRule.setContent {
            RestaurantDemoTheme {
                RestaurantsScreen(
                    state = RestaurantsScreenState(
                        restaurants = restaurants,
                        isLoading = false
                    ),
                    onItemClick = {},
                    onFavoriteClick = { _: Int, _: Boolean -> }
                )
            }
        }
        testRule.onNodeWithText(
            restaurants[0].title
        ).assertIsDisplayed()
        testRule.onNodeWithText(
            restaurants[0].description
        ).assertIsDisplayed()
        testRule.onNodeWithContentDescription(
            Description.RESTAURANTS_LOADING
        ).assertDoesNotExist()
    }

    @Test
    fun stateWithError_isRendered() {
        val error = "UNKNOWN ERROR"
        testRule.setContent {
            RestaurantDemoTheme {
                RestaurantsScreen(
                    state = RestaurantsScreenState(
                        restaurants = emptyList(),
                        isLoading = false,
                        error = error
                    ),
                    onItemClick = {},
                    onFavoriteClick = {_: Int, _: Boolean ->}
                )
            }
        }
        testRule.onNodeWithContentDescription(
            Description.RESTAURANTS_LOADING
        ).assertDoesNotExist()
        testRule.onNodeWithText(
            error
        ).assertIsDisplayed()
    }

    @Test
    fun stateWithContent_ClickOnItem_isRegistered(){
        val restaurants = DummyContentRestaurants.getDomainRestaurants()
        val targetRestaurant = restaurants[0]

        testRule.setContent {
            RestaurantDemoTheme {
                RestaurantsScreen(
                    state = RestaurantsScreenState(
                        restaurants = restaurants,
                        isLoading = false
                    ),
                    onItemClick = {id -> assert(id == targetRestaurant.id) },
                    onFavoriteClick = {_: Int, _: Boolean, ->}
                )
            }
        }
        testRule.onNodeWithText(
            targetRestaurant.title
        ).performClick()
    }
}