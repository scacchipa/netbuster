package ar.com.westsoft.netbuster.data.source

import ar.com.westsoft.netbuster.data.type.Series
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VolatileSeriesStore @Inject constructor() {

    private val _seriesList = MutableStateFlow<List<Series>>(emptyList())
    val seriesList: StateFlow<List<Series>> = _seriesList

    suspend fun setSeriesList(seriesList: List<Series>) {
        _seriesList.emit(seriesList)
    }
}