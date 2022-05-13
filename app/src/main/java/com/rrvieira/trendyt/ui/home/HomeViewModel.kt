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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: MoviesRepository) : ViewModel() {
    var movieList: List<Movie> by mutableStateOf(listOf())

    private val _movieState = MutableStateFlow<HomeViewModelState>(HomeViewModelState.START)
    val movieState : StateFlow<HomeViewModelState> = _movieState

    fun getMovieList() {
        viewModelScope.launch {
            apiService.fetchPopularMovies(1).onSuccess { data ->
                movieList = data
                _movieState.emit(HomeViewModelState.SUCCESS)
            }.onFailure { error ->
                _movieState.emit(HomeViewModelState.FAILURE(error.localizedMessage ?: ""))
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
