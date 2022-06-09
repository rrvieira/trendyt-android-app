package com.rrvieira.trendyt.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rrvieira.trendyt.data.movies.MoviesRepository
import com.rrvieira.trendyt.model.MovieSummary
import com.rrvieira.trendyt.model.MovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val moviesRepo: MoviesRepository) : ViewModel() {

    private val viewModelState = MutableStateFlow(DetailsViewModelState(isLoading = true))

    // UI state exposed to the UI
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        Log.d("AQUI", "CRIADO")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("AQUI", "onCleared")
    }

    fun fetchMovieDetails(movieId: Int) {
        Log.d("AQUI", "fetchMovieDetails")
        viewModelState.update { it.copy(isLoading = true, movieSummary = null) }

        viewModelScope.launch {
            val result = moviesRepo.fetchMovieDetails(movieId)
            viewModelState.update { state ->
                val movieDetails = result.getOrElse { throwable ->
                    val errorMessages = state.errorMessages + (throwable.message ?: "")
                    return@update state.copy(errorMessages = errorMessages, isLoading = false)
                }

                state.copy(movieDetails = movieDetails, isLoading = false)
            }
        }
    }
}

sealed interface DetailsUiState {
    val isLoading: Boolean
    val errorMessages: List<String>

    data class Empty(
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
    ) : DetailsUiState

    data class NoDetails(
        val movieSummary: MovieSummary,
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
    ) : DetailsUiState

    data class HasDetails(
        val movieDetails: MovieDetails,
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
    ) : DetailsUiState
}

private data class DetailsViewModelState(
    val movieSummary: MovieSummary? = null,
    val movieDetails: MovieDetails? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<String> = emptyList()
) {
    fun toUiState(): DetailsUiState = when {
        movieDetails != null -> {
            DetailsUiState.HasDetails(
                movieDetails = movieDetails,
                isLoading = true,
                errorMessages = emptyList()
            )
        }
        movieSummary != null -> {
            DetailsUiState.NoDetails(
                movieSummary = movieSummary,
                isLoading = true,
                errorMessages = emptyList()
            )
        }
        else -> {
            DetailsUiState.Empty(
                isLoading = true,
                errorMessages = emptyList()
            )
        }
    }


}
