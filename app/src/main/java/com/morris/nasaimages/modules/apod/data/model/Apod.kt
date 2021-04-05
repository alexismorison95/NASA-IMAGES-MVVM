package com.morris.nasaimages.modules.apod.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Apod(
    @SerializedName("copyright")
    val copyright: String = "NASA",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("explanation")
    val explanation: String = "",
    @SerializedName("hdurl")
    val hdurl: String = "",
    @SerializedName("media_type")
    val media_type: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: String = "",
) : Parcelable


@Entity(tableName = "apod")
data class ApodEntity(
    @PrimaryKey
    val title: String = "",

    @ColumnInfo(name = "copyright")
    val copyright: String = "",

    @ColumnInfo(name = "date")
    val date: String = "",

    @ColumnInfo(name = "explanation")
    val explanation: String = "",

    @ColumnInfo(name = "hdurl")
    val hdurl: String = "",

    @ColumnInfo(name = "media_type")
    val media_type: String = "",

    @ColumnInfo(name = "url")
    val url: String = "",
)

fun ApodEntity.asApod(): Apod =
    Apod(
        title = this.title,
        url = this.url,
        hdurl = this.hdurl,
        copyright = this.copyright,
        date = this.date,
        explanation = this.explanation,
        media_type = this.media_type
    )

fun Apod.asApodEntity(): ApodEntity =
    ApodEntity(
        title = this.title,
        url = this.url,
        hdurl = this.hdurl,
        copyright = this.copyright,
        date = this.date,
        explanation = this.explanation,
        media_type = this.media_type
    )

fun List<ApodEntity>.asApodList(): List<Apod> = this.map {
    Apod(
        title = it.title,
        url = it.url,
        hdurl = it.hdurl,
        copyright = it.copyright,
        date = it.date,
        explanation = it.explanation,
        media_type = it.media_type
    )
}