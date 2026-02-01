package ar.com.westsoft.netbuster.ui.screen

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.net.toUri
import ar.com.westsoft.netbuster.data.type.Episode
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView

@Composable
fun EpisodeDetailScreen(
    episode: Episode,
    imageLoader: ImageLoader,
    onBackClick: () -> Unit,
    onGoToPage: (Uri) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (txtSeries, txtEpisode, txtSeason, txtEpNumber,
            viewSummary, imgEpisode, btnGo, btnBack) = createRefs()
        val guidelineTop = createGuidelineFromTop(0.45f)
        val guidelineVertical = createGuidelineFromStart(0.6f)
        Text(
            text = episode.seriesTitle,
            modifier = Modifier.constrainAs(txtSeries) {
                bottom.linkTo(txtEpisode.top, margin = 8.dp)
                start.linkTo(txtEpisode.start)
            }
        )
        Text(
            text = episode.name,
            fontSize = 22.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(txtEpisode) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 24.dp)
                end.linkTo(guidelineVertical, margin = 8.dp)
                bottom.linkTo(guidelineTop)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = "Season: ${episode.seasonId}",
            modifier = Modifier.constrainAs(txtSeason) {
                top.linkTo(txtEpisode.bottom, margin = 8.dp)
                start.linkTo(txtEpisode.start, margin = 16.dp)
                bottom.linkTo(txtEpNumber.top, margin = 8.dp)
            }
        )
        Text(
            text = "Episode: ${episode.episodeId}",
            modifier = Modifier.constrainAs(txtEpNumber) {
                top.linkTo(txtSeason.bottom, margin = 16.dp)
                start.linkTo(txtEpisode.start, margin = 16.dp)
                bottom.linkTo(guidelineTop, margin = 16.dp)
            }
        )
        AndroidView(
            factory = { context ->
                NetworkImageView(context).apply {
                    setBackgroundColor(android.graphics.Color.WHITE)
                }
            },
            modifier = Modifier.constrainAs(imgEpisode) {
                top.linkTo(parent.top, margin = 32.dp)
                start.linkTo(guidelineVertical)
                end.linkTo(parent.end, margin = 16.dp)
                bottom.linkTo(viewSummary.top, margin = 24.dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            update = { view ->
                view.setImageUrl(episode.imageUrl, imageLoader)
            }
        )
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                }
            },
            modifier = Modifier.constrainAs(viewSummary) {
                top.linkTo(guidelineTop, margin = 16.dp)
                start.linkTo(parent.start, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
                bottom.linkTo(btnGo.top, margin = 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.percent(0.2f)
            },
            update = { webView ->
                webView.loadData(episode.summaryHtml, "text/html", "UTF-8")
            }
        )
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .constrainAs(btnBack) {
                    top.linkTo(btnGo.top)
                    bottom.linkTo(btnGo.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(btnGo.start)
                    width = Dimension.ratio("1:1")
                    height = Dimension.fillToConstraints
                }
        ) {
            Icon(
                painter = painterResource(android.R.drawable.ic_menu_revert),
                contentDescription = "Back",
                tint = Color.White
            )
        }
        Button(
            onClick = { onGoToPage(episode.url.toUri()) },
            modifier = Modifier.constrainAs(btnGo) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(viewSummary.bottom)
            }
        ) {
            Text("Go to Page", fontSize = 16.sp)
        }
    }
}