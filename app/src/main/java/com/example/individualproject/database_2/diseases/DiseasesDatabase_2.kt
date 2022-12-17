package com.example.individualproject.database_2.diseases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.individualproject.database_3.diseases.DiseaseEntity_3

@Database(entities = [DiseaseEntity_3::class], version = 1)
abstract class DiseasesDatabase_2 : RoomDatabase() {

    abstract fun diseaseDao(): DiseaseDao_2

    companion object {
        private var INSTANCE: DiseasesDatabase_2? = null

        @Synchronized
        fun getInstance(context: Context): DiseasesDatabase_2 {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    DiseasesDatabase_2::class.java,
                    "my_db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}