package com.rrvieira.trendyt.ui.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState

@Composable
fun DetailsRoute(
    viewModel: DetailsViewModel,
    movieId: Int,
    onTopBarBackPressed: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.fetchMovieDetails(movieId)
    }

    viewModel.uiState.collectAsState().value.let { state ->
        when (state) {
            is DetailsUiState.Empty -> Text(text = "EMPTY")
            is DetailsUiState.NoDetails -> Text(text = "NoDetails")
            is DetailsUiState.HasDetails -> HasDetailsScreen(
                uiState = state,
                onTopBarBackPressed = onTopBarBackPressed
            )
        }
    }
}
