package com.rrvieira.trendyt.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rrvieira.trendyt.R
import com.rrvieira.trendyt.ui.TrendytAppFoundation

@Composable
fun Rating(
    modifier: Modifier = Modifier,
    rating: Float,
    style: TextStyle = LocalTextStyle.current,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.wrapContentSize(),
            text = rating.toString(),
            style = style.copy(color = color)
        )
        Image(
            modifier = Modifier.padding(top = 2.dp, bottom = 2.dp).fillMaxHeight(),
            painter = painterResource(id = R.drawable.star_rate),
            contentScale = ContentScale.Fit,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color)
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewRating() {
    TrendytAppFoundation{
        Surface {
            Rating(
                modifier = Modifier.height(30.dp),
                rating = 8.1f
            )
        }
    }
}
