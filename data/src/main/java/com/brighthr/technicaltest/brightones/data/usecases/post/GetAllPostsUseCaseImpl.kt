package com.brighthr.technicaltest.brightones.data.usecases.post

import com.brighthr.technicaltest.brightones.domain.common.Resource
import com.brighthr.technicaltest.brightones.domain.models.post.AllPostData
import com.brighthr.technicaltest.brightones.domain.repository.PostRepository
import com.brighthr.technicaltest.brightones.domain.usecases.post.GetAllPostUseCase
import javax.inject.Inject

class GetAllPostsUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : GetAllPostUseCase {
    override suspend fun invoke(): Resource<AllPostData> {
       return postRepository.getAllPosts()
    }
}