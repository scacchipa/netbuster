package ar.com.westsoft.netbuster.core.activity

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ar.com.westsoft.netbuster.core.client.TvAPIClient
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView

@Composable
fun EpisodeDetailScreen(
    seriesTitle: String,
    episodeTitle: String,
    seasonNumber: String,
    episodeNumber: String,
    imageUrl: String?,
    imageLoader: ImageLoader,
    summaryHtml: String,
    onBackClick: () -> Unit,
    onGoToPageClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Definición de las referencias (IDs)
        val (txtSerie, txtEpisode, txtSeason, txtEpNumber,
            viewSummary, imgEpisode, btnGo, btnBack) = createRefs()

        // Guidelines
        val guidelineTop = createGuidelineFromTop(0.45f)
        val guidelineVertical = createGuidelineFromStart(0.6f)

        // 1. Título de la Serie
        Text(
            text = seriesTitle,
            modifier = Modifier.constrainAs(txtSerie) {
                bottom.linkTo(txtEpisode.top, margin = 8.dp)
                start.linkTo(txtEpisode.start)
            }
        )

        // 2. Título del Episodio
        Text(
            text = episodeTitle,
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

        // 3. Número de Temporada
        Text(
            text = seasonNumber,
            modifier = Modifier.constrainAs(txtSeason) {
                top.linkTo(txtEpisode.bottom, margin = 8.dp)
                start.linkTo(txtEpisode.start, margin = 16.dp)
                bottom.linkTo(txtEpNumber.top, margin = 8.dp)
            }
        )

        // 4. Número de Episodio
        Text(
            text = episodeNumber,
            modifier = Modifier.constrainAs(txtEpNumber) {
                top.linkTo(txtSeason.bottom, margin = 16.dp)
                start.linkTo(txtEpisode.start, margin = 16.dp)
                bottom.linkTo(guidelineTop, margin = 16.dp)
            }
        )

        // 5. Imagen del Episodio (Volley)
        AndroidView(
            factory = { context ->
                NetworkImageView(context).apply {
                    setBackgroundColor(android.graphics.Color.WHITE)
                    // Aquí asignarías tu ImageLoader de Volley
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
                view.setImageUrl(imageUrl, imageLoader)
            }
        )

        // 6. WebView (Summary)
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
                height = Dimension.percent(0.2f) // app:layout_constraintHeight_percent="0.2"
            },
            update = { webView ->
                webView.loadData(summaryHtml, "text/html", "UTF-8")
            }
        )

        // 7. Botón Go to Page
        Button(
            onClick = onGoToPageClick,
            modifier = Modifier.constrainAs(btnGo) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(viewSummary.bottom)
            }
        ) {
            Text("Go to Page", fontSize = 16.sp)
        }

        // 8. Botón Volver (Reemplaza ImageButton)
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
    }
}

@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true,
)
@Composable
fun EpisodeDetailPreview() = EpisodeDetailScreen(
    seriesTitle = "Series Title",
    episodeTitle = "Episode Title",
    seasonNumber = "Season 1",
    episodeNumber = "Episode 1",
    imageUrl = null,
    imageLoader = TvAPIClient.instance.imageLoader,
    summaryHtml = "",
    onBackClick = { } ,
    onGoToPageClick = { },
)