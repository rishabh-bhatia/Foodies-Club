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

class RestaurantRepositoryImpl @Inject constructor(
    private val apiService: RestaurantApiService
) : RestaurantRepository {
    override fun getRestaurants(): Flow<Resource<List<Restaurant>>> = flow {
        emit(Resource.Loading())

        try {
            val response = apiService.getRestaurants()
            emit(Resource.Success(response.restaurants.map { it.toDomain() }))
        } catch (e: HttpException) {
            emit(Resource.Error("Something went wrong on the server. Please try again later."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Please check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred."))
        }
    }
}