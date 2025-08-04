package com.rishabh.foodiesclub.features.restaurants.ui.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rishabh.foodiesclub.core.ui.theme.FoodiesClubTheme
import com.rishabh.foodiesclub.features.restaurants.domain.model.Restaurant

@Composable
fun RestaurantDetailHeader(
    restaurant: Restaurant,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = restaurant.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = restaurant.cuisines.joinToString(" • "),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

        InfoSection(
            openTime = restaurant.openTime,
            closeTime = restaurant.closeTime,
            address = restaurant.address,
            suburb = restaurant.suburb
        )

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Composable
private fun InfoSection(
    openTime: String?,
    closeTime: String?,
    address: String,
    suburb: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        InfoRow(
            icon = { Icon(Icons.Default.Info, contentDescription = "Hours", tint = Color.Gray) },
            text = "Hours: $openTime - $closeTime"
        )
        InfoRow(
            icon = {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Address",
                    tint = Color.Gray
                )
            },
            text = "$address, $suburb"
        )
    }
}

@Composable
private fun InfoRow(
    icon: @Composable () -> Unit,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        icon()
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RestaurantDetailHeaderPreview() {
    FoodiesClubTheme {
        RestaurantDetailHeader(
            restaurant = Restaurant(
                id = "1",
                name = "Masala Kitchen",
                address = "123 Test Street",
                suburb = "Sydney",
                cuisines = listOf("Indian", "Test", "Data"),
                imageUrl = "",
                openTime = "12:00PM",
                closeTime = "11:00PM",
                deals = emptyList()
            )
        )
    }
}
