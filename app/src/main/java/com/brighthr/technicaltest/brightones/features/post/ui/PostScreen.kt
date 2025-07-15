package com.brighthr.technicaltest.brightones.features.post.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.brighthr.technicaltest.brightones.domain.models.post.Post

@Composable
fun PostScreen(
    posts: List<Post>,
//    modifier: Modifier = Modifier, No need to pass modifier
    onPostClick: (Int) -> Unit
) {

    // posts should be collected in the parent of this screen
//    val posts by viewModel.posts.collectAsState()

    PostView(
        posts = posts,
        onPostClick = onPostClick
//        loadPost = viewModel::loadPost
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostView(
    posts: List<Post>,
    onPostClick: (Int) -> Unit
//    loadPost: () -> Unit
) {

    // Loading should happen in lifecycle
//    loadPost()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = "Post List") })
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
            items(posts.size) { index ->
                val post = posts[index]

                PostView(
                    post = post,
                    onPostClick = onPostClick
                )
                HorizontalDivider(
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
fun PostView(
    post: Post,
    onPostClick: (Int) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .clickable {
                onPostClick(post.id)
            }
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .testTag("PostItem")
    ) {
        Text(
            text = post.title,
            style = MaterialTheme.typography.titleSmall
        )
        Text(text = post.body)
    }
}