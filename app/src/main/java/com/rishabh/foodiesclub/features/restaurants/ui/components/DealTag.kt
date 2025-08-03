package com.rishabh.foodiesclub.features.restaurants.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rishabh.foodiesclub.core.ui.theme.FoodiesClubRed
import com.rishabh.foodiesclub.core.ui.theme.FoodiesClubTheme
import com.rishabh.foodiesclub.features.restaurants.domain.model.Deal

@Composable
internal fun DealTag(
    deal: Deal,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(FoodiesClubRed)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Column {
            Text(
                text = "${deal.discountPercent}% off",
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            val availabilityText = if (deal.endTime != null) {
                "Arrive before ${deal.endTime}"
            } else {
                "Anytime today"
            }

            Text(
                text = availabilityText,
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DealTagPreview() {
    FoodiesClubTheme {
        DealTag(
            deal = Deal(
                id = "1",
                discountPercent = 40,
                isDineIn = true,
                isLightningDeal = false,
                startTime = "5:00pm",
                endTime = "7:00pm",
                quantityLeft = 5
            )
        )
    }
}
