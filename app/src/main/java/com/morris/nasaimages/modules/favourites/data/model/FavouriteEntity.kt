package com.morris.nasaimages.modules.favourites.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.morris.nasaimages.modules.apod.data.model.Apod
import com.morris.nasaimages.modules.library.data.model.Item
import com.morris.nasaimages.modules.library.data.model.Library
import com.morris.nasaimages.utils.Utils
import kotlinx.parcelize.Parcelize
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

    @ColumnInfo(name = "copyright")
    val copyright: String,

    @ColumnInfo(name = "explanation")
    val explanation: String,
)

@Parcelize
data class Favourite(
    val title: String = "",
    val url: String = "",
    var hdurl: String? = null,
    val date: String,
    val copyright: String,
    val explanation: String,
) : Parcelable


fun Apod.asFavourite(): Favourite =
    Favourite(
        title = this.title,
        url = this.url,
        hdurl = this.hdurl,
        date = Utils.getCurrentDate(),
        copyright = this.copyright,
        explanation = this.explanation
    )

fun Item.asFavourite(): Favourite =
    Favourite(
        title = this.data[0].title,
        url = this.links[0].href,
        hdurl = this.data[0].hdUrl,
        date = Utils.getCurrentDate(),
        copyright = this.data[0].center,
        explanation = this.data[0].description
    )

fun FavouriteEntity.asFavourite(): Favourite =
    Favourite(this.title, this.url, this.hdurl, this.date, this.copyright, this.explanation)

fun Favourite.asFavouriteEntity(): FavouriteEntity =
    FavouriteEntity(this.title, this.url, this.hdurl, this.date, this.copyright, this.explanation)

fun List<FavouriteEntity>.asFavouriteList(): List<Favourite> = this.map {
    Favourite(it.title, it.url, it.hdurl, it.date, it.copyright, it.explanation)
}