package com.tss.restaurantdemo.data


import com.tss.restaurantdemo.data.local.RestaurantsDao
import com.tss.restaurantdemo.data.remote.RestaurantRemote
import com.tss.restaurantdemo.domain.Restaurant
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantDetailsRepository @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val restaurantsDao: RestaurantsDao
) {
    suspend fun getRestaurant(id: Int): Restaurant {
        return withContext(Dispatchers.IO) {
            val restaurant = restaurantsDao.getRestaurant(id)
            if(restaurant.isEmpty()) {
                try {
                    return@withContext getRemoteRestaurant(id)
                } catch (e: Exception) {
                    when(e) {
                        is UnknownHostException,
                        is ConnectException,
                        is HttpException -> {
                            throw Exception("Something went wrong. We have no data")
                        }
                        else -> throw e
                    }
                }
            } else {
                return@withContext Restaurant(
                    id = restaurant.first().id,
                    title = restaurant.first().title,
                    description = restaurant.first().description,
                    isFavorite = restaurant.first().isFavorite,
                )
            }
        }
    }

    private suspend fun getRemoteRestaurant(id: Int): Restaurant {
        val restaurantDto = supabaseClient.from("restaurants")
            .select {
                filter { eq("r_id", id) }
            }.decodeList<RestaurantRemote>()

        val restaurant = restaurantDto.map {
            Restaurant(
                id = it.id,
                title = it.title,
                description = it.description
            )
        }
        return restaurant.first()
    }
}