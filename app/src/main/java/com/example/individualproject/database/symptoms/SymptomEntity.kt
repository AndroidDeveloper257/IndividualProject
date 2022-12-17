package com.example.individualproject.database.symptoms

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "symptoms_table")
data class SymptomEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "disease_id")
    val diseaseId: Int,
    @ColumnInfo(name = "symptom_name")
    val symptomName: String
)