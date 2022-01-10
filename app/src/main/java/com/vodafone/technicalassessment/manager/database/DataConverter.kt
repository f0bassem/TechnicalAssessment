package com.vodafone.technicalassessment.manager.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vodafone.technicalassessment.domain.dao.main.ListItemEntity

object DataConverter {
    @TypeConverter
    fun fromPromotionItems(value: ArrayList<ListItemEntity>): String {
        return Gson().toJson(
            value,
            object : TypeToken<ArrayList<ListItemEntity>>() {}.type
        )
    }
}