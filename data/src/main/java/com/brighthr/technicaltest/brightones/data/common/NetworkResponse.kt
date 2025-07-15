package com.brighthr.technicaltest.brightones.data.common

sealed interface NetworkResponse<T>{
    data class Success<T>(val response : T) : NetworkResponse<T>
    data class NoInternet<T>(val message : String? = null) :
        NetworkResponse<T>
    data class ClientError<T>(val code : Int, val message : String? = null) :
        NetworkResponse<T>
    data class ServerError<T>(val code : Int, val message : String? = null) :
        NetworkResponse<T>
}