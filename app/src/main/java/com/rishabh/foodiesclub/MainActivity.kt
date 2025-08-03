package com.rishabh.foodiesclub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rishabh.foodiesclub.navigation.AppNavHost
import com.rishabh.foodiesclub.core.ui.theme.FoodiesClubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodiesClubTheme {
                AppNavHost()
            }
        }
    }
}