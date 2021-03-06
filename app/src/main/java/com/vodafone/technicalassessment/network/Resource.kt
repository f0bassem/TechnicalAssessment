package com.vodafone.technicalassessment.network

import com.vodafone.technicalassessment.utils.Status

sealed class Resource<T>(
    val data: T? = null,
    var apiStatus: Status,
    val message: String? = ""
) {
    class Loading<T> : Resource<T>(apiStatus = Status.LOADING)

    class Success<T>(data: T? = null) : Resource<T>(data = data, apiStatus = Status.SUCCESS)

    class Error<T>(message: String? = null, data: T? = null) :
        Resource<T>(apiStatus = Status.ERROR, message = message, data = data)

    class Failed<T>(message: String? = null, data: T? = null) :
        Resource<T>(apiStatus = Status.FAILED, message = message, data = data)
}