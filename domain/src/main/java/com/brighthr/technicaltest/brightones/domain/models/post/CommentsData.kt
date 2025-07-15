package com.brighthr.technicaltest.brightones.domain.models.post

data class CommentData(
    val comments: List<Comment>,
)

data class Comment (
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String,
)
