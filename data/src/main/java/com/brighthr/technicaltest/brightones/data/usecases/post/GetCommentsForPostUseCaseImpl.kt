package com.brighthr.technicaltest.brightones.data.usecases.post

import com.brighthr.technicaltest.brightones.domain.common.Resource
import com.brighthr.technicaltest.brightones.domain.models.post.CommentData
import com.brighthr.technicaltest.brightones.domain.repository.PostRepository
import com.brighthr.technicaltest.brightones.domain.usecases.post.GetCommentsForPostUseCase
import javax.inject.Inject

class GetCommentsForPostUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : GetCommentsForPostUseCase {
    override suspend fun invoke(postId: Int): Resource<CommentData> {
       return postRepository.getCommentsForPost(postId)
    }
}