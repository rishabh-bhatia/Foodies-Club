package com.rishabh.foodiesclub.features.restaurants.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.rishabh.foodiesclub.core.ui.theme.FoodiesClubTheme
import com.rishabh.foodiesclub.features.restaurants.domain.model.Deal
import com.rishabh.foodiesclub.features.restaurants.domain.model.Restaurant

@Composable
fun RestaurantCard(
    restaurant: Restaurant,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(restaurant.imageUrl),
                    contentDescription = restaurant.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                restaurant.deals.firstOrNull()?.let { deal ->
                    DealTag(
                        deal = deal,
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.TopStart)
                    )
                }
            }

            RestaurantInfo(
                name = restaurant.name,
                suburb = restaurant.suburb,
                cuisines = restaurant.cuisines
            )
        }
    }
}

@Composable
private fun RestaurantInfo(
    name: String,
    suburb: String,
    cuisines: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "0.5km Away • $suburb", // Placeholder for distance
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Text(
            text = cuisines.take(3).joinToString(" • "),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RestaurantCardPreview() {
    FoodiesClubTheme {
        val sampleRestaurant = Restaurant(
            id = "1",
            name = "Masala Kitchen",
            address = "123 Test Street",
            suburb = "Sydney",
            cuisines = listOf("Indian", "Test", "Data"),
            imageUrl = "", // Image URL is handled by Coil, can be empty for preview
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
        RestaurantCard(restaurant = sampleRestaurant)
    }
}