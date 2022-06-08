package com.rrvieira.trendyt.ui

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

object TrendytDestinations {
    val HOME_ROUTE = NavHomeRoute("home")
    val MOVIE_ROUTE =
        NavMovieRoute("movie/{id}", navArgument("id") { type = NavType.IntType })
}

sealed class NavRoute(val route: String, val arguments: List<NamedNavArgument>) {
    protected fun <T> routeWithPathArgumentValue(navArgument: NamedNavArgument, value: T): String {
        return route.replace("{${navArgument.name}}", value.toString())
    }
}

class NavHomeRoute(route: String) : NavRoute(route, emptyList())
class NavMovieRoute(route: String, val id: NamedNavArgument) : NavRoute(route, listOf(id)) {
    fun build(movieId: Int) = routeWithPathArgumentValue(id, movieId)
}
