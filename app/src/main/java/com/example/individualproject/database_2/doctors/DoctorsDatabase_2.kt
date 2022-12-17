package com.example.individualproject.database_2.doctors

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.individualproject.database_3.doctors.DoctorEntity_3

@Database(entities = [DoctorEntity_3::class], version = 1)
abstract class DoctorsDatabase_2 : RoomDatabase() {

    abstract fun doctorDao(): DoctorDao_2

    companion object {
        private var INSTANCE: DoctorsDatabase_2? = null

        @Synchronized
        fun getInstance(context: Context): DoctorsDatabase_2 {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    DoctorsDatabase_2::class.java,
                    "my_db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}