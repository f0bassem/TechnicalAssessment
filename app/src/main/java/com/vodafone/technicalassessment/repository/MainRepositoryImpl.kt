package com.vodafone.technicalassessment.repository

import com.vodafone.technicalassessment.R
import com.vodafone.technicalassessment.domain.dao.MainDAOMapper
import com.vodafone.technicalassessment.domain.dao.main.ListItemEntity
import com.vodafone.technicalassessment.domain.dto.MainDTOMapper
import com.vodafone.technicalassessment.domain.dto.main.ListItemDTO
import com.vodafone.technicalassessment.manager.database.ItemsDatabase
import com.vodafone.technicalassessment.manager.local.ResourceProvider
import com.vodafone.technicalassessment.network.ApiService
import com.vodafone.technicalassessment.network.HandleApiError
import com.vodafone.technicalassessment.network.NetworkManager
import com.vodafone.technicalassessment.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class MainRepositoryImpl(
    private val service: ApiService,
    private val domainMapper: MainDTOMapper,
    private val entityMapper: MainDAOMapper,
    private val resourceProvider: ResourceProvider,
    private val networkManager: NetworkManager,
    private val handleApiError: HandleApiError,
    private val database: ItemsDatabase,
) : MainRepository {

    override suspend fun getList(page: Int): Flow<Resource<ArrayList<ListItemDTO>>> = flow {
        if (networkManager.isNetworkConnected()) {
            try {
                emit(Resource.Loading<ArrayList<ListItemDTO>>())
                val response = service.getList(page = page, limit = 10)

                val mapListToDomainModel = domainMapper.mapListToDomainModel(response)
                val mapListToEntityModel = entityMapper.mapListToEntityModel(mapListToDomainModel)

                if (page <= 2)
                    insertList(itemsList = mapListToEntityModel)

                emit(Resource.Success<ArrayList<ListItemDTO>>(mapListToDomainModel))
            } catch (exception: HttpException) {
                exception.printStackTrace()
                val handleApiError = handleApiError.handleApiError(exception)
                emit(
                    Resource.Failed<ArrayList<ListItemDTO>>(
                        message = handleApiError.second,
                        data = fetchList()
                    )
                )
            }
        } else {
            emit(
                Resource.Error<ArrayList<ListItemDTO>>(
                    message = resourceProvider.getString(R.string.no_internet_connection),
                    data = fetchList()
                )
            )
        }
    }

    private suspend fun insertList(itemsList: ArrayList<ListItemEntity>) {
        database.listItemsDAO().insertAll(itemsList)
    }

    private suspend fun fetchList(): ArrayList<ListItemDTO> {
        val result = database.listItemsDAO().selectAll()
        return entityMapper.mapEntityModelToList(result)
    }
}