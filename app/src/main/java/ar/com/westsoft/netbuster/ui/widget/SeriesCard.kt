package ar.com.westsoft.netbuster.ui.widget

import android.R
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView

@Composable
fun SeriesCard(
    title: String,
    imageUrl: String?,
    imageLoader: ImageLoader,
    isFavorite: Boolean,
    onCardClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        onClick = onCardClick // Hace que toda la tarjeta sea clickable
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            AndroidView(
                factory = { context ->
                    NetworkImageView(context).apply {
                        scaleType = ImageView.ScaleType.FIT_CENTER
                        adjustViewBounds = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.75f)
                    .wrapContentHeight(),
                update = { view ->
                    view.setImageUrl(imageUrl, imageLoader)
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                )
                Image(
                    painter = painterResource(
                        id = if (isFavorite) R.drawable.btn_star_big_on
                            else R.drawable.btn_star_big_off
                    ),
                    contentDescription = "Favorite star",
                    modifier = Modifier.size(30.dp).clickable(onClick = onFavoriteClick),
                )
            }
        }
    }
}

