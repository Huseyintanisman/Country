package com.example.myapplication.viewmodel
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.Country
import com.example.myapplication.services.CountryAPIService
import com.example.myapplication.services.CountryDatabase
import com.example.myapplication.util.CustomSharedPreferances
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application : Application) : BaseViewModel(application) {

    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private val customSharedPreferances = CustomSharedPreferances(getApplication())
    private var refreshTime = 10000 * 60 * 1000 * 1000 * 1000L

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        val updateTime = customSharedPreferances.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromAPI()
        }
    }

    fun europaitemSelected() {
        europeSelectedDataFromSQLite()
    }

    fun asiaitemSelected() {
        asiaSelectedDataFromSQLite()
    }

    fun africaitemSelected() {
        africaSelectedDataFromSQLite()
    }

    fun americaitemSelected() {
        americasSelectedDataFromSQLite()
    }

    fun getAllCountries() {
        getDataFromSQLite()
    }


    fun deleteSelectedCountries(uuid: Int) {
        deleteSelectedDataFromSQLite(uuid)
    }

    fun deleteCountries() {
        deleteDataFromSQLite()
    }


    private fun getDataFromSQLite() {
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries From Sql", Toast.LENGTH_LONG).show()
        }
    }


    private fun europeSelectedDataFromSQLite() {
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().europeSelectedCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Europe Countries", Toast.LENGTH_LONG).show()
        }
    }

    private fun africaSelectedDataFromSQLite() {
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().africaSelectedCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Africa Countries", Toast.LENGTH_LONG).show()
        }
    }

    private fun americasSelectedDataFromSQLite() {
        launch {
            val countries =
                CountryDatabase(getApplication()).countryDao().americasSelectedCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "America Countries", Toast.LENGTH_LONG).show()
        }
    }

    private fun asiaSelectedDataFromSQLite() {
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().asiaSelectedCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Asia Countries", Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteSelectedDataFromSQLite(uuid: Int) {
        launch {
            CountryDatabase(getApplication()).countryDao().deleteSelectedCountry(uuid)
            Toast.makeText(getApplication(), "Country deleted", Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteDataFromSQLite() {
        launch {
            CountryDatabase(getApplication()).countryDao().deleteAllCountries()
            Toast.makeText(getApplication(), "Countries deleted", Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI() {
        countryLoading.value = true
        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        storeInSqLite(t)
                        Toast.makeText(getApplication(), "Countries From API", Toast.LENGTH_LONG)
                            .show()
                        countryLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                        e.printStackTrace()
                    }

                })
        )

    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSqLite(list: List<Country>) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val listLong = dao.insertAll(*list.toTypedArray()) // -> list -> individual
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }
            showCountries(list)
        }
    }
}
