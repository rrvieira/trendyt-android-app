package com.rrvieira.trendyt.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rrvieira.trendyt.data.movies.MoviesRepository
import com.rrvieira.trendyt.model.MovieSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesRepo: MoviesRepository) : ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))

    // UI state exposed to the UI
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        refreshMovies()
    }

    private fun refreshMovies() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = moviesRepo.fetchPopularMovies(1)
            viewModelState.update { state ->
                val feed = result.getOrElse { throwable ->
                    val errorMessages = state.errorMessages + (throwable.message ?: "")
                    return@update state.copy(errorMessages = errorMessages, isLoading = false)
                }

                state.copy(moviesFeed = feed, isLoading = false)
            }
        }
    }
}

sealed interface HomeUiState {
    val isLoading: Boolean
    val errorMessages: List<String>

    data class NoMovies(
        override val isLoading: Boolean,
        override val errorMessages: List<String>
    ) : HomeUiState

    data class HasMovies(
        val moviesFeed: List<MovieSummary>,
        override val isLoading: Boolean,
        override val errorMessages: List<String>
    ) : HomeUiState
}

private data class HomeViewModelState(
    val moviesFeed: List<MovieSummary>? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<String> = emptyList()
) {
    fun toUiState(): HomeUiState =
        when {
            moviesFeed != null -> {
                HomeUiState.HasMovies(
                    moviesFeed = moviesFeed,
                    isLoading = isLoading,
                    errorMessages = errorMessages,
                )
            }
            else -> {
                HomeUiState.NoMovies(
                    isLoading = isLoading,
                    errorMessages = errorMessages,
                )
            }
        }
}
