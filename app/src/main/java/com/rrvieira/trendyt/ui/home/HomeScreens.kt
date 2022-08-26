package com.rrvieira.trendyt.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rrvieira.trendyt.R
import com.rrvieira.trendyt.model.MovieSummary
import com.rrvieira.trendyt.ui.LocalDateFormatter
import com.rrvieira.trendyt.ui.components.Rating
import com.rrvieira.trendyt.ui.components.RetryScreen
import com.rrvieira.trendyt.utils.DateFormatter

@Composable
fun NoMoviesScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState.NoMovies,
    onRefreshMovies: () -> Unit
) {
    HomeScaffold(modifier = modifier) {
        when (uiState.hasError) {
            true -> {
                RetryScreen(
                    modifier = Modifier.fillMaxSize(),
                    messageStyle = MaterialTheme.typography.labelLarge
                ) {
                    onRefreshMovies()
                }
            }
            false -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Image(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(id = R.drawable.movie),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                    Spacer(Modifier.height(8.dp))
                    if (uiState.isLoading) {
                        LinearProgressIndicator()
                    } else {
                        Text(
                            style = MaterialTheme.typography.labelMedium,
                            text = stringResource(id = R.string.no_movies_message),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieFeedScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState.HasMovies,
    moviesLazyListState: LazyListState,
    onSelectMovie: (Int) -> Unit
) {
    HomeScaffold(
        modifier = modifier,
        lazyListState = moviesLazyListState
    ) { contentPadding ->
        LazyColumn(
            contentPadding = WindowInsets.systemBars
                .only(WindowInsetsSides.Bottom)
                .add(WindowInsets(top = contentPadding.calculateTopPadding()))
                .asPaddingValues(),
            state = moviesLazyListState
        ) {
            itemsIndexed(items = uiState.moviesFeed) { _, item ->
                MovieItem(movieSummary = item, onSelect = onSelectMovie)
            }
        }
    }
}

@Composable
fun MovieItem(
    movieSummary: MovieSummary,
    onSelect: (Int) -> Unit,
    dateFormatter: DateFormatter = LocalDateFormatter.current
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 8.dp)
            .clickable {
                onSelect(movieSummary.id)
            }
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 1.78f)
                    .wrapContentHeight()
                    .background(color = MaterialTheme.colorScheme.secondaryContainer),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movieSummary.imageUrl)
                    .crossfade(500)
                    .build(),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
            )

            Column(
                modifier = Modifier.padding(8.dp, 12.dp)
            ) {
                Text(
                    text = movieSummary.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = dateFormatter.formatDefaultDate(movieSummary.releaseDate),
                        style = MaterialTheme.typography.labelMedium
                    )

                    Rating(
                        modifier = Modifier.height(16.dp),
                        rating = movieSummary.rating,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScaffold(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState? = null,
    content: @Composable (PaddingValues) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.surface,
                elevation = if (!lazyListState.isScrolled) 0.dp else 4.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.trending_up),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                    Spacer(Modifier.width(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.trendyt_logo),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                }
            }
        },
        content = content
    )
}

val LazyListState?.isScrolled: Boolean
    get() = this?.let {
        firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0
    } ?: false
