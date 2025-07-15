package com.brighthr.technicaltest.brightones.network.post.api

import com.brighthr.technicaltest.brightones.network.models.post.CommentRemote
import com.brighthr.technicaltest.brightones.network.models.post.PostRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API interface for fetching posts data.
 */
interface PostService {

    @GET("posts")
    suspend fun allPosts(): Response<List<PostRemote>>

    @GET("posts/{id}/comments")
    suspend fun retrieveCommentsForPost(
        @Path("id") postId: Int
    ): Response<List<CommentRemote>>
}