package com.example.individualproject.database.symptoms

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.individualproject.database_3.symptoms.SymptomEntity_3

@Database(entities = [SymptomEntity_3::class], version = 1)
abstract class SymptomsDatabase : RoomDatabase() {

    abstract fun symptomDao(): SymptomDao

    companion object {
        private var INSTANCE: SymptomsDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SymptomsDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    SymptomsDatabase::class.java,
                    "doctors_database"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}