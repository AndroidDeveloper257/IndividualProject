package com.example.individualproject.database.diseases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.individualproject.database_3.diseases.DiseaseEntity_3

@Database(entities = [DiseaseEntity_3::class], version = 1)
abstract class DiseasesDatabase : RoomDatabase() {

    abstract fun diseaseDao(): DiseaseDao

    companion object {
        private var INSTANCE: DiseasesDatabase? = null

        @Synchronized
        fun getInstance(context: Context): DiseasesDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    DiseasesDatabase::class.java,
                    "doctors_database"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}