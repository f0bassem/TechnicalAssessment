package com.vodafone.technicalassessment.presentation.ui.details

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import com.vodafone.technicalassessment.domain.dto.main.ListItemDTO
import com.vodafone.technicalassessment.utils.Constants.Companion.ITEM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val item = savedStateHandle.get<ListItemDTO>(ITEM)

    var result = mutableStateOf<Drawable?>(null)
    var bitmap = mutableStateOf<Bitmap?>(null)
    var palette = mutableStateOf<Palette?>(null)
}