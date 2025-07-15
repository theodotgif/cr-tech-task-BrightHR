package com.brighthr.technicaltest.brightones.data.repository

import com.brighthr.technicaltest.brightones.data.common.networkBoundResource
import com.brighthr.technicaltest.brightones.data.datasource.PostRemoteDataSource
import com.brighthr.technicaltest.brightones.domain.repository.PostRepository
import javax.inject.Inject

internal class PostDataRepository @Inject constructor(
    private val network: PostRemoteDataSource,
) : PostRepository {

    override suspend fun getAllPosts() = networkBoundResource(
        network = { network.getAllPosts()},
    )

    override suspend fun getCommentsForPost(postId: Int) = networkBoundResource(
        network = { network.getCommentsForPost(postId)},
    )
}