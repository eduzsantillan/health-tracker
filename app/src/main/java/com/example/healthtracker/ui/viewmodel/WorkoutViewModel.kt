package com.example.healthtracker.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.data.AppDatabase
import com.example.healthtracker.data.entity.Workout
import com.example.healthtracker.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: WorkoutRepository
    val allWorkouts: Flow<List<Workout>>
    
    private val _workoutTypes = MutableStateFlow(listOf("Running", "Walking", "Cycling", "Swimming", "Weightlifting", "Yoga", "HIIT", "Other"))
    val workoutTypes: StateFlow<List<String>> = _workoutTypes.asStateFlow()
    
    init {
        val workoutDao = AppDatabase.getDatabase(application).workoutDao()
        repository = WorkoutRepository(workoutDao)
        allWorkouts = repository.allWorkouts
    }
    
    fun insertWorkout(workout: Workout) = viewModelScope.launch {
        repository.insertWorkout(workout)
    }
    
    fun updateWorkout(workout: Workout) = viewModelScope.launch {
        repository.updateWorkout(workout)
    }
    
    fun deleteWorkout(workout: Workout) = viewModelScope.launch {
        repository.deleteWorkout(workout)
    }
    
    suspend fun getWorkoutById(id: Int): Workout? {
        return repository.getWorkoutById(id)
    }
    
    fun getWorkoutByIdAsFlow(id: Int): Flow<Workout?> {
        return repository.getWorkoutByIdAsFlow(id)
    }
    
    fun getWorkoutsBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<Workout>> {
        return repository.getWorkoutsBetweenDates(startDate, endDate)
    }
    
    fun getWorkoutsByType(type: String): Flow<List<Workout>> {
        return repository.getWorkoutsByType(type)
    }
}
