package com.rishabh.foodiesclub.features.restaurants.domain.usecase

import com.rishabh.foodiesclub.core.data.Resource
import com.rishabh.foodiesclub.features.restaurants.domain.model.Restaurant
import com.rishabh.foodiesclub.features.restaurants.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRestaurantDetailsUseCase @Inject constructor(
    private val repository: RestaurantRepository
) {
    operator fun invoke(restaurantId: String): Flow<Resource<Restaurant?>> {
        return repository.getRestaurants().map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val restaurant = resource.data?.find { it.id == restaurantId }
                    Resource.Success(restaurant)
                }
                is Resource.Error -> {
                    Resource.Error(resource.message ?: "An unknown error occurred")
                }
                is Resource.Loading -> {
                    Resource.Loading()
                }
            }
        }
    }
}
