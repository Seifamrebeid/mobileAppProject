package com.example.ebeidmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.ebeidmovies.navigation.MovieNavGraph
import com.example.ebeidmovies.ui.theme.EbeidMoviesTheme
import com.example.ebeidmovies.viewmodel.MovieViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EbeidMoviesTheme {
                val navController = rememberNavController()
                val viewModel: MovieViewModel = viewModel()

                MovieNavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}