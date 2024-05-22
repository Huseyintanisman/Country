package com.example.myapplication.services
import com.example.myapplication.model.Country
import io.reactivex.Single
import retrofit2.http.GET


interface CountryAPI {
    //GET,POST,
    //https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    //Base url : https://raw.githubusercontent.com/
    //EXT : atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Country>>
}