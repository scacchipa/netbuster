package ar.com.westsoft.netbuster.usecase

import ar.com.westsoft.netbuster.data.source.TvAPIClient
import com.android.volley.toolbox.ImageLoader
import javax.inject.Inject

class GetImageLoaderUseCase @Inject constructor(
    private val tvAPIClient: TvAPIClient,
) {
    operator fun invoke(): ImageLoader = tvAPIClient.imageLoader
}