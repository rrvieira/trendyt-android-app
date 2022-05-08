package com.rrvieira.trendyt.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rrvieira.trendyt.R
import com.rrvieira.trendyt.ui.home.HomeViewModelState
import com.rrvieira.trendyt.ui.home.HomeViewModel
import com.rrvieira.trendyt.ui.home.MovieFeedScreen
import com.rrvieira.trendyt.ui.theme.TrendytTheme

@Composable
fun TrendytApp() {
    val viewModel: HomeViewModel = viewModel()
    val state by viewModel.movieState.collectAsState()
    viewModel.getMovieList()

    TrendytTheme {
        state.let { state ->
            when (state) {
                HomeViewModelState.START -> {
                }
                HomeViewModelState.LOADING -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(color = colorResource(id = R.color.purple_700))
                    }
                }
                is HomeViewModelState.FAILURE -> {
                    val message = state.message
                    val context = LocalContext.current
                    LaunchedEffect(key1 = message, context) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }

                HomeViewModelState.SUCCESS -> {
                    MovieFeedScreen(movieList = viewModel.movieListResponse)
                }
            }
        }
    }
}
