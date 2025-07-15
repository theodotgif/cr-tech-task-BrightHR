package com.brighthr.technicaltest.brightones.features.post.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighthr.technicaltest.brightones.domain.common.Resource
import com.brighthr.technicaltest.brightones.domain.models.post.AllPostData
import com.brighthr.technicaltest.brightones.domain.models.post.Comment
import com.brighthr.technicaltest.brightones.domain.models.post.CommentData
import com.brighthr.technicaltest.brightones.domain.models.post.Post
import com.brighthr.technicaltest.brightones.domain.usecases.post.GetAllPostUseCase
import com.brighthr.technicaltest.brightones.domain.usecases.post.GetCommentsForPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getAllPostUseCase: GetAllPostUseCase,
    private val getCommentsForPostUseCase: GetCommentsForPostUseCase,
): ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    fun loadPost() {
        viewModelScope.launch (Dispatchers.IO) {
            handlePostsResponse(getAllPostUseCase())
        }
    }

    private fun handlePostsResponse(result: Resource<AllPostData>) {
        when (result) {
            is Resource.Error -> {
                // Display error
            }
            is Resource.Success -> {
                result.data?.let { data ->
                    data.posts.let { posts ->
                        _posts.value = posts
                    }
                }
            }
            is Resource.Loading -> {
                // Handle loading state
            }
        }
    }

    fun loadComments(postId: Int) {
        _comments.value = emptyList()
        viewModelScope.launch (Dispatchers.IO) {
            handleCommentsResponse(getCommentsForPostUseCase(postId = postId))
        }
    }

    private fun handleCommentsResponse(result: Resource<CommentData>) {
        when (result) {
            is Resource.Error -> {
                // Display error
            }
            is Resource.Success -> {
                result.data?.let { data ->
                    data.comments.let { comments ->
                        _comments.value = comments
                    }
                }
            }
            is Resource.Loading -> {
                // Handle loading state
            }
        }
    }
}