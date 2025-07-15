package com.brighthr.technicaltest.brightones.data.datasource

import com.brighthr.technicaltest.brightones.data.common.NetworkResponse
import com.brighthr.technicaltest.brightones.domain.models.post.AllPostData
import com.brighthr.technicaltest.brightones.domain.models.post.CommentData

interface PostRemoteDataSource {

    suspend fun getAllPosts(): NetworkResponse<AllPostData>

    suspend fun getCommentsForPost(postId: Int): NetworkResponse<CommentData>

}