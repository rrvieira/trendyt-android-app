package com.rrvieira.trendyt.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rrvieira.trendyt.R
import com.rrvieira.trendyt.model.MovieSummary
import com.rrvieira.trendyt.ui.LocalDateFormatter
import com.rrvieira.trendyt.ui.components.Rating
import com.rrvieira.trendyt.utils.DateFormatter

val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieFeedScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState.HasMovies,
    moviesLazyListState: LazyListState,
    onSelectMovie: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.surface,
                elevation = if (!moviesLazyListState.isScrolled) 0.dp else 4.dp
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
        }
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
                    .wrapContentHeight(),
                model = movieSummary.imageUrl,
                contentScale = ContentScale.FillWidth,
                contentDescription = null
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
