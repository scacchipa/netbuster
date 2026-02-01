package ar.com.westsoft.netbuster.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun BarButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconId: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color =
                    if (isSelected)
                        MaterialTheme.colorScheme.surface
                    else MaterialTheme.colorScheme.surfaceVariant
            )
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .clickable(onClick = onClick),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text)
            Image(
                painter = painterResource(iconId),
                contentDescription = text
            )
        }
    }
}