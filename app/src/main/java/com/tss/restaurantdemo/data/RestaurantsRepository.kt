package com.tss.restaurantdemo.data


import com.tss.restaurantdemo.data.local.PartialRestaurantLocal
import com.tss.restaurantdemo.data.local.RestaurantLocal
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
class RestaurantsRepository @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val restaurantsDao: RestaurantsDao
) {

    suspend fun loadRestaurants() {
        return withContext(Dispatchers.IO) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if (restaurantsDao.getAll().isEmpty()) {
                            throw Exception(
                                "Something went wrong. We have no data"
                            )
                        }
                    }
                    else -> throw e
                }
            }
        }
    }

    suspend fun getRestaurants(): List<Restaurant>{
        return withContext(Dispatchers.IO){
            return@withContext restaurantsDao.getAll().map{
                Restaurant(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    isFavorite = it.isFavorite
                )
            }
        }
    }

    private suspend fun refreshCache() {
        val restaurantsDto = supabaseClient.from("restaurants")
            .select().decodeList<RestaurantRemote>()

        val restaurants = restaurantsDto.map {
            Restaurant(
                id = it.id,
                title = it.title,
                description = it.description
            )
        }
        restaurantsDao.addAll(
            restaurants.map{
                RestaurantLocal(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    isFavorite = it.isFavorite
                )
            }
        )
        val favoriteRestaurants = restaurantsDao.getAllFavorited()
        restaurantsDao.updateAll(
            favoriteRestaurants.map {
                PartialRestaurantLocal(it.id, true)
            }
        )
    }

    suspend fun toggleFavoriteRestaurant(
        id: Int,
        oldValue: Boolean
    ){
        withContext(Dispatchers.IO) {
            restaurantsDao.update(
                PartialRestaurantLocal(
                    id = id,
                    isFavorite = !oldValue
                )
            )
        }
    }
}