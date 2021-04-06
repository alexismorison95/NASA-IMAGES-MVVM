package com.morris.nasaimages.modules.library.data.model

import com.google.gson.annotations.SerializedName

data class Asset(
    @SerializedName("collection")
    val collection: AssetColl
)

data class AssetColl(
    @SerializedName("items")
    val items: List<AssetItem>
)

data class AssetItem(
    @SerializedName("href")
    val href: String
)
