package com.example.myapplication.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Country
import com.example.myapplication.services.CountryDatabase
import kotlinx.coroutines.launch

class AddCountryViewModel(application: Application) : AndroidViewModel(application) {

    private val countryDao = CountryDatabase(application).countryDao()

    fun addCountry(name: String, capital: String, region: String, currency: String, language: String, flag: String) {
        val country = Country(name, capital, region, currency, language, flag)
        viewModelScope.launch {
            countryDao.insertAll(country)
        }
    }
}
