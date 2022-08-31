package com.rrvieira.trendyt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rrvieira.trendyt.ui.theme.TrendytTheme
import com.rrvieira.trendyt.utils.DateFormatter
import com.rrvieira.trendyt.utils.TimeFormatter

@Composable
fun TrendytApp() {
    val useDarkTheme = isSystemInDarkTheme()

    TrendytAppFoundation(useDarkTheme = useDarkTheme) {
        val systemUiController = rememberSystemUiController()
        //make sure the system bars match the current theme
        SideEffect {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = !useDarkTheme)
        }

        Row(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .statusBarsPadding()
                .windowInsetsPadding(
                    WindowInsets
                        .navigationBars
                        .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                )
        ) {
            TrendytNavGraph()
        }
    }

}

@Composable
fun TrendytAppFoundation(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    CompositionLocalProvider(
        LocalDateFormatter provides DateFormatter,
        LocalTimeFormatter provides TimeFormatter
    ) {
        TrendytTheme(useDarkTheme = useDarkTheme, content = content)
    }
}
