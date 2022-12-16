package com.example.individualproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DoctorEntity::class], version = 1)
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
                    .build()
            }
            return INSTANCE!!
        }
    }
}