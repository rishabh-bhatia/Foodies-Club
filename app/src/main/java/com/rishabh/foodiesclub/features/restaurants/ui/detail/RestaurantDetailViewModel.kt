package com.rishabh.foodiesclub.features.restaurants.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rishabh.foodiesclub.core.data.Resource
import com.rishabh.foodiesclub.features.restaurants.domain.model.Restaurant
import com.rishabh.foodiesclub.features.restaurants.domain.usecase.GetRestaurantDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailViewModel @Inject constructor(
    private val getRestaurantDetailsUseCase: GetRestaurantDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(RestaurantDetailState())
    val uiState: StateFlow<RestaurantDetailState> = _uiState.asStateFlow()

    init {
        savedStateHandle.get<String>("restaurantId")?.let { restaurantId ->
            fetchRestaurantDetails(restaurantId)
        }
    }

    /**
     * Fetches the details for a specific restaurant from the repository, sorts the deals,
     * and updates the UI state.
     * @param restaurantId The unique ID of the restaurant to fetch.
     */
    private fun fetchRestaurantDetails(restaurantId: String) {
        viewModelScope.launch {
            getRestaurantDetailsUseCase(restaurantId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        val sortedRestaurant = result.data?.let { restaurant ->
                            restaurant.copy(deals = restaurant.deals.sortedByDescending { it.discountPercent })
                        }
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                restaurant = sortedRestaurant,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }
}

data class RestaurantDetailState(
    val isLoading: Boolean = false,
    val restaurant: Restaurant? = null,
    val error: String? = null
)