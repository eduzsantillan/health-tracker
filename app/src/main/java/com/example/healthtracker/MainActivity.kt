package com.example.healthtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.healthtracker.ui.components.BottomNavBar
import com.example.healthtracker.ui.navigation.BottomNavItem
import com.example.healthtracker.ui.navigation.Screen
import com.example.healthtracker.ui.screens.AddHealthMetricScreen
import com.example.healthtracker.ui.screens.AddWorkoutScreen
import com.example.healthtracker.ui.screens.HealthMetricDetailScreen
import com.example.healthtracker.ui.screens.HistoryScreen
import com.example.healthtracker.ui.screens.WorkoutDetailScreen
import com.example.healthtracker.ui.screens.WorkoutScreen
import com.example.healthtracker.ui.theme.HealthTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Handle the splash screen transition
        installSplashScreen()
        
        setContent {
            HealthTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HealthTrackerApp()
                }
            }
        }
    }
}

@Composable
fun HealthTrackerApp() {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Workout,
        BottomNavItem.History
    )
    
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    
    val bottomBarRoutes = listOf(
        Screen.Workout.route,
        Screen.History.route
    )
    
    val shouldShowBottomBar = currentRoute in bottomBarRoutes
    
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomNavBar(
                    navController = navController,
                    items = items
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Workout.route
        ) {
            composable(Screen.Workout.route) {
                WorkoutScreen(navController, paddingValues)
            }
            
            composable(Screen.History.route) {
                HistoryScreen(navController, paddingValues)
            }
            
            composable(Screen.AddWorkout.route) {
                AddWorkoutScreen(navController)
            }
            
            composable(Screen.AddHealthMetric.route) {
                AddHealthMetricScreen(navController)
            }
            
            composable(
                route = Screen.WorkoutDetail.route,
                arguments = listOf(navArgument("workoutId") { type = NavType.IntType })
            ) {
                val workoutId = it.arguments?.getInt("workoutId") ?: 0
                WorkoutDetailScreen(navController, workoutId)
            }
            
            composable(
                route = Screen.HealthMetricDetail.route,
                arguments = listOf(
                    navArgument("healthMetricId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val healthMetricId = backStackEntry.arguments?.getInt("healthMetricId") ?: -1
                HealthMetricDetailScreen(navController = navController, healthMetricId = healthMetricId)
            }
        }
    }
}