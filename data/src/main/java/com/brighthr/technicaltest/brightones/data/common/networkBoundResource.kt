package com.brighthr.technicaltest.brightones.data.common

import com.brighthr.technicaltest.brightones.domain.common.AppError
import com.brighthr.technicaltest.brightones.domain.common.Resource

internal suspend inline fun <Result> networkBoundResource(
    crossinline network: suspend () -> NetworkResponse<Result>,
) = when (val response = network()) {
    is NetworkResponse.ClientError -> Resource.Error(
        AppError.NetworkError(response.code,
        response.message))
    is NetworkResponse.ServerError -> Resource.Error(AppError.NetworkError(response.code,
        response.message))
    is NetworkResponse.NoInternet -> Resource.Error(AppError.NoInternet)
    is NetworkResponse.Success -> Resource.Success(response.response)
}