package com.rishabh.foodiesclub.features.restaurants.domain.usecase

import com.rishabh.foodiesclub.core.data.Resource
import com.rishabh.foodiesclub.features.restaurants.domain.model.Restaurant
import com.rishabh.foodiesclub.features.restaurants.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantRepository
) {
    operator fun invoke(): Flow<Resource<List<Restaurant>>> {
        return repository.getRestaurants()
    }
}