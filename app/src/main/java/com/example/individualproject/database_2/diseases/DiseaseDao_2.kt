package com.example.individualproject.database_2.diseases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.individualproject.database_3.diseases.DiseaseEntity_3

@Dao
interface DiseaseDao_2 {
    @Insert(entity = DiseaseEntity_3::class, onConflict = OnConflictStrategy.REPLACE)
    fun addDisease(diseaseEntity: DiseaseEntity_2)

    @Insert(entity = DiseaseEntity_2::class, onConflict = OnConflictStrategy.REPLACE)
    fun addDiseases(diseaseList: ArrayList<DiseaseEntity_2>)

    @Query("select * from diseases_table")
    fun getDiseases(): List<DiseaseEntity_2>

    @Query("select count(*) from diseases_table")
    fun countDiseases(): Int

    @Query("select * from diseases_table where id = :id")
    fun getDiseaseById(id: Int): DiseaseEntity_2
}