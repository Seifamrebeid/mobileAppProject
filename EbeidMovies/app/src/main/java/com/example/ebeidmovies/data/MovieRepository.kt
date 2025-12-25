package com.example.ebeidmovies.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MovieRepository {
    private val movies = MutableStateFlow<List<Movie>>(emptyList())

    fun getAllMovies(): Flow<List<Movie>> = movies.asStateFlow()

    suspend fun addMovie(movie: Movie) {
        movies.update { currentList ->
            currentList + movie
        }
    }

    suspend fun updateMovie(movie: Movie) {
        movies.update { currentList ->
            currentList.map {
                if (it.id == movie.id) movie else it
            }
        }
    }

    suspend fun deleteMovie(movieId: String) {
        movies.update { currentList ->
            currentList.filter { it.id != movieId }
        }
    }

    suspend fun toggleWatchedStatus(movieId: String) {
        movies.update { currentList ->
            currentList.map { movie ->
                if (movie.id == movieId) {
                    movie.copy(isWatched = !movie.isWatched)
                } else {
                    movie
                }
            }
        }
    }

    fun getMovieById(movieId: String): Movie? {
        return movies.value.find { it.id == movieId }
    }
}

