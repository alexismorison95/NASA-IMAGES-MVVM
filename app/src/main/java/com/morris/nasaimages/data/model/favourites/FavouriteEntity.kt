package com.morris.nasaimages.data.model.favourites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.morris.nasaimages.data.model.apod.Apod


@Entity(tableName = "favourites")
data class FavouriteEntity(

    @PrimaryKey
    val title: String = "",

    @ColumnInfo(name = "url")
    val url: String = "",

    @ColumnInfo(name = "hdurl")
    val hdurl: String? = null,
)


data class Favourite(
    val title: String = "",
    val url: String = "",
    val hdurl: String? = null,
)


fun Apod.asFavourite(): Favourite =
    Favourite(title = this.title, url = this.url, hdurl = this.hdurl)

fun FavouriteEntity.asFavourite(): Favourite =
    Favourite(title = this.title, url = this.url, hdurl = this.hdurl)

fun Favourite.asFavouriteEntity(): FavouriteEntity =
    FavouriteEntity(title = this.title, url = this.url, hdurl = this.hdurl)

fun List<FavouriteEntity>.asFavouriteList(): List<Favourite> = this.map {
    Favourite(it.title, it.url, it.hdurl)
}