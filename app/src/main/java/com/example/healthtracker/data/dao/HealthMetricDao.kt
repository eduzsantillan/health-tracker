package com.example.healthtracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.healthtracker.data.entity.HealthMetric
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthMetricDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthMetric(healthMetric: HealthMetric): Long
    
    @Update
    suspend fun updateHealthMetric(healthMetric: HealthMetric)
    
    @Delete
    suspend fun deleteHealthMetric(healthMetric: HealthMetric)
    
    @Query("SELECT * FROM health_metrics ORDER BY date DESC")
    fun getAllHealthMetrics(): Flow<List<HealthMetric>>
    
    @Query("SELECT * FROM health_metrics WHERE id = :id")
    suspend fun getHealthMetricById(id: Int): HealthMetric?
    
    @Query("SELECT * FROM health_metrics WHERE id = :id")
    fun getHealthMetricByIdAsFlow(id: Int): Flow<HealthMetric?>
    
    @Query("SELECT * FROM health_metrics WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getHealthMetricsBetweenDates(startDate: String, endDate: String): Flow<List<HealthMetric>>
    
    @Query("SELECT * FROM health_metrics WHERE type = :type ORDER BY date DESC")
    fun getHealthMetricsByType(type: String): Flow<List<HealthMetric>>
    
    @Query("SELECT * FROM health_metrics WHERE type = :type ORDER BY date DESC LIMIT 1")
    suspend fun getLatestHealthMetricByType(type: String): HealthMetric?
}
