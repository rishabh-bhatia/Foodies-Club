package com.rishabh.foodiesclub.features.restaurants.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.rishabh.foodiesclub.core.ui.theme.FoodiesClubTheme
import com.rishabh.foodiesclub.features.restaurants.domain.model.Deal
import com.rishabh.foodiesclub.features.restaurants.domain.model.Restaurant
import com.rishabh.foodiesclub.features.restaurants.ui.detail.components.DealCard
import com.rishabh.foodiesclub.features.restaurants.ui.detail.components.RestaurantDetailHeader

@Composable
fun RestaurantDetailScreen(
    viewModel: RestaurantDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    RestaurantDetailContent(state = state)
}

@Composable
private fun RestaurantDetailContent(state: RestaurantDetailState) {
    Surface(modifier = Modifier.fillMaxSize()) {
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

            state.restaurant?.let { restaurant ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    item {
                        Image(
                            painter = rememberAsyncImagePainter(restaurant.imageUrl),
                            contentDescription = restaurant.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp),
                            contentScale = ContentScale.Crop
                        )
                        RestaurantDetailHeader(restaurant = restaurant)
                    }
                    itemsIndexed(restaurant.deals) { index, deal ->
                        DealCard(
                            deal = deal,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        if (index < restaurant.deals.size - 1) {
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Success State")
@Composable
private fun RestaurantDetailScreenSuccessPreview() {
    val sampleRestaurant = Restaurant(
        id = "1",
        name = "Masala Kitchen",
        address = "123 Test Street",
        suburb = "Sydney",
        cuisines = listOf("Indian", "Test", "Data"),
        imageUrl = "",
        openTime = "12:00PM",
        closeTime = "11:00PM",
        deals = listOf(
            Deal(
                id = "d1",
                discountPercent = 30,
                isDineIn = true,
                isLightningDeal = false,
                startTime = "12:00 pm",
                endTime = "3:00 pm",
                quantityLeft = 5
            ),
            Deal(
                id = "d2",
                discountPercent = 20,
                isDineIn = false,
                isLightningDeal = true,
                startTime = "5:00 pm",
                endTime = "8:00 pm",
                quantityLeft = 3
            )
        )
    )
    FoodiesClubTheme {
        RestaurantDetailContent(
            state = RestaurantDetailState(
                isLoading = false,
                restaurant = sampleRestaurant
            )
        )
    }
}