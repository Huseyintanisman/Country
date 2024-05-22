package com.example.myapplication.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class CustomSharedPreferances {
    companion object {
        private const val PREFERANCES_TIME = ""
        private var sharedPreferances : SharedPreferences? = null
                @Volatile private var instance : CustomSharedPreferances? = null

        private val lock = Any()

        operator fun invoke(context: Context) : CustomSharedPreferances = instance ?: synchronized(lock){
            instance ?: makeCustomSharedPreferances(context).also {
                instance = it
            }
        }
        private fun makeCustomSharedPreferances(context: Context): CustomSharedPreferances{
                sharedPreferances = PreferenceManager.getDefaultSharedPreferences(context)

            return CustomSharedPreferances()
        }
    }

    fun getTime() = sharedPreferances?.getLong(PREFERANCES_TIME,0)
}