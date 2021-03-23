package com.morris.nasaimages.data.model.apod

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Apod(
    @SerializedName("copyright")
    val copyright: String ="",
    @SerializedName("date")
    val date: String ="",
    @SerializedName("explanation")
    val explanation: String ="",
    @SerializedName("hdurl")
    val hdurl: String ="",
    @SerializedName("media_type")
    val media_type: String ="",
    @SerializedName("title")
    val title: String ="",
    @SerializedName("url")
    val url: String ="",
): Parcelable