package com.brighthr.technicaltest.brightones.domain.usecases.post

import com.brighthr.technicaltest.brightones.domain.common.Resource
import com.brighthr.technicaltest.brightones.domain.models.post.CommentData

/**
 * Use case interface to fetch all comments for a post.
 *
 * Executes the operation to retrieve all comments for a post data,
 * returning a [Resource] that represents success, loading, or error state.
 */
interface GetCommentsForPostUseCase {
    suspend operator fun invoke(postId: Int): Resource<CommentData>
}