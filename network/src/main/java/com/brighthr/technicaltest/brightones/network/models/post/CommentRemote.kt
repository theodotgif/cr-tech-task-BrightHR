package com.brighthr.technicaltest.brightones.network.models.post

import com.brighthr.technicaltest.brightones.domain.models.post.Comment
import com.google.gson.annotations.SerializedName

data class CommentRemote(
    @SerializedName("postId")
    val postId: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("body")
    val body: String,
) {
    fun transform() = Comment(
        postId = postId,
        id = id,
        name = name,
        email = email,
        body = body
    )
}