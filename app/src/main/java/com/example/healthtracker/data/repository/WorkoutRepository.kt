package com.example.healthtracker.data.repository

import com.example.healthtracker.data.dao.WorkoutDao
import com.example.healthtracker.data.entity.Workout
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class WorkoutRepository(private val workoutDao: WorkoutDao) {
    
    val allWorkouts: Flow<List<Workout>> = workoutDao.getAllWorkouts()
    
    suspend fun insertWorkout(workout: Workout): Long {
        return workoutDao.insertWorkout(workout)
    }
    
    suspend fun updateWorkout(workout: Workout) {
        workoutDao.updateWorkout(workout)
    }
    
    suspend fun deleteWorkout(workout: Workout) {
        workoutDao.deleteWorkout(workout)
    }
    
    suspend fun getWorkoutById(id: Int): Workout? {
        return workoutDao.getWorkoutById(id)
    }
    
    fun getWorkoutByIdAsFlow(id: Int): Flow<Workout?> {
        return workoutDao.getWorkoutByIdAsFlow(id)
    }
    
    fun getWorkoutsBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<Workout>> {
        val formattedStartDate = Workout.formatDate(startDate)
        val formattedEndDate = Workout.formatDate(endDate)
        return workoutDao.getWorkoutsBetweenDates(formattedStartDate, formattedEndDate)
    }
    
    fun getWorkoutsByType(type: String): Flow<List<Workout>> {
        return workoutDao.getWorkoutsByType(type)
    }
}
