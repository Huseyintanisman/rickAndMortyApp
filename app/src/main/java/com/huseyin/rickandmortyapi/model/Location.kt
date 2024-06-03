package com.huseyin.rickandmortyapi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "location")
 class Location  (
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String? = "",
    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String? = ""
) : Serializable

