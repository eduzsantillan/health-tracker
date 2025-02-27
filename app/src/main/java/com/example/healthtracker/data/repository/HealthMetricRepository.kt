package com.example.healthtracker.data.repository

import com.example.healthtracker.data.dao.HealthMetricDao
import com.example.healthtracker.data.entity.HealthMetric
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class HealthMetricRepository(private val healthMetricDao: HealthMetricDao) {
    
    val allHealthMetrics: Flow<List<HealthMetric>> = healthMetricDao.getAllHealthMetrics()
    
    suspend fun insertHealthMetric(healthMetric: HealthMetric): Long {
        return healthMetricDao.insertHealthMetric(healthMetric)
    }
    
    suspend fun updateHealthMetric(healthMetric: HealthMetric) {
        healthMetricDao.updateHealthMetric(healthMetric)
    }
    
    suspend fun deleteHealthMetric(healthMetric: HealthMetric) {
        healthMetricDao.deleteHealthMetric(healthMetric)
    }
    
    suspend fun getHealthMetricById(id: Int): HealthMetric? {
        return healthMetricDao.getHealthMetricById(id)
    }
    
    fun getHealthMetricByIdAsFlow(id: Int): Flow<HealthMetric?> {
        return healthMetricDao.getHealthMetricByIdAsFlow(id)
    }
    
    fun getHealthMetricsBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<HealthMetric>> {
        val formattedStartDate = HealthMetric.formatDate(startDate)
        val formattedEndDate = HealthMetric.formatDate(endDate)
        return healthMetricDao.getHealthMetricsBetweenDates(formattedStartDate, formattedEndDate)
    }
    
    fun getHealthMetricsByType(type: String): Flow<List<HealthMetric>> {
        return healthMetricDao.getHealthMetricsByType(type)
    }
    
    suspend fun getLatestHealthMetricByType(type: String): HealthMetric? {
        return healthMetricDao.getLatestHealthMetricByType(type)
    }
}
