package com.vodafone.technicalassessment.manager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vodafone.technicalassessment.domain.dao.main.ListItemEntity

@Database(
    entities = [
        ListItemEntity::class,
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(DataConverter::class)
abstract class ItemsDatabase : RoomDatabase() {
    abstract fun listItemsDAO(): ListItemsDAO
}
