package ar.com.westsoft.netbuster.data.type

data class FavSeries(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val schedule: Schedule,
    val genres: List<String>,
    val summaryHtml: String,
    val isFavorite: Boolean,
) {
    fun toSeries(): Series = Series(
        id = id,
        title = title,
        imageUrl = imageUrl,
        schedule = schedule,
        genres = genres,
        summaryHtml = summaryHtml,
    )
}