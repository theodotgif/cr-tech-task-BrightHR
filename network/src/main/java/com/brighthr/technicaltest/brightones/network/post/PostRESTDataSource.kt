package com.brighthr.technicaltest.brightones.network.post

import com.brighthr.technicaltest.brightones.data.datasource.PostRemoteDataSource
import com.brighthr.technicaltest.brightones.domain.models.post.AllPostData
import com.brighthr.technicaltest.brightones.domain.models.post.CommentData
import com.brighthr.technicaltest.brightones.network.common.retrofitApiCall
import com.brighthr.technicaltest.brightones.network.post.api.PostService
import javax.inject.Inject

/**
 * Implementation of [PostRemoteDataSource] that fetches posts from a REST API using [PostService].
 *
 * Uses [retrofitApiCall] helper to make the network request and map the remote data
 * models to domain models ([AllPostData]) by transforming each remote post.
 */
class PostRESTDataSource @Inject constructor(
    private val service: PostService
) : PostRemoteDataSource {

    override suspend fun getAllPosts() = retrofitApiCall(
        call = {
            service.allPosts()
        },
        parse = {
            AllPostData(
                posts = it.map { remote -> remote.transform() },
            )
        }
    )

    override suspend fun getCommentsForPost(postId: Int) = retrofitApiCall(
        call = {
            service.retrieveCommentsForPost(postId)
        },
        parse = {
            CommentData(
                comments = it.map { remote -> remote.transform() },
            )
        }
    )
}