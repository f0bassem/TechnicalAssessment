package com.vodafone.technicalassessment.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListResponseModel(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("author") val author: String? = null,
    @SerializedName("width") val width: Int? = null,
    @SerializedName("height") val height: Int? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("download_url") val download_url: String? = null,
) : Parcelable