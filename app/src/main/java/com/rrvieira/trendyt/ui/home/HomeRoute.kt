package com.rrvieira.trendyt.ui.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    onSelectMovie: (Int) -> Unit,
) {
    val lazyListState: LazyListState = rememberLazyListState()

    viewModel.uiState.collectAsState().value.let { state ->
        when (state) {
            is HomeUiState.HasMovies -> MovieFeedScreen(
                uiState = state,
                moviesLazyListState = lazyListState,
                onSelectMovie = onSelectMovie
            )
            is HomeUiState.NoMovies -> NoMoviesScreen(
                uiState = state,
                onRefreshMovies = {
                    viewModel.refreshMovies()
                }
            )
        }
    }
}
