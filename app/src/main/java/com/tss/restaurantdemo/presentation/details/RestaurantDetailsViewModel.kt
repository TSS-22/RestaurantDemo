package com.tss.restaurantdemo.presentation.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tss.restaurantdemo.domain.GetRestaurantById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
    private val getRestaurantById: GetRestaurantById
) : ViewModel() {

    private val _state = mutableStateOf(RestaurantDetailsState())
    val state: State<RestaurantDetailsState>
        get() = _state

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _state.value = _state.value.copy(
            error = exception.message,
            isLoading = false
        )
    }

    fun fetchRestaurantData(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            getRestaurantData(id)
        }
    }
    private suspend fun getRestaurantData(restaurantId: Int) {
        viewModelScope.launch(errorHandler) {
            _state.value = _state.value.copy(
                restaurant = getRestaurantById(restaurantId),
                isLoading = false
            )
        }
    }


}