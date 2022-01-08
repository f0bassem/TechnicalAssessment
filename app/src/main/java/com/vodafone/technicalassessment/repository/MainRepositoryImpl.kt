package com.vodafone.technicalassessment.repository

import com.vodafone.technicalassessment.manager.local.PreferenceHelper
import com.vodafone.technicalassessment.manager.local.ResourceProvider
import com.vodafone.technicalassessment.network.ApiService
import com.vodafone.technicalassessment.network.HandleApiError
import com.vodafone.technicalassessment.network.NetworkManager
import com.vodafone.technicalassessment.network.dto.MainDTOMapper

class MainRepositoryImpl(
    service: ApiService,
    domainMapper: MainDTOMapper,
    resourceProvider: ResourceProvider,
    networkManager: NetworkManager,
    handleApiError: HandleApiError,
    preferenceHelper: PreferenceHelper
) : MainRepository{
}