package com.huseyin.rickandmortyapi.viewModel

import android.app.Application
import android.database.Observable
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.huseyin.rickandmortyapi.model.RickAndMorty
import com.huseyin.rickandmortyapi.services.RickAndMortyAPIService
import com.huseyin.rickandmortyapi.services.RickAndMortyDatabase
import com.huseyin.rickandmortyapi.util.CustomSharedPreferances
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel (application: Application) : BaseViewModel(application){
    private val RickAndMortyAPIService = RickAndMortyAPIService()
    private val disposable = CompositeDisposable()
    private val customSharedPreferances = CustomSharedPreferances(getApplication())
    private var refreshTime = 10000 * 60 * 1000 * 1000 * 1000L



    val rickAndMorties = MutableLiveData<List<RickAndMorty>>()
    val rickAndMortyError = MutableLiveData<Boolean>()
    val rickAndMortyLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        val updateTime = customSharedPreferances.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromAPI()
        } else {
            getDataFromAPI()
        }
    }

    fun getAllRickAndMorties() {
        getDataFromSQLite()
    }

    private fun deleteDataFromSQLite() {
        launch {
            RickAndMortyDatabase(getApplication()).rickAndMortyDao().deleteRickAndMorties()
            Toast.makeText(getApplication(), "Countries deleted", Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromSQLite() {
        launch {
            val rickAndMorties = RickAndMortyDatabase(getApplication()).rickAndMortyDao().GetAllRickAndMorties()
            showCountries(rickAndMorties)
            Toast.makeText(getApplication(), "Countries From Sql", Toast.LENGTH_LONG).show()
        }
    }

    private fun showCountries(rickAndMortyList: List<RickAndMorty>) {
        rickAndMorties.value = rickAndMortyList
        rickAndMortyError.value = false
        rickAndMortyLoading.value = false
    }
    private fun getDataFromAPI() {
        rickAndMortyLoading.value = true
        disposable.add(
            RickAndMortyAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<RickAndMorty>>() {
                    override fun onSuccess(t: List<RickAndMorty>) {
                        storeInSqLite(t)
                        Toast.makeText(getApplication(), "Countries From API", Toast.LENGTH_LONG)
                            .show()
                        rickAndMortyLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        rickAndMortyError.value = true
                        rickAndMortyLoading.value = false
                        e.printStackTrace()
                        Toast.makeText(getApplication(), "$e", Toast.LENGTH_LONG).show()
                    }

                })
        )
    }

    private fun storeInSqLite(list: List<RickAndMorty>) {
        launch {
            val dao = RickAndMortyDatabase(getApplication()).rickAndMortyDao()
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



