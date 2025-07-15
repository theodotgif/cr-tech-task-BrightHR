package com.brighthr.technicaltest.brightones.usecase.post

import com.brighthr.technicaltest.brightones.data.usecases.post.GetCommentsForPostUseCaseImpl
import com.brighthr.technicaltest.brightones.domain.common.AppError
import com.brighthr.technicaltest.brightones.domain.common.Resource
import com.brighthr.technicaltest.brightones.domain.models.post.Comment
import com.brighthr.technicaltest.brightones.domain.models.post.CommentData
import com.brighthr.technicaltest.brightones.domain.repository.PostRepository
import com.brighthr.technicaltest.brightones.domain.usecases.post.GetCommentsForPostUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/*
This class will perform unit test on GetCommentForPostUseCaseImpl
 */
class GetCommentForPostUseCaseImplTest {

    private lateinit var repository: PostRepository
    private lateinit var getCommentsForPostUseCase: GetCommentsForPostUseCase

    @BeforeTest
    fun setUp() {
        repository = mockk()
        getCommentsForPostUseCase = GetCommentsForPostUseCaseImpl(repository)
    }

    /*
    Following test will fake the empty response of CommentData when
    repository.getCommentsForPost() is called from use case
     */
    @Test
    fun invoke_returnsSuccess_withEmptyCommentList() = runBlocking {
        val fakeResponse = Resource.Success(CommentData(listOf()))
        coEvery { repository.getCommentsForPost(1) } returns fakeResponse

        val result = getCommentsForPostUseCase(postId = 1)

        assertTrue(result is Resource.Success)
        assertEquals(fakeResponse, result)
    }

    /*
    Following test will return an list of comments with success
     */
    @Test
    fun invoke_returnsSuccess_withNonEmptyCommentsList() = runBlocking {
        val comments = listOf(Comment(1, 1, "name", "email", "body"))
        val fakeResponse = Resource.Success(CommentData(comments))
        coEvery { repository.getCommentsForPost(1) } returns fakeResponse

        val result = getCommentsForPostUseCase(postId = 1)

        assertTrue(result is Resource.Success)
        assertEquals(comments, result.data?.comments)
    }

    /*
    Following test will create an No internet error with error response
     */
    @Test
    fun invoke_returnsError_whenRepositoryFails() = runBlocking {
        val error = Resource.Error<CommentData>(AppError.NoInternet)
        coEvery { repository.getCommentsForPost(1) } returns error

        val result = getCommentsForPostUseCase(postId = 1)

        assertTrue(result is Resource.Error)
        assertEquals(AppError.NoInternet, result.error)
    }

    @Test
    fun invoke_returnsLoading_whenRepositoryReturnsLoading() = runBlocking {
        val loading = Resource.Loading<CommentData>()
        coEvery { repository.getCommentsForPost(1) } returns loading

        val result = getCommentsForPostUseCase(1)

        assertTrue(result is Resource.Loading)
    }
}