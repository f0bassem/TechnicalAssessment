package com.vodafone.technicalassessment.manager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vodafone.technicalassessment.domain.dao.main.ListItemEntity

@Dao
interface ListItemsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ListItemEntity>)

    @Query("SELECT * FROM ListItem")
    suspend fun selectAll(): List<ListItemEntity>

    @Query("DELETE FROM ListItem")
    suspend fun deleteAll()

    @Query("DELETE FROM ListItem WHERE id= :id")
    fun deleteById(id: Long)
}