package com.vodafone.technicalassessment.network

import com.vodafone.technicalassessment.R
import com.vodafone.technicalassessment.manager.local.ResourceProvider
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HandleApiError @Inject constructor(
    private val networkManager: NetworkManager,
    private val service: ApiService,
    private val resourceProvider: ResourceProvider,
) {

    fun handleApiError(
        error: HttpException,
    ): Pair<Int, String> {
        when (error.code()) {
            else -> return Pair(error.code(), resourceProvider.getString(R.string.generic_error))
        }
    }
}