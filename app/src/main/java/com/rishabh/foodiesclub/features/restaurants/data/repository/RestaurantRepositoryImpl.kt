package com.rishabh.foodiesclub.features.restaurants.data.repository

import com.rishabh.foodiesclub.features.restaurants.data.mappers.toDomain
import com.rishabh.foodiesclub.features.restaurants.data.remote.RestaurantApiService
import com.rishabh.foodiesclub.features.restaurants.domain.model.Restaurant
import com.rishabh.foodiesclub.features.restaurants.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val apiService: RestaurantApiService
) : RestaurantRepository {
    override fun getRestaurants(): Flow<List<Restaurant>> = flow {
        try {
            val response = apiService.getRestaurants()
            emit(response.restaurants.map { it.toDomain() })
        } catch (e: Exception) {
            println("RestaurantRepositoryImpl Caught exception $e")
            // For now, just emit an empty list on error.
            emit(emptyList())
        }
    }
}
