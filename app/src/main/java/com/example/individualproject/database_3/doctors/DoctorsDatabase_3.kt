package com.example.individualproject.database_3.doctors

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DoctorEntity_3::class], version = 1)
abstract class DoctorsDatabase_3 : RoomDatabase() {

    abstract fun doctorDao(): DoctorDao_3

    companion object {
        private var INSTANCE: DoctorsDatabase_3? = null

        @Synchronized
        fun getInstance(context: Context): DoctorsDatabase_3 {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    DoctorsDatabase_3::class.java,
                    "my_db_3"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}