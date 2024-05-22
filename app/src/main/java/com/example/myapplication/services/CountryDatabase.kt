package com.example.myapplication.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.model.Country



@Database(entities = [Country::class], version = 1, exportSchema = true)
abstract class CountryDatabase() : RoomDatabase() {

    abstract fun countryDao() : CountryDao
    //Singleton
    companion object {
        @Volatile private var instance : CountryDatabase? = null
        private val lock = Any()
        operator fun invoke(context : Context) = instance ?: synchronized(lock){
            instance ?: makeDataBase(context).also {
                instance = it
            }
        }

        private fun makeDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,CountryDatabase::class.java,"countryDatabase"
        ).build()

    }

}