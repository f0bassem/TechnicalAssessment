package com.vodafone.technicalassessment.di

import com.vodafone.technicalassessment.manager.local.PreferenceHelper
import com.vodafone.technicalassessment.manager.local.ResourceProvider
import com.vodafone.technicalassessment.network.ApiService
import com.vodafone.technicalassessment.network.HandleApiError
import com.vodafone.technicalassessment.network.NetworkManager
import com.vodafone.technicalassessment.network.dto.MainDTOMapper
import com.vodafone.technicalassessment.repository.MainRepository
import com.vodafone.technicalassessment.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthenticationRepository(
        service: ApiService,
        domainMapper: MainDTOMapper,
        resourceProvider: ResourceProvider,
        networkManager: NetworkManager,
        handleApiError: HandleApiError,
    ): MainRepository = MainRepositoryImpl(
        service = service,
        domainMapper = domainMapper,
        resourceProvider = resourceProvider,
        networkManager = networkManager,
        handleApiError = handleApiError,
    )
}