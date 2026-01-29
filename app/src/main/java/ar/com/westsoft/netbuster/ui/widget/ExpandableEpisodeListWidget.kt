package ar.com.westsoft.netbuster.ui.widget

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableEpisodeListWidget(
    title: String,
    content: @Composable () -> Unit
) {
    var expandable by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { expandable = expandable.not() }
            ) {
                Text(title)
            }
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .clickable(onClick = { expandable = expandable.not() })
                    .padding(8.dp),
                painter = painterResource(
                    if (expandable) R.drawable.arrow_down_float
                    else R.drawable.arrow_up_float
                ),
                contentDescription = null,
            )
        }
        if (expandable) content()
    }
}

@Preview(
    widthDp = 360,
    heightDp = 600,
    showBackground = true
)
@Composable
fun ExpandableEpisodeListWidgetPreview() = ExpandableEpisodeListWidget(
    title = "Episode 1",
    content = { Text("Episode description") }
)

