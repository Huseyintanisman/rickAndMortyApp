package com.huseyin.rickandmortyapi.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.huseyin.rickandmortyapi.model.RickAndMorty
import com.huseyin.rickandmortyapi.services.RickAndMortyDatabase
import kotlinx.coroutines.launch

class RickAndMortyViewModel(application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<RickAndMorty>()

    fun getDataFromRoom(uuid: Int){
        launch {
            val dao = RickAndMortyDatabase(getApplication()).rickAndMortyDao()
            val rickAndMorty = dao.getRickAndMorty(uuid)
            countryLiveData.value = rickAndMorty
        }

    }
}