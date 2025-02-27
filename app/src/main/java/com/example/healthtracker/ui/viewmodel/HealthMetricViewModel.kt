package com.example.healthtracker.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthtracker.data.AppDatabase
import com.example.healthtracker.data.entity.HealthMetric
import com.example.healthtracker.data.repository.HealthMetricRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HealthMetricViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: HealthMetricRepository
    val allHealthMetrics: Flow<List<HealthMetric>>
    
    private val _metricTypes = MutableStateFlow(listOf("Steps", "Heart Rate", "Weight", "Sleep", "Water", "Calories"))
    val metricTypes: StateFlow<List<String>> = _metricTypes.asStateFlow()
    
    init {
        val healthMetricDao = AppDatabase.getDatabase(application).healthMetricDao()
        repository = HealthMetricRepository(healthMetricDao)
        allHealthMetrics = repository.allHealthMetrics
    }
    
    fun insertHealthMetric(healthMetric: HealthMetric) = viewModelScope.launch {
        repository.insertHealthMetric(healthMetric)
    }
    
    fun updateHealthMetric(healthMetric: HealthMetric) = viewModelScope.launch {
        repository.updateHealthMetric(healthMetric)
    }
    
    fun deleteHealthMetric(healthMetric: HealthMetric) = viewModelScope.launch {
        repository.deleteHealthMetric(healthMetric)
    }
    
    suspend fun getHealthMetricById(id: Int): HealthMetric? {
        return repository.getHealthMetricById(id)
    }
    
    fun getHealthMetricByIdAsFlow(id: Int): Flow<HealthMetric?> {
        return repository.getHealthMetricByIdAsFlow(id)
    }
    
    fun getHealthMetricsBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<HealthMetric>> {
        return repository.getHealthMetricsBetweenDates(startDate, endDate)
    }
    
    fun getHealthMetricsByType(type: String): Flow<List<HealthMetric>> {
        return repository.getHealthMetricsByType(type)
    }
    
    suspend fun getLatestHealthMetricByType(type: String): HealthMetric? {
        return repository.getLatestHealthMetricByType(type)
    }
    
    fun getHealthMetricByIdFlow(id: Int): Flow<HealthMetric?> {
        return repository.getHealthMetricByIdAsFlow(id)
    }
}
