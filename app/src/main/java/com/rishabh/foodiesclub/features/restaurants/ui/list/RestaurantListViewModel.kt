package com.rishabh.foodiesclub.features.restaurants.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rishabh.foodiesclub.core.data.Resource
import com.rishabh.foodiesclub.features.restaurants.domain.model.Restaurant
import com.rishabh.foodiesclub.features.restaurants.domain.usecase.GetRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.OptIn

@OptIn(FlowPreview::class)
@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val getRestaurantsUseCase: GetRestaurantsUseCase,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(RestaurantListState())
    val uiState: StateFlow<RestaurantListState> = _uiState.asStateFlow()

    private var allRestaurants: List<Restaurant> = emptyList()

    init {
        fetchRestaurants()
        observeSearchQuery()
    }

    /**
     * Updates the search query in the UI state.
     * This function is called directly from the UI when the user types in the search bar.
     * @param query The new search string entered by the user.
     */
    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    /**
     * Observes the search query from the UI state, applies debouncing, and triggers the
     * filtering and sorting logic.
     */
    private fun observeSearchQuery() {
        viewModelScope.launch {
            uiState
                .map { it.searchQuery }
                .distinctUntilChanged()
                .debounce(300L)
                .collect { query ->
                    val filteredAndSorted = withContext(defaultDispatcher) {
                        applyFilterAndSort(allRestaurants, query)
                    }
                    _uiState.update { it.copy(restaurants = filteredAndSorted) }
                }
        }
    }

    /**
     * Fetches the initial list of restaurants from the repository and updates the UI state.
     */
    private fun fetchRestaurants() {
        viewModelScope.launch {
            getRestaurantsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        allRestaurants = result.data ?: emptyList()
                        val initialList = withContext(defaultDispatcher) {
                            applyFilterAndSort(allRestaurants, _uiState.value.searchQuery)
                        }
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                restaurants = initialList,
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

    /**
     * Filters a list of restaurants based on a search query and sorts the result by the best deal.
     *
     * @param restaurants The original, unfiltered list of restaurants to process.
     * @param query The search string to filter by (case-insensitive). Checks against name, suburb, and cuisines.
     * @return A new list of restaurants, filtered by the query and sorted descending by the highest discount percentage.
     */
    private fun applyFilterAndSort(
        restaurants: List<Restaurant>,
        query: String
    ): List<Restaurant> {
        val filteredList = if (query.isBlank()) {
            restaurants
        } else {
            restaurants.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.suburb.contains(query, ignoreCase = true) ||
                        it.cuisines.any { cuisine -> cuisine.contains(query, ignoreCase = true) }
            }
        }

        return filteredList.sortedByDescending { restaurant ->
            restaurant.deals.maxOfOrNull { it.discountPercent } ?: 0
        }
    }
}