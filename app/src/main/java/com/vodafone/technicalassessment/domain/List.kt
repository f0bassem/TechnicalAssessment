package com.vodafone.technicalassessment.domain

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class List(
    val id: String? = null,
    val author: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val url: String? = null,
    val download_url: String? = null,
    var rgb: Int? = null
) : Parcelable