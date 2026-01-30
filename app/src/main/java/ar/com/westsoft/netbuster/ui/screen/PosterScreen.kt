package ar.com.westsoft.netbuster.ui.screen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import ar.com.westsoft.netbuster.data.type.Poster
import ar.com.westsoft.netbuster.ui.widget.ExpandableEpisodeListWidget
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView

@Composable
fun PosterScreen(
    poster: Poster,
    imageLoader: ImageLoader,
    onEpisodeClick: (seasonId: Int, episodeId: Int) -> Unit,
) {
    val schedule = poster.series?.schedule
    val scheduleText = (schedule?.time ?: "") +
            if  (schedule?.days?.isNotEmpty() ?: false)
                schedule.days.joinToString(" - ", " (", ")")
            else ""

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(state = scrollState).padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            factory = { context ->
                NetworkImageView(context).apply {
                    this.setDefaultImageResId(android.R.drawable.ic_menu_gallery)
                }
            },
            modifier = Modifier.fillMaxWidth(0.7f).aspectRatio(1f).padding(8.dp),
            update = { view -> view.setImageUrl(poster.series?.imageUrl ?: "", imageLoader) }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = poster.series?.title ?: "",
                fontSize = 22.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 32.dp, top = 8.dp, bottom = 8.dp)
            )
            if (scheduleText.isBlank().not()) {
                Text(
                    text = "Schedule: $scheduleText",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 48.dp, top = 8.dp, bottom = 8.dp)
                )
            }
            if (poster.series?.genres.isNullOrEmpty().not()) {
                Text(
                    text = poster.series.genres.joinToString(" "),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 48.dp, top = 8.dp)
                )
            }
        }
        ExpandableEpisodeListWidget(
            title = "Seasons"
        ) {
            poster.seasonTree?.seasonMap?.forEach { (seasonId, seasonElement) ->
                ExpandableEpisodeListWidget(
                    title = "Season $seasonId"
                ) {
                    seasonElement.episodesMap.forEach { (_, episode) ->
                        Text(
                            text = (episode.seasonId + 1).toString().padStart(2, '0') + "x" +
                                    (episode.episodeId + 1).toString().padStart(2, '0') + " - " +
                                    episode.name,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(start = 48.dp, top = 8.dp, bottom = 8.dp)
                                .clickable(
                                    onClick = {
                                        onEpisodeClick(episode.seasonId, episode.episodeId)
                                    }),
                        )
                    }
                }
            }
        }
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .heightIn(min = 100.dp)
                .padding(top = 8.dp, bottom = 16.dp),
            update = { webView ->
                webView.loadData(poster.series?.summaryHtml ?: "", "text/html", "UTF-8")
            }
        )
    }
}
