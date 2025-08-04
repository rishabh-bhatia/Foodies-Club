package com.rishabh.foodiesclub.features.restaurants.data.repository

import com.rishabh.foodiesclub.core.data.Resource
import com.rishabh.foodiesclub.features.restaurants.data.mappers.toDomain
import com.rishabh.foodiesclub.features.restaurants.data.remote.RestaurantApiService
import com.rishabh.foodiesclub.features.restaurants.domain.model.Restaurant
import com.rishabh.foodiesclub.features.restaurants.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

// A better implementation could use a StateFlow to get data once and let everyone collect it.
// Ideal solution would be to use Room as a single source
// of truth, providing offline support as well.
@Singleton
class RestaurantRepositoryImpl @Inject constructor(
    private val apiService: RestaurantApiService
) : RestaurantRepository {

    private var cachedRestaurants: List<Restaurant>? = null

    override fun getRestaurants(): Flow<Resource<List<Restaurant>>> = flow {
        emit(Resource.Loading())

        if (cachedRestaurants != null) {
            emit(Resource.Success(cachedRestaurants!!))
            return@flow
        }

        try {
            val response = apiService.getRestaurants()
            val restaurants = response.restaurants.map { it.toDomain() }
            cachedRestaurants = restaurants
            emit(Resource.Success(restaurants))
        } catch (e: HttpException) {
            emit(Resource.Error("Something went wrong on the server. Please try again later."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Please check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred."))
        }
    }
}