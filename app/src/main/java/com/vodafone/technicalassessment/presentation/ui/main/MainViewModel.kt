package com.vodafone.technicalassessment.presentation.ui.main

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.vodafone.technicalassessment.domain.dto.main.ListItemDTO
import com.vodafone.technicalassessment.repository.MainRepository
import com.vodafone.technicalassessment.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    val apiStatus = mutableStateOf<Status?>(value = null)

    val itemsList = mutableStateListOf<ListItemDTO>()

    var page: Int = 1

    var result = mutableStateOf<Drawable?>(null)
    var bitmap = mutableStateOf<Bitmap?>(null)
    var palette = mutableStateOf<Palette?>(null)

    init {
        onTriggerEvent(MainEvent.GetList)
    }

    fun onTriggerEvent(event: MainEvent) {
        viewModelScope.launch {
            when (event) {
                MainEvent.GetList -> getList()
            }
        }
    }

    private suspend fun getList() {
        repository.getList(page = page).onEach {
            apiStatus.value = it.apiStatus
            when (it.apiStatus) {
                Status.SUCCESS -> it.data?.let { list -> itemsList.addAll(list) }
                Status.ERROR,
                Status.FAILED -> {
                    it.data?.let { list ->
                        if (itemsList.size <= 0)
                            itemsList.addAll(list)
                    }
                }
            }


        }.launchIn(viewModelScope)
    }
}