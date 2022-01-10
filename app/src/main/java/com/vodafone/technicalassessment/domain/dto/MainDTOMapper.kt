package com.vodafone.technicalassessment.domain.dto

import com.vodafone.technicalassessment.domain.dto.main.ListItemDTO
import com.vodafone.technicalassessment.network.response.ListResponseModel
import javax.inject.Inject

class MainDTOMapper @Inject constructor() {
    fun mapListToDomainModel(response: ArrayList<ListResponseModel>) : ArrayList<ListItemDTO>{
        val list = arrayListOf<ListItemDTO>()
        response.forEach{
            list.add(getList(it))
        }
        return list
    }

    private fun getList(response: ListResponseModel): ListItemDTO {
        return ListItemDTO(
            id = response.id,
            author = response.author,
            width = response.width,
            height = response.height,
            url = response.url,
            download_url = response.download_url,
        )
    }
}