package com.rishabh.foodiesclub.features.restaurants.ui.detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rishabh.foodiesclub.core.ui.theme.FoodiesClubRed
import com.rishabh.foodiesclub.core.ui.theme.FoodiesClubTheme
import com.rishabh.foodiesclub.features.restaurants.domain.model.Deal

@Composable
fun DealCard(
    deal: Deal,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DealInfo(deal = deal)
            OutlinedButton(
                onClick = { /* No-op as per requirement */ },
                border = BorderStroke(1.dp, FoodiesClubRed)
            ) {
                Text(text = "Redeem", color = FoodiesClubRed)
            }
        }
    }
}

@Composable
private fun DealInfo(
    deal: Deal,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "${deal.discountPercent}% Off",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = FoodiesClubRed
        )
        val timeText = if (deal.startTime != null && deal.endTime != null) {
            "Between ${deal.startTime} - ${deal.endTime}"
        } else {
            "Available anytime"
        }
        Text(
            text = timeText,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "${deal.quantityLeft} Deals Left",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DealCardPreview() {
    FoodiesClubTheme {
        DealCard(
            deal = Deal(
                id = "d1",
                discountPercent = 30,
                isDineIn = true,
                isLightningDeal = false,
                startTime = "12:00 pm",
                endTime = "3:00 pm",
                quantityLeft = 5
            )
        )
    }
}
