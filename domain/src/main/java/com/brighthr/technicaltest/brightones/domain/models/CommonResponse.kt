package com.brighthr.technicaltest.brightones.domain.models

abstract class CommonResponse {
    abstract val status: Boolean
    abstract val errorCode: Int
    abstract val message: String
}