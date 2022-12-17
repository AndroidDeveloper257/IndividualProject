package com.example.individualproject.database.diseases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.individualproject.database_3.diseases.DiseaseEntity_3

@Dao
interface DiseaseDao {
    @Insert(entity = DiseaseEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun addDisease(diseaseEntity: DiseaseEntity)

    @Insert(entity = DiseaseEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun addDiseases(diseaseList: ArrayList<DiseaseEntity>)

    @Query("select * from diseases_table")
    fun getDiseases(): List<DiseaseEntity>

    @Query("select count(*) from diseases_table")
    fun countDiseases(): Int

    @Query("select * from diseases_table where id = :id")
    fun getDiseaseById(id: Int): DiseaseEntity
}