package com.vodafone.technicalassessment.di

import android.content.Context
import androidx.room.Room
import com.vodafone.technicalassessment.manager.database.ItemsDatabase
import com.vodafone.technicalassessment.manager.database.ListItemsDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  DatabaseModule {

    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ItemsDatabase =
        Room.databaseBuilder(context, ItemsDatabase::class.java, "ItemsDatabase.db")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideMohDao(database: ItemsDatabase): ListItemsDAO = database.listItemsDAO()

}