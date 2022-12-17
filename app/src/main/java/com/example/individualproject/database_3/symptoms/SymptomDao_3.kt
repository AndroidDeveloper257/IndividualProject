package com.example.individualproject.database_3.symptoms

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SymptomDao_3 {
    @Insert(entity = SymptomEntity_3::class, onConflict = OnConflictStrategy.REPLACE)
    fun addSymptom(symptomEntity: SymptomEntity_3)

    @Insert(entity = SymptomEntity_3::class, onConflict = OnConflictStrategy.REPLACE)
    fun addSymptoms(symptomList: ArrayList<SymptomEntity_3>)

    @Query("select * from diseases_table")
    fun getDSymptoms(): List<SymptomEntity_3>

    @Query("select count(*) from diseases_table")
    fun countSymptoms(): Int

    @Query("select * from diseases_table where id = :id")
    fun getSymptomById(id: Int): SymptomEntity_3
}