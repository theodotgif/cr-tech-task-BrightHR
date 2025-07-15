package com.brighthr.technicaltest.brightones.feature.post

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.brighthr.technicaltest.brightones.domain.common.AppError
import com.brighthr.technicaltest.brightones.domain.common.Resource
import com.brighthr.technicaltest.brightones.domain.models.post.AllPostData
import com.brighthr.technicaltest.brightones.domain.models.post.Comment
import com.brighthr.technicaltest.brightones.domain.models.post.CommentData
import com.brighthr.technicaltest.brightones.domain.models.post.Post
import com.brighthr.technicaltest.brightones.domain.usecases.post.GetAllPostUseCase
import com.brighthr.technicaltest.brightones.domain.usecases.post.GetCommentsForPostUseCase
import com.brighthr.technicaltest.brightones.features.post.viewmodel.PostViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Test dispatcher for coroutines, allows controlling coroutine execution.
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getAllPostUseCase: GetAllPostUseCase
    private lateinit var getCommentsForPostUseCase: GetCommentsForPostUseCase
    private lateinit var viewModel: PostViewModel

    @Before
    fun setup() {
        // Set the main dispatcher to our test dispatcher for coroutine testing
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)

        // Initialize the mock for GetAllPostUseCase & GetCommentsForPostUseCase
        // Using MockK:
        getAllPostUseCase = mockk()
        getCommentsForPostUseCase = mockk()

        // Initialize the ViewModel with the mocked dependency
        viewModel = PostViewModel(getAllPostUseCase, getCommentsForPostUseCase)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher after tests
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    @Test
    fun loadPost_emits_posts_when_use_case_returns_success() = runTest {
        val dummyPosts = listOf(
            Post(userId = 1, id = 1, title = "Title 1", body = "Body 1"),
            Post(userId = 1, id = 2, title = "Title 2", body = "Body 2")
        )
        val allPostData = AllPostData(posts = dummyPosts)

        coEvery { getAllPostUseCase() } returns Resource.Success(allPostData)

        viewModel.loadPost()

        advanceUntilIdle()

        val posts = viewModel.posts.value

        assertEquals(dummyPosts, posts)
    }

    @Test
    fun loadPost_does_not_emit_posts_when_use_case_returns_error() = runTest {
        coEvery { getAllPostUseCase() } returns Resource.Error(error = AppError.NetworkError(
            code = 1,
            message = "Something went wrong")
        )

        viewModel.loadPost()

        advanceUntilIdle()

        assertTrue(viewModel.posts.value.isEmpty())
    }

    @Test
    fun loadPost_emits_empty_list_when_use_case_returns_empty_data() = runTest {
        val fakeResponse = Resource.Success(AllPostData(emptyList()))
        coEvery { getAllPostUseCase() } returns fakeResponse

        viewModel.loadPost()

        advanceUntilIdle()

        assertTrue(viewModel.posts.value.isEmpty())
    }

    @Test
    fun loadComments_emits_comments_when_use_case_returns_success() = runTest {
        val dummyComments = listOf(
            Comment(postId = 1, id = 1, email = "email 1", name = "name 1", body = "Body 1"),
            Comment(postId = 1, id = 2, email = "email 2", name = "name 2", body = "Body 2")
        )
        val commentsData = CommentData(comments = dummyComments)

        coEvery { getCommentsForPostUseCase(postId = 1) } returns Resource.Success(commentsData)

        viewModel.loadComments(postId = 1)

        advanceUntilIdle()

        val comments = viewModel.comments.value

        assertEquals(dummyComments, comments)
    }

    @Test
    fun loadComments_does_not_emit_comments_when_use_case_returns_error() = runTest {
        coEvery { getCommentsForPostUseCase(postId = 1) } returns Resource.Error(error = AppError.NetworkError(
            code = 1,
            message = "Something went wrong")
        )

        viewModel.loadComments(postId = 1)

        advanceUntilIdle()

        assertTrue(viewModel.comments.value.isEmpty())
    }

    @Test
    fun loadComments_emits_empty_list_when_use_case_returns_empty_data() = runTest {
        val fakeResponse = Resource.Success(CommentData(emptyList()))
        coEvery { getCommentsForPostUseCase(postId = 1) } returns fakeResponse

        viewModel.loadComments(postId = 1)

        advanceUntilIdle()

        assertTrue(viewModel.comments.value.isEmpty())
    }
}