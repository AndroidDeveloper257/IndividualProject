package com.example.individualproject.database_3.diseases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DiseaseEntity_3::class], version = 1)
abstract class DiseasesDatabase_3 : RoomDatabase() {

    abstract fun diseaseDao(): DiseaseDao_3

    companion object {
        private var INSTANCE: DiseasesDatabase_3? = null

        @Synchronized
        fun getInstance(context: Context): DiseasesDatabase_3 {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    DiseasesDatabase_3::class.java,
                    "my_db_3"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}