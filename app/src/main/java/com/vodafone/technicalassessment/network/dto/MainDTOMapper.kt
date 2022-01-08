package com.vodafone.technicalassessment.network.dto

import com.vodafone.technicalassessment.domain.List
import com.vodafone.technicalassessment.network.response.ListResponseModel
import javax.inject.Inject

class MainDTOMapper @Inject constructor() {
    fun mapListToDomainModel(response: ArrayList<ListResponseModel>) : ArrayList<List>{
        val list = arrayListOf<List>()
        response.forEach{
            list.add(getList(it))
        }
        return list
    }

    private fun getList(response: ListResponseModel): List {
        return List(
            id = response.id,
            author = response.author,
            width = response.width,
            height = response.height,
            url = response.url,
            download_url = response.download_url,
        )
    }
}