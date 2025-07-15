package com.brighthr.technicaltest.brightones.domain.models.post

data class AllPostData(
    val posts: List<Post>,
)

data class Post (
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)
