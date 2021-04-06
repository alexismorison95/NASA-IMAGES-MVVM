package com.morris.nasaimages.modules.library.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    @SerializedName("nasa_id")
    val id: String = "",
    @SerializedName("keywords")
    val keywords: List<String> = listOf(),
    @SerializedName("date_created")
    val dateCreated: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("center")
    val center: String = "",
    var hdUrl: String? = null
) : Parcelable

@Parcelize
data class Links(
    @SerializedName("href")
    val href: String = ""
) : Parcelable

@Parcelize
data class Item(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("links")
    val links: List<Links>
) : Parcelable

data class Collection(
    @SerializedName("items")
    val items: List<Item>
)

data class Library(
    @SerializedName("collection")
    val collection: Collection
)