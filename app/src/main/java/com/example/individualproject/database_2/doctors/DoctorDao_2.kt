package com.example.individualproject.database_2.doctors

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.individualproject.database_3.doctors.DoctorEntity_3

@Dao
interface DoctorDao_2 {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDoctors(doctorList: ArrayList<DoctorEntity_2>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDoctor(doctor: DoctorEntity_2)

    @Query("select count(*) from doctors_table")
    fun countDoctors(): Int

    @Query("select * from doctors_table where id = :id")
    fun getDoctorById(id: String): DoctorEntity_2

    @Query("select * from doctors_table")
    fun getDoctors(): List<DoctorEntity_2>

}