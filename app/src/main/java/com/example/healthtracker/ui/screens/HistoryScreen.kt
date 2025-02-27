package com.example.healthtracker.ui.screens

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
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun HistoryScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    workoutViewModel: WorkoutViewModel = viewModel(),
    healthMetricViewModel: HealthMetricViewModel = viewModel()
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Workouts", "Health Metrics")
    
    Scaffold(
        modifier = Modifier.padding(paddingValues)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index }
                    )
                }
            }
            
            when (selectedTabIndex) {
                0 -> WorkoutsHistoryTab(navController, workoutViewModel)
                1 -> HealthMetricsHistoryTab(navController, healthMetricViewModel)
            }
            
            Footer()
        }
    }
}

@Composable
fun WorkoutsHistoryTab(
    navController: NavController,
    workoutViewModel: WorkoutViewModel
) {
    val workouts by workoutViewModel.allWorkouts.collectAsState(initial = emptyList())
    val workoutTypes by workoutViewModel.workoutTypes.collectAsState()
    
    var selectedType by remember { mutableStateOf<String?>(null) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        // Filter chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filter",
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = "Filter by type:",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            FilterChip(
                selected = selectedType == null,
                onClick = { selectedType = null },
                label = { Text("All") }
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            FilterChip(
                selected = selectedType == "Running",
                onClick = { selectedType = if (selectedType == "Running") null else "Running" },
                label = { Text("Running") }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (workouts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No workouts recorded yet",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            val filteredWorkouts = if (selectedType != null) {
                workouts.filter { it.type == selectedType }
            } else {
                workouts
            }
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredWorkouts) { workout ->
                    WorkoutItem(workout, navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthMetricsHistoryTab(
    navController: NavController,
    healthMetricViewModel: HealthMetricViewModel
) {
    val healthMetrics by healthMetricViewModel.allHealthMetrics.collectAsState(initial = emptyList())
    val metricTypes by healthMetricViewModel.metricTypes.collectAsState()
    
    var selectedType by remember { mutableStateOf<String?>(null) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        
        // Filter chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filter",
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = "Filter by type:",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            FilterChip(
                selected = selectedType == null,
                onClick = { selectedType = null },
                label = { Text("All") }
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            FilterChip(
                selected = selectedType == "Steps",
                onClick = { selectedType = if (selectedType == "Steps") null else "Steps" },
                label = { Text("Steps") }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (healthMetrics.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No health metrics recorded yet",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            val filteredMetrics = if (selectedType != null) {
                healthMetrics.filter { it.type == selectedType }
            } else {
                healthMetrics
            }
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredMetrics) { metric ->
                    HealthMetricItem(metric, navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthMetricItem(healthMetric: HealthMetric, navController: NavController) {
    ElevatedCard(
        onClick = { navController.navigate(Screen.HealthMetricDetail.createRoute(healthMetric.id)) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.MonitorHeart,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = healthMetric.type,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                val unit = when (healthMetric.type) {
                    "Steps" -> "steps"
                    "Heart Rate" -> "bpm"
                    "Weight" -> "kg"
                    "Sleep" -> "hours"
                    "Water" -> "ml"
                    "Calories" -> "kcal"
                    else -> ""
                }
                
                Text(
                    text = "${healthMetric.value} $unit",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Text(
                    text = formatDate(healthMetric.date),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
