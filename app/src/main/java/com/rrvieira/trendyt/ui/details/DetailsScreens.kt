package com.rrvieira.trendyt.ui.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HasDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: DetailsUiState.HasDetails,
    onTopBarBackPressed: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = uiState.movieDetails.title,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onTopBarBackPressed()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.surface,
                elevation = 4.dp
            )

        }

    ) { contentPadding ->
        Text(
            modifier = Modifier.padding(contentPadding),
            text = uiState.movieDetails.title
        )
    }
}
