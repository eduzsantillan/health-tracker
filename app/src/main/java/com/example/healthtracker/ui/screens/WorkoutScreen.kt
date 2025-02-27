package com.example.healthtracker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.healthtracker.data.entity.HealthMetric
import com.example.healthtracker.data.entity.Workout
import com.example.healthtracker.ui.components.Footer
import com.example.healthtracker.ui.navigation.Screen
import com.example.healthtracker.ui.viewmodel.HealthMetricViewModel
import com.example.healthtracker.ui.viewmodel.WorkoutViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    workoutViewModel: WorkoutViewModel = viewModel(),
    healthMetricViewModel: HealthMetricViewModel = viewModel()
) {
    val workouts by workoutViewModel.allWorkouts.collectAsState(initial = emptyList())
    val healthMetrics by healthMetricViewModel.allHealthMetrics.collectAsState(initial = emptyList())
    
    Scaffold(
        modifier = Modifier.padding(paddingValues),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddWorkout.route) }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Workout")
            }
        },
        bottomBar = {
            Footer()
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Health Metrics",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                HealthMetricsSummary(healthMetrics, navController)
            }

            item {
                Text(
                    text = "Recent Workouts",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            
            if (workouts.isEmpty()) {
                item {
                    Text(
                        text = "No workouts yet. Add your first workout!",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            } else {
                items(workouts.take(5)) { workout ->
                    WorkoutItem(workout, navController)
                }
                
                if (workouts.size > 5) {
                    item {
                        Text(
                            text = "View all workouts",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clickable { navController.navigate(Screen.History.route) }
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }
            
            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutItem(workout: Workout, navController: NavController) {
    ElevatedCard(
        onClick = { navController.navigate(Screen.WorkoutDetail.createRoute(workout.id)) },
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (workout.type) {
                    "Running" -> Icons.AutoMirrored.Filled.DirectionsRun
                    "Cycling" -> Icons.AutoMirrored.Filled.DirectionsBike
                    "Swimming" -> Icons.Filled.Pool
                    "Yoga" -> Icons.Filled.SelfImprovement
                    "Weightlifting" -> Icons.Filled.FitnessCenter
                    "HIIT" -> Icons.Filled.FlashOn
                    "Walking" -> Icons.AutoMirrored.Filled.DirectionsWalk
                    "Hiking" -> Icons.Filled.Terrain
                    else -> Icons.Filled.FitnessCenter
                },
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = workout.type,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "Duration: ${workout.duration} min • ${workout.caloriesBurned} calories",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Text(
                    text = formatDate(workout.date),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun HealthMetricsSummary(healthMetrics: List<HealthMetric>, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.AddHealthMetric.route) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.MonitorHeart,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Health Metrics",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            val steps = healthMetrics.find { it.type == "Steps" }
            val heartRate = healthMetrics.find { it.type == "Heart Rate" }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MetricItem(
                    title = "Steps",
                    value = steps?.value?.toInt()?.toString() ?: "0",
                    modifier = Modifier.weight(1f)
                )
                
                HorizontalDivider(
                    modifier = Modifier
                        .height(40.dp)
                        .width(1.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f)
                )
                
                MetricItem(
                    title = "Heart Rate",
                    value = heartRate?.value?.toInt()?.toString() ?: "0",
                    unit = "bpm",
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tap to add health metrics",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun MetricItem(
    title: String,
    value: String,
    unit: String = "",
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        )
        
        Text(
            text = if (unit.isNotEmpty()) "$value $unit" else value,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )
    }
}

fun formatDate(dateString: String): String {
    val dateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    return dateTime.format(DateTimeFormatter.ofPattern("MMM d, yyyy • h:mm a"))
}
