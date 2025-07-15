package com.brighthr.technicaltest.brightones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.brighthr.technicaltest.brightones.features.post.ui.PostCommentScreen
import com.brighthr.technicaltest.brightones.features.post.ui.PostScreen
import com.brighthr.technicaltest.brightones.features.post.viewmodel.PostViewModel
import com.brighthr.technicaltest.brightones.ui.theme.BrightonesTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Please carefully read the README, in the root project directory,
 * in order to complete this tech task.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrightonesTheme {
                MainNavigation()
            }
        }
    }

    @Composable
    fun MainNavigation() {

        val navHostController = rememberNavController()
        val viewModel : PostViewModel = hiltViewModel()

        // get posts here from view model
        val posts by viewModel.posts.collectAsState()

        // Fetch Posts from API
        viewModel.loadPost()

        NavHost(navController = navHostController, startDestination = "post") {
            composable(route = "post") {
                PostScreen(
                    posts = posts,
                    onPostClick = { postId ->
                        navHostController.navigate("comments/$postId")
                    }

                )
            }

            composable(
                route = "comments/{postId}",
                arguments = listOf(navArgument("postId") { type = NavType.IntType })
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getInt("postId") ?: return@composable

                PostCommentScreen(
                    postId = postId,
                    viewModel = viewModel
                )
            }
        }
    }
}