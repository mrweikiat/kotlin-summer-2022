package com.example.workoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.chrono.ChronoLocalDateTime

@Entity(tableName = "history-table")
data class HistoryEntity(
    @PrimaryKey
    val date:String
)
