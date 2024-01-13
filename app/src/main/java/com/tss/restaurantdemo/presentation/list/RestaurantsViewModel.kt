package com.tss.restaurantdemo.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tss.restaurantdemo.domain.GetInitialRestaurantsUseCase
import com.tss.restaurantdemo.domain.ToggleRestaurantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor (
    private val getRestaurantUseCase: GetInitialRestaurantsUseCase,
    private val toggleRestaurantUseCase: ToggleRestaurantUseCase,
) : ViewModel() {


    private val _state = mutableStateOf(
        RestaurantsScreenState(
            restaurants = listOf(),
            isLoading = true,
        )
    )
    val state: State<RestaurantsScreenState>
        get() = _state

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _state.value = _state.value.copy(
            error = exception.message,
            isLoading = false
        )
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getRestaurants()
        }
    }

    private suspend fun getRestaurants() {
        viewModelScope.launch(errorHandler) {
            _state.value = _state.value.copy(
                restaurants = getRestaurantUseCase(),
                isLoading = false
            )
        }
    }

    fun toggleFavorite(id: Int, value: Boolean) {
        viewModelScope.launch {
            val updatedRestaurant = toggleRestaurantUseCase(id, value)
            _state.value = _state.value.copy(
                restaurants = updatedRestaurant
            )
        }
    }
}