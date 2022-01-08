package com.vodafone.technicalassessment.di

import android.content.Context
import com.vodafone.technicalassessment.presentation.base.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn (SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext context: Context): BaseApplication =
        context as BaseApplication


}