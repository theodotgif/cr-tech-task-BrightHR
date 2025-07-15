package com.brighthr.technicaltest.brightones.network.post.api

import com.brighthr.technicaltest.brightones.data.common.NetworkResponse
import com.brighthr.technicaltest.brightones.network.post.PostRESTDataSource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.Test

/*
This class will test possible success and error state of Post Data source by mocking
service with MockWebServer
 */
class PostRESTDataSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var postService: PostService
    private lateinit var dataSource: PostRESTDataSource

    @Before
    fun setup() {
        System.setProperty("okhttp.platform", "org.conscrypt.OpenSSLProvider")

        mockWebServer = MockWebServer()
        mockWebServer.start()

        postService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostService::class.java)

        dataSource = PostRESTDataSource(postService)
    }

    @After
    fun shutDown() {
        mockWebServer.shutdown()
    }

    /*
    Check for 200 success parsing
     */
    @Test
    fun getAllPosts_returns_Success_200() = runBlocking {
        val mockJson = """
            [
                {
                  "userId": 1,
                  "id": 1,
                  "title": "Test title",
                  "body": "Test body"
                }
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockJson)
        )

        val result = dataSource.getAllPosts()

        assertTrue(result is NetworkResponse.Success)
        val posts = (result as NetworkResponse.Success).response.posts
        assertEquals(1, posts.size)
        assertEquals("Test title", posts[0].title)
    }

    /*
    Check for bad request
     */
    @Test
    fun getAllPosts_returns_ClientError_400() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody("{ \"message\": \"Bad Request\" }")
        )

        val result = dataSource.getAllPosts()

        assertTrue(result is NetworkResponse.ClientError)
        assertEquals(400, (result as NetworkResponse.ClientError).code)
    }

    /*
    Check for server error 500
     */
    @Test
    fun getAllPosts_returns_ClientError_500() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("{ \"message\": \"Internal server error\" }")
        )

        val result = dataSource.getAllPosts()

        assertTrue(result is NetworkResponse.ServerError)
        assertEquals(500, (result as NetworkResponse.ServerError).code)
    }

    /*
    Check for No internet
     */
    @Test
    fun getAllPosts_returns_NoInternet() = runBlocking {
        mockWebServer.shutdown()

        val result = dataSource.getAllPosts()

        assertTrue(result is NetworkResponse.NoInternet)
    }

    /*
    Following test we will try to break by passing wrong json
     */
    @Test
    fun getAllPosts_returns_Success_withParsingError() = runBlocking {
        // To mock parsing error, removed array []
        val mockJson = """
            {
              "userId": 1,
              "id": 1,
              "title": "Test title",
              "body": "Test body"
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockJson)
        )

        val result = dataSource.getAllPosts()

        assertTrue(result is NetworkResponse.ClientError)
        assertEquals(-1, (result as NetworkResponse.ClientError).code)
    }

    /*
    Check for 200 success parsing
     */
    @Test
    fun getCommentsForPost_returns_Success_200() = runBlocking {
        val mockJson = """
            [
                {
                  "postId": 1,
                  "id": 1,
                  "email": "email",
                  "name": "name",
                  "body": "Test body"
                }
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockJson)
        )

        val result = dataSource.getCommentsForPost(postId = 1)

        assertTrue(result is NetworkResponse.Success)
        val posts = (result as NetworkResponse.Success).response.comments
        assertEquals(1, posts.size)
        assertEquals("email", posts[0].email)
    }

    /*
    Check for bad request
     */
    @Test
    fun getCommentsForPost_returns_ClientError_400() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody("{ \"message\": \"Bad Request\" }")
        )

        val result = dataSource.getCommentsForPost(postId = 1)

        assertTrue(result is NetworkResponse.ClientError)
        assertEquals(400, (result as NetworkResponse.ClientError).code)
    }

    /*
    Check for server error 500
     */
    @Test
    fun getCommentsForPost_returns_ClientError_500() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("{ \"message\": \"Internal server error\" }")
        )

        val result = dataSource.getCommentsForPost(postId = 1)

        assertTrue(result is NetworkResponse.ServerError)
        assertEquals(500, (result as NetworkResponse.ServerError).code)
    }

    /*
    Check for No internet
     */
    @Test
    fun getCommentsForPost_returns_NoInternet() = runBlocking {
        mockWebServer.shutdown()

        val result = dataSource.getCommentsForPost(postId = 1)

        assertTrue(result is NetworkResponse.NoInternet)
    }

    /*
    Following test we will try to break by passing wrong json
     */
    @Test
    fun getCommentsForPost_returns_Success_withParsingError() = runBlocking {
        // To mock parsing error, removed array []
        val mockJson = """
            {
              "postId": 1,
              "id": 1,
              "email": "email",
              "name": "name",
              "body": "Test body"
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockJson)
        )

        val result = dataSource.getCommentsForPost(postId = 1)

        assertTrue(result is NetworkResponse.ClientError)
        assertEquals(-1, (result as NetworkResponse.ClientError).code)
    }
}