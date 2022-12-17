package com.example.individualproject.database_2.symptoms

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.individualproject.database_3.symptoms.SymptomEntity_3

@Database(entities = [SymptomEntity_3::class], version = 1)
abstract class SymptomsDatabase_2 : RoomDatabase() {

    abstract fun symptomDao(): SymptomDao_2

    companion object {
        private var INSTANCE: SymptomsDatabase_2? = null

        @Synchronized
        fun getInstance(context: Context): SymptomsDatabase_2 {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    SymptomsDatabase_2::class.java,
                    "my_db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}