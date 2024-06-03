package com.huseyin.rickandmortyapi.services

import android.database.Observable
import com.huseyin.rickandmortyapi.model.RickAndMorty
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RickAndMortyAPIService {

    private val BASE_URL = "https://rickandmortyapi.com/api/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(RickAndMortyAPI::class.java)

    fun getData(): Single<List<RickAndMorty>>{
        return api.getRickAndMorties()
    }
}