package com.rrvieira.trendyt.ui.details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rrvieira.trendyt.R
import com.rrvieira.trendyt.model.MovieDetails
import com.rrvieira.trendyt.ui.LocalDateFormatter
import com.rrvieira.trendyt.ui.LocalTimeFormatter
import com.rrvieira.trendyt.ui.TrendytAppFoundation
import com.rrvieira.trendyt.ui.components.Rating
import com.rrvieira.trendyt.ui.components.RetryScreen
import com.rrvieira.trendyt.utils.DateFormatter
import com.rrvieira.trendyt.utils.TimeFormatter
import java.util.*

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    uiState: DetailsUiState.HasDetails,
    onTopBarBackPressed: () -> Unit
) {
    val movie = uiState.movieDetails
    val dateFormatter = LocalDateFormatter.current
    val timeFormatter = LocalTimeFormatter.current

    DetailsScaffold(
        modifier = modifier,
        title = "${movie.title} (${
            dateFormatter.formatYear(
                movie.releaseDate
            )
        })",
        onTopBarBackPressed = onTopBarBackPressed
    ) { contentPadding ->
        Details(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(contentPadding),
            movie = uiState.movieDetails,
            dateFormatter = dateFormatter,
            timeFormatter = timeFormatter
        )
    }
}

@Composable
fun EmptyDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: DetailsUiState.Empty,
    onTopBarBackPressed: () -> Unit,
    onRetry: () -> Unit
) {
    DetailsScaffold(
        modifier = modifier,
        title = "",
        onTopBarBackPressed = onTopBarBackPressed
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.errorMessage != null) {
                RetryScreen(
                    modifier = Modifier.fillMaxSize(),
                    onRetry = onRetry
                )
            } else {
                LinearProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScaffold(
    modifier: Modifier = Modifier,
    title: String,
    onTopBarBackPressed: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Surface(shadowElevation = 4.dp) {
                SmallTopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .wrapContentSize(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary),
                                text = title,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onTopBarBackPressed
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
            }

        },
        content = content
    )
}

@Composable
fun Details(
    modifier: Modifier = Modifier,
    movie: MovieDetails,
    dateFormatter: DateFormatter = LocalDateFormatter.current,
    timeFormatter: TimeFormatter = LocalTimeFormatter.current
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Header(
                backdropUrl = movie.backdropUrl,
                posterUrl = movie.posterUrl
            )
        }

        if (movie.tagline.isNotBlank()) {
            item {
                Tagline(
                    tagline = movie.tagline
                )
            }
        }
        item {
            Highlights(
                rating = movie.voteAverage,
                releaseDate = movie.releaseDate,
                runtime = movie.runtime,
                genres = movie.genres,
                dateFormatter = dateFormatter,
                timeFormatter = timeFormatter
            )
        }
        item {
            Overview(overview = movie.overview)
        }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    backdropUrl: String,
    posterUrl: String
) {
    val backdropAspectRatio = 1.78f
    val crossfadeTransitionMilis = 500

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.CenterStart
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = backdropAspectRatio)
                .wrapContentHeight(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(backdropUrl)
                .crossfade(durationMillis = crossfadeTransitionMilis)
                .build(),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = backdropAspectRatio)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            Color.Transparent
                        )
                    )
                )
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, start = 16.dp)
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(posterUrl)
                    .crossfade(crossfadeTransitionMilis)
                    .build(),
                contentScale = ContentScale.Fit,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun Tagline(
    modifier: Modifier = Modifier,
    tagline: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(top = 16.dp, bottom = 16.dp),
        style = MaterialTheme.typography.bodyMedium.copy(
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onPrimary
        ),
        text = tagline,
        textAlign = TextAlign.Center
    )
}

@Composable
fun Highlights(
    modifier: Modifier = Modifier,
    rating: Float,
    releaseDate: Date,
    runtime: Int,
    genres: List<String>,
    dateFormatter: DateFormatter = LocalDateFormatter.current,
    timeFormatter: TimeFormatter = LocalTimeFormatter.current
) {
    Card(
        modifier = modifier,
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Rating(
                modifier = Modifier.height(20.dp),
                rating = rating,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = dateFormatter.formatNumericDate(releaseDate),
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = stringResource(id = R.string.time_separator_char),
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = timeFormatter.formatMinutesInHours(runtime),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                itemsIndexed(items = genres) { index, genre ->
                    val text = if (index < genres.size - 1) {
                        "$genre, "
                    } else {
                        genre
                    }

                    Text(
                        text = text,
                        style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
    }
}

@Composable
fun Overview(
    modifier: Modifier = Modifier,
    overview: String
) {
    Column(
        modifier = modifier.padding(
            start = 16.dp,
            bottom = 16.dp,
            end = 16.dp,
            top = 32.dp
        )
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.movie_details_overview_title),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = overview,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDetailsScreen() {
    TrendytAppFoundation {
        DetailsScreen(
            uiState = DetailsUiState.HasDetails(
                movieDetails = MovieDetails(
                    id = 0,
                    title = "Movie Title",
                    runtime = 140,
                    tagline = "Tagline text",
                    overview = "Overview text",
                    releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time,
                    voteAverage = 8.1f,
                    genres = listOf(
                        "Action",
                        "Thriller"
                    ),
                    backdropUrl = "backgropUrl",
                    posterUrl = "posterUrl"
                )
            )
        ) {}
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewEmptyDetailsScreenWithLoading() {
    TrendytAppFoundation {
        EmptyDetailsScreen(
            uiState = DetailsUiState.Empty(
                isLoading = true,
                errorMessage = null
            ),
            onTopBarBackPressed = {},
            onRetry = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewEmptyDetailsScreenWithError() {
    TrendytAppFoundation {
        EmptyDetailsScreen(
            uiState = DetailsUiState.Empty(
                isLoading = false,
                errorMessage = "some error"
            ),
            onTopBarBackPressed = {},
            onRetry = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDetailsScaffold() {
    TrendytAppFoundation {
        DetailsScaffold(title = "The main title",
            onTopBarBackPressed = {

            }) {
            Text(
                modifier = Modifier.padding(it),
                text = "Some content")
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewHeader() {
    TrendytAppFoundation {
        Header(backdropUrl = "backgropUrl", posterUrl = "posterUrl")
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewTagline() {
    TrendytAppFoundation {
        Tagline(tagline = "Tagline text")
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewHighlights() {
    TrendytAppFoundation {
        Highlights(
            runtime = 140,
            rating = 8.1f,
            releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time,
            genres = listOf(
                "Action",
                "Thriller"
            )
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewOverview() {
    TrendytAppFoundation {
        Overview(
            overview = "Overview text."
        )
    }
}
