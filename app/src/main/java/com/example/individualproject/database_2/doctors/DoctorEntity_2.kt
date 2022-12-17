package com.example.individualproject.database_2.doctors

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctors_table")
data class DoctorEntity_2(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "speciality")
    val speciality: String,
    @ColumnInfo(name = "speciality_id")
    val specialityId: Int,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "room_number")
    val roomNumber: Int
)