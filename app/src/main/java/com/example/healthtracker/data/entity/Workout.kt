package com.example.healthtracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,
    val duration: Int,
    val caloriesBurned: Int,
    val date: String,
    val notes: String = ""
) {
    companion object {
        fun formatDate(dateTime: LocalDateTime): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            return dateTime.format(formatter)
        }
        
        fun parseDate(dateString: String): LocalDateTime {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            return LocalDateTime.parse(dateString, formatter)
        }
    }
}
