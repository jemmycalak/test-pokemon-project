package com.testproject.pokemonlist.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.testproject.pokemonlist.ui.pokemondetail.DetailPokemon
import com.testproject.pokemonlist.ui.pokemonlist.PokemonListScreen

/**
 * @author Jemmy
 * Created on 31/10/2023 at 16:22.
 */

const val POKEMONLIST_ROUTE = "pokemonlist_route"
const val DETAILPOKEMON_ROUTE = "detailpokemon_route"
const val POKEMONID_ARGS = "pokemon_id"
private const val DEEP_LINK_URI_PATTERN =
    "https://www.com.testproject.pokemonlist/DETAILPOKEMON_ROUTE/{$POKEMONID_ARGS}"

fun NavGraphBuilder.pokemonListNavGraph(
    controller: NavHostController,
    showActionBar: (String, String?) -> Unit,
) {
    composable(route = POKEMONLIST_ROUTE) {
        PokemonListScreen(
            onPokeminItemClicked = controller::navigateToDetailPokemon,
            showActionBar = showActionBar,
        )
    }
}

fun NavGraphBuilder.detailPokemonNavGraph(controller: NavHostController) {
    composable(
        route = "$DETAILPOKEMON_ROUTE/{$POKEMONID_ARGS}",
        arguments = listOf(
            navArgument(POKEMONID_ARGS) {
                type = NavType.IntType
                defaultValue = -1
            },
        ),
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        DetailPokemon()
    }
}

fun NavController.navigateToPokemonList(fromPage: String) {
    navigate(POKEMONLIST_ROUTE) {
        popUpTo(fromPage) {
            inclusive = true
            saveState = false
        }
    }
}
fun NavController.navigateToDetailPokemon(pokemonId: Int) {
    navigate("$DETAILPOKEMON_ROUTE/$pokemonId") {
        launchSingleTop = true
    }
}
