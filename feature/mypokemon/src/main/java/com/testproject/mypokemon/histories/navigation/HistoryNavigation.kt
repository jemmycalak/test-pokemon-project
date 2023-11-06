package com.testproject.mypokemon.histories.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.testproject.mypokemon.histories.history.MyPokemon

/**
 * @author Jemmy
 * Created on 31/10/2023 at 16:36.
 */

const val HISTORYPOKEMON_ROUTE = "historypokemon_route"
fun NavGraphBuilder.historyPokemonListNavGraph(
    onPokeminItemClicked: (Int) -> Unit = {},
) {
    composable(route = HISTORYPOKEMON_ROUTE) {
        MyPokemon(
            onPokeminItemClicked = onPokeminItemClicked,
        )
    }
}