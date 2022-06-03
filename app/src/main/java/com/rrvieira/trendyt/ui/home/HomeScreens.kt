package com.rrvieira.trendyt.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rrvieira.trendyt.model.Movie

@Composable
fun MovieFeedScreen(modifier: Modifier = Modifier, movieList: List<Movie>) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(items = movieList) { _, item ->
            MovieItem(movie = item)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 8.dp)
            .height(280.dp).clickable {

            },
    ) {

        Column(
            verticalArrangement = Arrangement.Top,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
            )

            Column(
                modifier = Modifier.padding(16.dp, 16.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = movie.category,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 4
                )
            }
        }
    }
}
