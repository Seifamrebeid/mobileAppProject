package com.example.ebeidmovies.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ebeidmovies.ui.screens.AddMovieScreen
import com.example.ebeidmovies.ui.screens.MovieDetailScreen
import com.example.ebeidmovies.ui.screens.MovieListScreen
import com.example.ebeidmovies.viewmodel.MovieViewModel

@Composable
fun MovieNavGraph(
    navController: NavHostController,
    viewModel: MovieViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MovieList.route,
        modifier = modifier
    ) {
        composable(Screen.MovieList.route) {
            MovieListScreen(
                viewModel = viewModel,
                onNavigateToAddMovie = {
                    navController.navigate(Screen.AddMovie.route)
                },
                onNavigateToMovieDetail = { movieId ->
                    navController.navigate(Screen.MovieDetail.createRoute(movieId))
                }
            )
        }

        composable(Screen.AddMovie.route) {
            AddMovieScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.MovieDetail.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            movieId?.let {
                MovieDetailScreen(
                    viewModel = viewModel,
                    movieId = it,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

