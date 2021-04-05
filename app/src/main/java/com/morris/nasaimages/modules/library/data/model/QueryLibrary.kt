package com.morris.nasaimages.modules.library.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QueryLibrary(
    val queryText: String = "",
    val startYear: String = "1920",
    val endYear: String = "2021",
) : Parcelable