package com.example.ebeidmovies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebeidmovies.data.Movie
import com.example.ebeidmovies.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovieUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class MovieViewModel(
    private val repository: MovieRepository = MovieRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    val movies: StateFlow<List<Movie>> = repository.getAllMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            repository.getAllMovies().collect { movieList ->
                _uiState.update {
                    it.copy(movies = movieList, isLoading = false)
                }
            }
        }
    }

    fun addMovie(
        title: String,
        director: String,
        releaseYear: Int,
        genre: String,
        notes: String = "",
        imageUri: String? = null
    ) {
        viewModelScope.launch {
            try {
                val movie = Movie(
                    title = title,
                    director = director,
                    releaseYear = releaseYear,
                    genre = genre,
                    notes = notes,
                    imageUri = imageUri
                )
                repository.addMovie(movie)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Failed to add movie: ${e.message}")
                }
            }
        }
    }

    fun toggleWatchedStatus(movieId: String) {
        viewModelScope.launch {
            try {
                repository.toggleWatchedStatus(movieId)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Failed to update movie: ${e.message}")
                }
            }
        }
    }

    fun deleteMovie(movieId: String) {
        viewModelScope.launch {
            try {
                repository.deleteMovie(movieId)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Failed to delete movie: ${e.message}")
                }
            }
        }
    }

    fun getMovieById(movieId: String): Movie? {
        return repository.getMovieById(movieId)
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

