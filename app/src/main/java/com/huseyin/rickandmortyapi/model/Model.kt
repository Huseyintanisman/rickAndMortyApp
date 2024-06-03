package com.huseyin.rickandmortyapi.model


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "rick_and_morty")
data class RickAndMorty(
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var RickAndMortyID: Int,
  //  @ColumnInfo(name = "location")
   // @Embedded
  //  val location : Location = Location(),
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val RickAndMortyName: String?,
    @ColumnInfo(name = "status")
    @SerializedName("status")
    val RickAndMortyStatus: String?,
    @ColumnInfo(name = "species")
    @SerializedName("species")
    val RickAndMortySpecies: String?,
    @ColumnInfo(name = "gender")
    @SerializedName("gender")
    val RickAndMortyGender: String?,
    @ColumnInfo(name = "image")
    @SerializedName("image")
    val RıckAndMortyImageUrl: String?,
    @ColumnInfo(name = "url")
    @SerializedName("url")
    val RıckAndMortyUrl: String?,
    @ColumnInfo(name = "created")
    @SerializedName("created")
    val RıckAndMortyCreated: String?
    ){
        @PrimaryKey(true)
        var uuid: Int = 0
    }
