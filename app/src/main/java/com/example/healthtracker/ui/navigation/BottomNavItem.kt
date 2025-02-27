package com.example.healthtracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Workout : BottomNavItem(
        route = Screen.Workout.route,
        title = "Workout",
        icon = Icons.AutoMirrored.Filled.DirectionsRun
    )
    
    object History : BottomNavItem(
        route = Screen.History.route,
        title = "History",
        icon = Icons.Filled.History
    )
}
