package com.testproject.pokemonapp.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.testproject.mypokemon.histories.navigation.HISTORYPOKEMON_ROUTE
import com.testproject.pokemonapp.R
import com.testproject.pokemonlist.ui.navigation.DETAILPOKEMON_ROUTE
import com.testproject.pokemonlist.ui.navigation.POKEMONLIST_ROUTE

/**
 * @author Jemmy
 * Created on 30/10/2023 at 14:27.
 */
sealed class ScreenNav(
    val route: String,
    @StringRes val title: Int = -1,
    @DrawableRes val icon: Int = -1,
) {
    object HOME : ScreenNav(POKEMONLIST_ROUTE, R.string.title_home, R.drawable.ic_home)
    object HISTORY : ScreenNav(HISTORYPOKEMON_ROUTE, R.string.title_history, R.drawable.ic_clock)
    object DETAIL_POKEMON : ScreenNav(DETAILPOKEMON_ROUTE)
}
