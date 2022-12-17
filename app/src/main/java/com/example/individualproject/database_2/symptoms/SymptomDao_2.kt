package com.example.individualproject.database_2.symptoms

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.individualproject.database_3.symptoms.SymptomEntity_3

@Dao
interface SymptomDao_2 {
    @Insert(entity = SymptomEntity_3::class, onConflict = OnConflictStrategy.REPLACE)
    fun addDisease(symptomEntity: SymptomEntity_2)

    @Insert(entity = SymptomEntity_2::class, onConflict = OnConflictStrategy.REPLACE)
    fun addDiseases(symptomList: ArrayList<SymptomEntity_2>)

    @Query("select * from diseases_table")
    fun getDSymptoms(): List<SymptomEntity_2>

    @Query("select count(*) from diseases_table")
    fun countSymptoms(): Int

    @Query("select * from diseases_table where id = :id")
    fun getSymptomById(id: Int): SymptomEntity_2
}