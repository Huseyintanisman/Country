package com.example.myapplication.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.model.Country

@Dao
interface CountryDao {
    //Data Access Object

    @Insert
    suspend fun insertAll(vararg countries: Country) : List<Long>

    //Insert -> INSERT INTO

    //suspend -> coroutine, pause&resume

    //vararg -> multiple country object

    //List<Long> -> primary keys

    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE region = 'Europe'")
    suspend fun europeSelectedCountries() : List<Country>

    @Query("SELECT * FROM country WHERE region = 'Asia'")
    suspend fun asiaSelectedCountries() : List<Country>

    @Query("SELECT * FROM country WHERE region = 'Africa'")
    suspend fun africaSelectedCountries() : List<Country>

    @Query("SELECT * FROM country WHERE region = 'Americas'")
    suspend fun americasSelectedCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE  FROM country")
    suspend fun deleteAllCountries()

    @Query("DELETE FROM country WHERE uuid = :countryId")
    suspend fun deleteSelectedCountry(countryId: Int)

}