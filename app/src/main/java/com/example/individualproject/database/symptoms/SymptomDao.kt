package com.example.individualproject.database.symptoms

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.individualproject.database_3.symptoms.SymptomEntity_3

@Dao
interface SymptomDao {
    @Insert(entity = SymptomEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun addDisease(symptomEntity: SymptomEntity)

    @Insert(entity = SymptomEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun addDiseases(symptomList: ArrayList<SymptomEntity>)

    @Query("select * from diseases_table")
    fun getDSymptoms(): List<SymptomEntity>

    @Query("select count(*) from diseases_table")
    fun countSymptoms(): Int

    @Query("select * from diseases_table where id = :id")
    fun getSymptomById(id: Int): SymptomEntity
}