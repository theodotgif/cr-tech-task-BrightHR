package com.brighthr.technicaltest.brightones.network.models

import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("message")
    val message: String,
)