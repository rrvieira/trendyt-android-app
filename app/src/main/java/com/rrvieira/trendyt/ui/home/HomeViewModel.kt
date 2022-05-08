package com.rrvieira.trendyt.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rrvieira.trendyt.data.movies.MoviesRepository
import com.rrvieira.trendyt.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: MoviesRepository) : ViewModel() {
    var movieListResponse: List<Movie> by mutableStateOf(listOf())
    val movieState = MutableStateFlow<HomeViewModelState>(HomeViewModelState.START)

    fun getMovieList() {
        viewModelScope.launch {
            apiService.fetchPopularMovies(1).onSuccess { data ->
                movieListResponse = data
                movieState.emit(HomeViewModelState.SUCCESS)
            }.onFailure { error ->
                movieState.emit(HomeViewModelState.FAILURE(error.localizedMessage ?: ""))
            }
        }
    }
}

sealed class HomeViewModelState {
    object START : HomeViewModelState()
    object LOADING : HomeViewModelState()
    object SUCCESS : HomeViewModelState()
    data class FAILURE(val message: String) : HomeViewModelState()
}
