package com.example.individualproject.database_3.diseases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.individualproject.database_3.symptoms.SymptomEntity_3

@Entity(tableName = "diseases_table")
data class DiseaseEntity_3(
    @PrimaryKey
    val id: Int,
    val description: String,
    @ColumnInfo(name = "disease_name")
    val diseaseName: String,
    @ColumnInfo(name = "speciality_id")
    val specialityId: Int
)