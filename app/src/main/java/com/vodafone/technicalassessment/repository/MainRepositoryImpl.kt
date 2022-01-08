package com.vodafone.technicalassessment.repository

import com.vodafone.technicalassessment.R
import com.vodafone.technicalassessment.domain.List
import com.vodafone.technicalassessment.manager.local.ResourceProvider
import com.vodafone.technicalassessment.network.ApiService
import com.vodafone.technicalassessment.network.HandleApiError
import com.vodafone.technicalassessment.network.NetworkManager
import com.vodafone.technicalassessment.network.Resource
import com.vodafone.technicalassessment.network.dto.MainDTOMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class MainRepositoryImpl(
    private val service: ApiService,
    private val domainMapper: MainDTOMapper,
    private val resourceProvider: ResourceProvider,
    private val networkManager: NetworkManager,
    private val handleApiError: HandleApiError,
) : MainRepository {

    override suspend fun getList(page: Int): Flow<Resource<ArrayList<List>>> = flow {
            if (networkManager.isNetworkConnected()) {
                try {
                    emit(Resource.Loading<ArrayList<List>>())
                    val response = service.getList(page = page, limit = 10)
                    val mapListToDomainModel = domainMapper.mapListToDomainModel(response)
                    emit(Resource.Success<ArrayList<List>>(mapListToDomainModel))
                } catch (exception: HttpException) {
                    exception.printStackTrace()
                    val handleApiError = handleApiError.handleApiError(exception)
                    emit(Resource.Failed<ArrayList<List>>(handleApiError.second))
                }
            } else {
                emit(Resource.Error<ArrayList<List>>(resourceProvider.getString(R.string.no_internet_connection)))
            }
        }
}