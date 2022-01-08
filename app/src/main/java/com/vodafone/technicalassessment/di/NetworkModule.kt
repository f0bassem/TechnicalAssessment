package com.vodafone.technicalassessment.di

import android.content.Context
import com.vodafone.technicalassessment.BuildConfig
import com.vodafone.technicalassessment.manager.local.ResourceProvider
import com.vodafone.technicalassessment.network.ApiService
import com.vodafone.technicalassessment.network.HandleApiError
import com.vodafone.technicalassessment.network.Interceptor
import com.vodafone.technicalassessment.network.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {

        val httpLogging = HttpLoggingInterceptor()
        httpLogging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .connectTimeout(5, TimeUnit.MINUTES)
            .callTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .writeTimeout(5, TimeUnit.MINUTES)

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(httpLogging)
        }
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideNetworkManager(@ApplicationContext context: Context): NetworkManager =
        NetworkManager(context = context)

    @Singleton
    @Provides
    fun provideHandleApiError(
        networkManager: NetworkManager,
        service: ApiService,
        resourceProvider: ResourceProvider,
    ): HandleApiError = HandleApiError(
        networkManager = networkManager,
        service = service,
        resourceProvider = resourceProvider,
    )

}