package com.brighthr.technicaltest.brightones.features.post.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.brighthr.technicaltest.brightones.domain.models.post.Comment
import com.brighthr.technicaltest.brightones.features.post.viewmodel.PostViewModel

@Composable
fun PostCommentScreen(
    postId: Int,
    viewModel : PostViewModel,
) {

    val comments by viewModel.comments.collectAsState()

    // Trigger comment loading when screen appears
    LaunchedEffect(postId) {
        viewModel.loadComments(postId)
    }

    CommentView(
        comments = comments,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentView(
    comments: List<Comment>,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = "Comments") })
        }
    ) { padding ->

        /*
        Instead of column, needs to take LazyColum so that
        it can have scrollable view with lazy loading of items
        with respect to phone visible contents
         */
        LazyColumn (modifier = Modifier
            .fillMaxSize()
            .padding(padding)
        ) {
            items(comments.size) { index ->
                val comment = comments[index]

                CommentView(
                    comment = comment,
                )
                HorizontalDivider(
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
fun CommentView(
    comment: Comment,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .testTag("CommentItem")
    ) {
        Text(
            text = comment.email,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = comment.body
        )
    }
}