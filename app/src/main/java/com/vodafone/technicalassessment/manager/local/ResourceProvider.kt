package com.vodafone.technicalassessment.manager.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProvider @Inject constructor(@ApplicationContext private val context: Context) {

    fun getString(resId: Int): String {
        return context.getString(resId)
    }

    fun getString(resId: Int, value: String?): String {
        return context.getString(resId, value)
    }
}