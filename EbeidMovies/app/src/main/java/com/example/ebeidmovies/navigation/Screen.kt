package com.example.ebeidmovies.navigation

sealed class Screen(val route: String) {
    object MovieList : Screen("movie_list")
    object AddMovie : Screen("add_movie")
    object MovieDetail : Screen("movie_detail/{movieId}") {
        fun createRoute(movieId: String) = "movie_detail/$movieId"
    }
}

