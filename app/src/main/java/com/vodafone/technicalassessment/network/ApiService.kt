package com.vodafone.technicalassessment.network

import com.vodafone.technicalassessment.network.response.ListResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("list")
    suspend fun getList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ) : ArrayList<ListResponseModel>
}