package com.testproject.pokemonapp.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.testproject.pokemonapp.R

/**
 * @author Jemmy
 * Created on 30/10/2023 at 14:27.
 */
sealed class ScreenNav(
    @StringRes val title: Int,
    @DrawableRes val icon: Int = -1,
) {
    object HOME : ScreenNav(R.string.title_home, R.drawable.ic_home)
    object HISTORY : ScreenNav(R.string.title_history, R.drawable.ic_clock)
    object DETAIL_POKEMON : ScreenNav(R.string.title_detail_pokemon, R.drawable.ic_clock)
}
