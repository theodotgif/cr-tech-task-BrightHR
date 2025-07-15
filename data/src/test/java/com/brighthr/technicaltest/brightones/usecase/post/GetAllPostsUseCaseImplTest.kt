package com.brighthr.technicaltest.brightones.usecase.post

import com.brighthr.technicaltest.brightones.data.usecases.post.GetAllPostsUseCaseImpl
import com.brighthr.technicaltest.brightones.domain.common.AppError
import com.brighthr.technicaltest.brightones.domain.common.Resource
import com.brighthr.technicaltest.brightones.domain.models.post.AllPostData
import com.brighthr.technicaltest.brightones.domain.models.post.Post
import com.brighthr.technicaltest.brightones.domain.repository.PostRepository
import com.brighthr.technicaltest.brightones.domain.usecases.post.GetAllPostUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/*
This class will perform unit test on GetAllPostsUseCaseImpl
 */
class GetAllPostsUseCaseImplTest {

    private lateinit var repository: PostRepository
    private lateinit var getAllPostUseCase: GetAllPostUseCase

    @BeforeTest
    fun setUp() {
        repository = mockk()
        getAllPostUseCase = GetAllPostsUseCaseImpl(repository)
    }

    /*
    Following test will fake the empty response of AllPostData when
    repository.getAllPosts() is called from use case
     */
    @Test
    fun invoke_returnsSuccess_withEmptyPostList() = runBlocking {
        val fakeResponse = Resource.Success(AllPostData(listOf()))
        coEvery { repository.getAllPosts() } returns fakeResponse

        val result = getAllPostUseCase()

        assertTrue(result is Resource.Success)
        assertEquals(fakeResponse, result)
    }

    /*
    Following test will return an list of posts with success
     */
    @Test
    fun invoke_returnsSuccess_withNonEmptyPostList() = runBlocking {
        val posts = listOf(Post(1, 1, "title", "body"))
        val fakeResponse = Resource.Success(AllPostData(posts))
        coEvery { repository.getAllPosts() } returns fakeResponse

        val result = getAllPostUseCase()

        assertTrue(result is Resource.Success)
        assertEquals(posts, result.data?.posts)
    }

    /*
    Following test will create an No internet error with error response
     */
    @Test
    fun invoke_returnsError_whenRepositoryFails() = runBlocking {
        val error = Resource.Error<AllPostData>(AppError.NoInternet)
        coEvery { repository.getAllPosts() } returns error

        val result = getAllPostUseCase()

        assertTrue(result is Resource.Error)
        assertEquals(AppError.NoInternet, result.error)
    }

    @Test
    fun invoke_returnsLoading_whenRepositoryReturnsLoading() = runBlocking {
        val loading = Resource.Loading<AllPostData>()
        coEvery { repository.getAllPosts() } returns loading

        val result = getAllPostUseCase()

        assertTrue(result is Resource.Loading)
    }
}