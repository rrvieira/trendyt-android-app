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

    private val viewModelState = MutableStateFlow(HomeViewModelState(isRefreshing = true))

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

    fun refreshMovies() {
        viewModelState.update { it.copy(isRefreshing = true, errorMessages = emptyList()) }

        viewModelScope.launch {
            val result = moviesRepo.fetchPopularMovies(1)
            viewModelState.update { state ->
                val feed = result.getOrElse { throwable ->
                    val errorMessages = state.errorMessages + (throwable.message ?: "")
                    return@update state.copy(errorMessages = errorMessages, isRefreshing = false)
                }

                state.copy(moviesFeed = feed, isRefreshing = false, errorMessages = emptyList())
            }
        }
    }
}

sealed interface HomeUiState {
    val isRefreshing: Boolean
    val errorMessages: List<String>
    val hasError: Boolean
        get() = errorMessages.isNotEmpty()

    data class NoMovies(
        override val isRefreshing: Boolean,
        override val errorMessages: List<String>
    ) : HomeUiState

    data class HasMovies(
        val moviesFeed: List<MovieSummary>,
        override val isRefreshing: Boolean,
        override val errorMessages: List<String>
    ) : HomeUiState
}

private data class HomeViewModelState(
    val moviesFeed: List<MovieSummary>? = null,
    val isRefreshing: Boolean = false,
    val errorMessages: List<String> = emptyList()
) {
    fun toUiState(): HomeUiState =
        when {
            moviesFeed != null && moviesFeed.isNotEmpty() -> {
                HomeUiState.HasMovies(
                    moviesFeed = moviesFeed,
                    isRefreshing = isRefreshing,
                    errorMessages = errorMessages,
                )
            }
            else -> {
                HomeUiState.NoMovies(
                    isRefreshing = isRefreshing,
                    errorMessages = errorMessages,
                )
            }
        }
}
