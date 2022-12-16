package com.example.individualproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DoctorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDoctors(doctorList: ArrayList<DoctorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDoctor(doctor: DoctorEntity)

    @Query("select count(*) from doctors_table")
    fun countDoctors(): Int

    @Query("select * from doctors_table where id = :id")
    fun getDoctorById(id: String): DoctorEntity

    @Query("select * from doctors_table")
    fun getDoctors(): List<DoctorEntity>

}