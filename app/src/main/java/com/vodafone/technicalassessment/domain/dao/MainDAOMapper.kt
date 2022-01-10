package com.vodafone.technicalassessment.domain.dao

import com.vodafone.technicalassessment.domain.dao.main.ListItemEntity
import com.vodafone.technicalassessment.domain.dto.main.ListItemDTO
import javax.inject.Inject

class MainDAOMapper @Inject constructor() {
    fun mapListToEntityModel(response: ArrayList<ListItemDTO>): ArrayList<ListItemEntity> {
        val list = arrayListOf<ListItemEntity>()
        response.forEach {
            list.add(getList(it))
        }
        return list
    }

    private fun getList(item: ListItemDTO): ListItemEntity {
        return ListItemEntity(
            id = item.id ?: 0,
            author = item.author ?: "",
            width = item.width ?: 0,
            height = item.height ?: 0,
            url = item.url ?: "",
            download_url = item.download_url ?: "",
            rgb = item.rgb ?: 0x000000
        )
    }

    fun mapEntityModelToList(response: List<ListItemEntity>): ArrayList<ListItemDTO> {
        val list = arrayListOf<ListItemDTO>()
        response.forEach {
            list.add(getEntityModel(it))
        }
        return list
    }

    private fun getEntityModel(item: ListItemEntity): ListItemDTO {
        return ListItemDTO(
            id = item.id,
            author = item.author,
            width = item.width,
            height = item.height,
            url = item.url,
            download_url = item.download_url,
            rgb = item.rgb
        )
    }
}