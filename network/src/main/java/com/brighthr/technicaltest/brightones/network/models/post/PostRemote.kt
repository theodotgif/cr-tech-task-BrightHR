package com.brighthr.technicaltest.brightones.network.models.post

import com.brighthr.technicaltest.brightones.domain.models.post.Post
import com.google.gson.annotations.SerializedName

data class PostRemote(
    @SerializedName("userId")
    val userId: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("body")
    val body: String,
) {
    fun transform() = Post(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}