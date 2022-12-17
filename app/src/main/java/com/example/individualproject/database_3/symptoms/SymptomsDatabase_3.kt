package com.example.individualproject.database_3.symptoms

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SymptomEntity_3::class], version = 1)
abstract class SymptomsDatabase_3 : RoomDatabase() {

    abstract fun symptomDao(): SymptomDao_3

    companion object {
        private var INSTANCE: SymptomsDatabase_3? = null

        @Synchronized
        fun getInstance(context: Context): SymptomsDatabase_3 {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    SymptomsDatabase_3::class.java,
                    "my_db_3"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}