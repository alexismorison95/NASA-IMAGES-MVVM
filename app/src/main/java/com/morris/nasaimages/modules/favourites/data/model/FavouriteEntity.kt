package com.morris.nasaimages.modules.favourites.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.morris.nasaimages.modules.apod.data.model.Apod
import com.morris.nasaimages.utils.Utils
import java.util.*


@Entity(tableName = "favourites")
data class FavouriteEntity(

    @PrimaryKey
    val title: String = "",

    @ColumnInfo(name = "url")
    val url: String = "",

    @ColumnInfo(name = "hdurl")
    val hdurl: String? = null,

    @ColumnInfo(name = "date")
    val date: String,
)


data class Favourite(
    val title: String = "",
    val url: String = "",
    val hdurl: String? = null,
    val date: String,
)


fun Apod.asFavourite(): Favourite =
    Favourite(title = this.title, url = this.url, hdurl = this.hdurl, date = Utils.getCurrentDate())

fun FavouriteEntity.asFavourite(): Favourite =
    Favourite(title = this.title, url = this.url, hdurl = this.hdurl, date = this.date)

fun Favourite.asFavouriteEntity(): FavouriteEntity =
    FavouriteEntity(title = this.title, url = this.url, hdurl = this.hdurl, date = this.date)

fun List<FavouriteEntity>.asFavouriteList(): List<Favourite> = this.map {
    Favourite(it.title, it.url, it.hdurl, it.date)
}