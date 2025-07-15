package com.brighthr.technicaltest.brightones.domain.repository

import com.brighthr.technicaltest.brightones.domain.common.Resource
import com.brighthr.technicaltest.brightones.domain.models.post.AllPostData
import com.brighthr.technicaltest.brightones.domain.models.post.CommentData

interface PostRepository {

    /**
     * This method will allow user to get All posts from API
     */
    suspend fun getAllPosts(): Resource<AllPostData>

    /**
     * This method will allow user to get comments for post from API
     */
    suspend fun getCommentsForPost(postId: Int): Resource<CommentData>

}