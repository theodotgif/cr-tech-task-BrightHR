package com.brighthr.technicaltest.brightones.common

import com.brighthr.technicaltest.brightones.data.common.NetworkResponse
import com.brighthr.technicaltest.brightones.data.common.networkBoundResource
import com.brighthr.technicaltest.brightones.domain.common.AppError
import com.brighthr.technicaltest.brightones.domain.common.Resource
import com.brighthr.technicaltest.brightones.domain.models.post.AllPostData
import com.brighthr.technicaltest.brightones.domain.models.post.Post
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkBoundResourceTest {

    /*
    Test for the empty success from network
     */
    @Test
    fun networkBoundResource_returnsResourceSuccess_whenResponseIsEmptyList() = runBlocking {
        val result = networkBoundResource {
            NetworkResponse.Success(AllPostData(posts = listOf()))
        }

        assertTrue(result is Resource.Success)
    }

    /*
    Test for the expected list in success from network
     */
    @Test
    fun networkBoundResource_returnsResourceSuccess_withCorrectData() = runBlocking {
        val expectedPosts = listOf(
            Post(userId = 1, id = 1, title = "Title 1", body = "Body 1"),
            Post(userId = 2, id = 2, title = "Title 2", body = "Body 2")
        )
        val expectedData = AllPostData(posts = expectedPosts)

        val result = networkBoundResource {
            NetworkResponse.Success(expectedData)
        }

        assertTrue(result is Resource.Success)
        assertEquals(expectedData, result.data)
    }

    /*
    Test for Client error from network
     */
    @Test
    fun networkBoundResource_returnsResourceError_withClientError() = runBlocking {
        val result = networkBoundResource<AllPostData> {
            NetworkResponse.ClientError(code = 400, message = "Bad request")
        }

        assertTrue(result is Resource.Error)
        assertTrue((result as Resource.Error).error is AppError.NetworkError)
    }

    /*
    Test for server error from network
     */
    @Test
    fun networkBoundResource_returnsResourceError_withServerError() = runBlocking {
        val result = networkBoundResource<AllPostData> {
            NetworkResponse.ServerError(code = 500, message = "Server Error")
        }

        assertTrue(result is Resource.Error)
        assertTrue((result as Resource.Error).error is AppError.NetworkError)
    }

    /*
    Test for no internet
     */
    @Test
    fun networkBoundResource_returnsResourceError_withNoInternet() = runBlocking {
        val result = networkBoundResource<AllPostData> {
            NetworkResponse.NoInternet()
        }

        assertTrue(result is Resource.Error)
        assertEquals(AppError.NoInternet, (result as Resource.Error).error)
    }
}