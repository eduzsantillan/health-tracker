package com.example.healthtracker.ui.navigation

sealed class Screen(val route: String) {
    object Workout : Screen("workout")
    object History : Screen("history")
    object AddWorkout : Screen("add_workout")
    object AddHealthMetric : Screen("add_health_metric")
    object WorkoutDetail : Screen("workout_detail/{workoutId}") {
        fun createRoute(workoutId: Int) = "workout_detail/$workoutId"
    }
    object HealthMetricDetail : Screen("health_metric_detail/{healthMetricId}") {
        fun createRoute(healthMetricId: Int): String {
            return "health_metric_detail/$healthMetricId"
        }
    }
}
