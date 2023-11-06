package com.testproject.pokemonlist.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.testproject.pokemonlist.ui.pokemondetail.DetailPokemonScreen
import com.testproject.pokemonlist.ui.pokemonlist.PokemonListScreen

/**
 * @author Jemmy
 * Created on 31/10/2023 at 16:22.
 */

const val POKEMONLIST_ROUTE = "pokemonlist_route"
const val DETAILPOKEMON_ROUTE = "detailpokemon_route"
const val POKEMONID_ARGS = "pokemon_id"
const val POKEMONNAME_ARGS = "pokemon_name"
const val POKEMONURL_ARGS = "pokemon_url"
private const val DEEP_LINK_URI_PATTERN =
    "https://www.com.testproject.pokemonlist/DETAILPOKEMON_ROUTE/{$POKEMONID_ARGS}"

fun NavGraphBuilder.pokemonListNavGraph(
    onPokeminItemClicked: (Int) -> Unit = {},
    showActionBar: (String, String?) -> Unit,
) {
    composable(route = POKEMONLIST_ROUTE) {
        PokemonListScreen(
            onPokeminItemClicked = onPokeminItemClicked,
            showActionBar = showActionBar,
        )
    }
}

fun NavGraphBuilder.detailPokemonNavGraph(
    onBack: () -> Unit,
) {
    composable(
        route = "$DETAILPOKEMON_ROUTE/{$POKEMONID_ARGS}",
        arguments = listOf(
            navArgument(POKEMONID_ARGS) {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument(POKEMONNAME_ARGS) {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument(POKEMONURL_ARGS) {
                type = NavType.StringType
                defaultValue = ""
            },
        ),
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        DetailPokemonScreen(
            navigateBack = onBack,
        )
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
