package com.huseyin.rickandmortyapi.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.huseyin.rickandmortyapi.model.RickAndMorty


@Dao
interface RickAndMortyDao {


    @Insert
    suspend fun insertAll(vararg rickandmorties : RickAndMorty): List<Long>

    @Query("SELECT * FROM rick_and_morty")
    suspend fun GetAllRickAndMorties(): List<RickAndMorty>

    @Query("SELECT * FROM rick_and_morty WHERE id = :rickAndMortyId")
    suspend fun getRickAndMorty(rickAndMortyId : Int) : RickAndMorty

    @Query("DELETE  FROM rick_and_morty")
    suspend fun deleteRickAndMorties()

    @Query("SELECT * FROM rick_and_morty WHERE id = :id")
    suspend fun getRickAndMortyById(id: Int): RickAndMorty

}