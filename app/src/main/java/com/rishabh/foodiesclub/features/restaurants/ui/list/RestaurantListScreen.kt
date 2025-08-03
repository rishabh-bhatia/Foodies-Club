package com.rishabh.foodiesclub.features.restaurants.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rishabh.foodiesclub.core.ui.theme.FoodiesClubTheme
import com.rishabh.foodiesclub.features.restaurants.domain.model.Deal
import com.rishabh.foodiesclub.features.restaurants.domain.model.Restaurant
import com.rishabh.foodiesclub.features.restaurants.ui.list.components.RestaurantCard

@Composable
fun RestaurantListScreen(
    viewModel: RestaurantListViewModel = hiltViewModel(),
    onRestaurantClick: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    RestaurantListContent(
        state = state,
        onRestaurantClick = onRestaurantClick,
        onSearchQueryChanged = viewModel::onSearchQueryChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RestaurantListContent(
    state: RestaurantListState,
    onRestaurantClick: (String) -> Unit,
    onSearchQueryChanged: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FoodiesClub") },
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchBar(
                    query = state.searchQuery,
                    onQueryChanged = onSearchQueryChanged
                )

                Box(modifier = Modifier.fillMaxSize()) {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    state.error?.let { error ->
                        Text(
                            text = error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.restaurants) { restaurant ->
                            RestaurantCard(
                                restaurant = restaurant,
                                onClick = { onRestaurantClick(restaurant.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Search for restaurants...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Preview(showBackground = true, name = "Success State")
@Composable
private fun RestaurantListScreenSuccessPreview() {
    val sampleRestaurant = Restaurant(
        id = "1",
        name = "Masala Kitchen",
        address = "123 Test Street",
        suburb = "Sydney",
        cuisines = listOf("Indian", "Test", "Data"),
        imageUrl = "",
        openTime = "12:00pm",
        closeTime = "11:00pm",
        deals = listOf(
            Deal(
                id = "d1",
                discountPercent = 40,
                isDineIn = true,
                isLightningDeal = false,
                startTime = "5:00pm",
                endTime = "7:00pm",
                quantityLeft = 3
            )
        )
    )
    FoodiesClubTheme {
        RestaurantListContent(
            state = RestaurantListState(
                isLoading = false,
                restaurants = listOf(sampleRestaurant, sampleRestaurant)
            ),
            onRestaurantClick = {},
            onSearchQueryChanged = {}
        )
    }
}