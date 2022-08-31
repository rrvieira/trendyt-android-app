package com.rrvieira.trendyt.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rrvieira.trendyt.data.movies.MoviesRepository
import com.rrvieira.trendyt.model.MovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val moviesRepo: MoviesRepository) : ViewModel() {

    private val viewModelState = MutableStateFlow(DetailsViewModelState(isLoading = true))

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun fetchMovieDetails(movieId: Int) {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = moviesRepo.fetchMovieDetails(movieId)
            viewModelState.update { state ->
                val movieDetails = result.getOrElse { throwable ->
                    val errorMessage = throwable.message ?: ""
                    return@update state.copy(errorMessage = errorMessage, isLoading = false)
                }

                state.copy(movieDetails = movieDetails, isLoading = false)
            }
        }
    }
}

sealed interface DetailsUiState {
    data class Empty(
        val isLoading: Boolean,
        val errorMessage: String?,
    ) : DetailsUiState

    data class HasDetails(
        val movieDetails: MovieDetails
    ) : DetailsUiState
}

private data class DetailsViewModelState(
    val movieDetails: MovieDetails? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    fun toUiState(): DetailsUiState = when {
        movieDetails != null -> {
            DetailsUiState.HasDetails(
                movieDetails = movieDetails
            )
        }
        else -> {
            DetailsUiState.Empty(
                isLoading = isLoading,
                errorMessage = errorMessage
            )
        }
    }
}
