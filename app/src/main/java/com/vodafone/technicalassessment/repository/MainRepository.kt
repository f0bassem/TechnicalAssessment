package com.vodafone.technicalassessment.repository

import com.vodafone.technicalassessment.domain.List
import com.vodafone.technicalassessment.network.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getList(page: Int): Flow<Resource<ArrayList<List>>>
}