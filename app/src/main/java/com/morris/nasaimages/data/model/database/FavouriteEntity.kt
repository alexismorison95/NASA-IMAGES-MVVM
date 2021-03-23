package com.morris.nasaimages.data.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.morris.nasaimages.data.model.apod.Apod


@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "url")
    val url: String = "",

    @ColumnInfo(name = "hdurl")
    val hdurl: String? = null,
)


data class Favourite(
    val id: Int? = null,
    val title: String = "",
    val url: String = "",
    val hdurl: String? = null,
)


fun Apod.asFavourite(): Favourite =
    Favourite(title = this.title, url = this.url, hdurl = this.hdurl)

fun FavouriteEntity.asFavourite(): Favourite =
    Favourite(id = this.id, title = this.title, url = this.url, hdurl = this.hdurl)

fun Favourite.asFavouriteEntity(): FavouriteEntity =
    FavouriteEntity(title = this.title, url = this.url, hdurl = this.hdurl)