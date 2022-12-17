package com.example.individualproject.database.doctors

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.individualproject.database_3.doctors.DoctorEntity_3

@Database(entities = [DoctorEntity_3::class], version = 1)
abstract class DoctorsDatabase : RoomDatabase() {

    abstract fun doctorDao(): DoctorDao

    companion object {
        private var INSTANCE: DoctorsDatabase? = null

        @Synchronized
        fun getInstance(context: Context): DoctorsDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    DoctorsDatabase::class.java,
                    "doctors_database"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}