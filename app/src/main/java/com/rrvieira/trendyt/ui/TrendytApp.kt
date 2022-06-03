package com.rrvieira.trendyt.ui

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rrvieira.trendyt.ui.home.HomeViewModel
import com.rrvieira.trendyt.ui.home.HomeViewModelState
import com.rrvieira.trendyt.ui.home.MovieFeedScreen
import com.rrvieira.trendyt.ui.theme.TrendytTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendytApp() {
    val useDarkTheme = isSystemInDarkTheme()

    val viewModel: HomeViewModel = viewModel()
    val state by viewModel.movieState.collectAsState()
    viewModel.getMovieList()

    TrendytTheme(useDarkTheme = useDarkTheme) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = !useDarkTheme)
        }

        Scaffold(
            topBar = {
                Surface(tonalElevation = 3.dp) {
                    SmallTopAppBar(
                        modifier = Modifier
                            .statusBarsPadding(),
                        title = {
                            Text(
                                textAlign = TextAlign.Center,
                                text = "Trendyt",
                            )
                        },
                    )
                }
            }

        ) { contentPadding ->
            state.let { state ->
                when (state) {
                    HomeViewModelState.START -> {
                    }
                    HomeViewModelState.LOADING -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(contentPadding)
                        ) {
                            CircularProgressIndicator()
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
                        MovieFeedScreen(
                            modifier = Modifier
                                .padding(contentPadding)
                                .navigationBarsPadding(),
                            movieList = viewModel.movieList
                        )
                    }
                }
            }

        }
    }
}
