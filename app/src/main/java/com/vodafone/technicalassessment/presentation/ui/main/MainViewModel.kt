package com.vodafone.technicalassessment.presentation.ui.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodafone.technicalassessment.domain.List
import com.vodafone.technicalassessment.manager.local.ResourceProvider
import com.vodafone.technicalassessment.repository.MainRepository
import com.vodafone.technicalassessment.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: MainRepository,
    val resourceProvider: ResourceProvider
) : ViewModel() {

    val apiStatus = mutableStateOf<Status?>(value = null)

    val itemsList = mutableStateListOf<List>()

    var page: Int = 1

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
            if (it.apiStatus == Status.SUCCESS)
                it.data?.let { list -> itemsList.addAll(list) }
        }.launchIn(viewModelScope)
    }
}