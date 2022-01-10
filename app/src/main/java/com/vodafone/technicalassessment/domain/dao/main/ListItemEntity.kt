package com.vodafone.technicalassessment.domain.dao.main

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ListItem")
data class ListItemEntity(
    @PrimaryKey @field:SerializedName("id") val id: Int,
    @field:SerializedName("author") val author: String,
    @field:SerializedName("width") val width: Int,
    @field:SerializedName("height") val height: Int,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("download_url") val download_url: String,
    @field:SerializedName("rgb") var rgb: Int
)