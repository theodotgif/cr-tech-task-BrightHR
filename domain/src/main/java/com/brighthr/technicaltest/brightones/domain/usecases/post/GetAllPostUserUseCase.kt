package com.brighthr.technicaltest.brightones.domain.usecases.post

import com.brighthr.technicaltest.brightones.domain.common.Resource
import com.brighthr.technicaltest.brightones.domain.models.post.AllPostData

/**
 * Use case interface to fetch all posts.
 *
 * Executes the operation to retrieve all post data,
 * returning a [Resource] that represents success, loading, or error state.
 */
interface GetAllPostUseCase {
    suspend operator fun invoke(): Resource<AllPostData>
}