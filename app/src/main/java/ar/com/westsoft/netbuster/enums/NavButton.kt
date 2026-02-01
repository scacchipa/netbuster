package ar.com.westsoft.netbuster.enums

import androidx.annotation.DrawableRes

enum class NavButton(val label: String, @param:DrawableRes val iconId: Int, val route: Route) {
    SEARCH("Search", android.R.drawable.ic_menu_search, Route.SEARCH),
    FAVORITE("Favorite", android.R.drawable.btn_star_big_off, Route.FAVORITE),
    SETTINGS("Settings", android.R.drawable.ic_menu_preferences, Route.SETTINGS),
}