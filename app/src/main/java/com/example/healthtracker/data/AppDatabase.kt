package com.example.healthtracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.healthtracker.data.dao.HealthMetricDao
import com.example.healthtracker.data.dao.WorkoutDao
import com.example.healthtracker.data.entity.HealthMetric
import com.example.healthtracker.data.entity.Workout

@Database(
    entities = [Workout::class, HealthMetric::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun workoutDao(): WorkoutDao
    abstract fun healthMetricDao(): HealthMetricDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "health_tracker_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
