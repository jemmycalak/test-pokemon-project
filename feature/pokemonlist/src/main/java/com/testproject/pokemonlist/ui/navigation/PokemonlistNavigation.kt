package com.testproject.pokemonlist.ui.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.testproject.core.extention.getIdFromUrl
import com.testproject.model.PokemonResponseModel
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
const val POKEMONDATA_ARGS = "pokemon_data"
const val ISCATCHED_ARGS = "isCatched"
private const val DEEP_LINK_URI_PATTERN =
    "https://www.com.testproject.pokemonlist/DETAILPOKEMON_ROUTE/{$POKEMONID_ARGS}"

fun NavGraphBuilder.pokemonListNavGraph(
    onPokeminItemClicked: (PokemonResponseModel) -> Unit,
    showSnackbar: (String, String?) -> Unit,
) {
    composable(route = POKEMONLIST_ROUTE) {
        PokemonListScreen(
            onPokeminItemClicked = onPokeminItemClicked,
            showActionBar = showSnackbar,
        )
    }
}

fun NavGraphBuilder.detailPokemonNavGraph(
    onBack: () -> Unit,
    showSnackBar: (String, String?) -> Unit,
) {
    composable(
        route = "$DETAILPOKEMON_ROUTE/{$POKEMONID_ARGS}",
        arguments = listOf(
            navArgument(POKEMONID_ARGS) {
                type = NavType.StringType
                defaultValue = "-1"
            },
        ),
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        DetailPokemonScreen(
            navigateBack = onBack,
            showSnackBar = showSnackBar,
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

fun NavController.navigateToDetailPokemon(
    pokemon: PokemonResponseModel,
    isCatched: Boolean = false
) {
    graph.findNode("$DETAILPOKEMON_ROUTE/{$POKEMONID_ARGS}")?.id?.let { destinationId ->
        navigate(
            destinationId, bundleOf(
                POKEMONDATA_ARGS to pokemon.copy(id = pokemon.url.getIdFromUrl().toInt()),
                ISCATCHED_ARGS to isCatched
            )
        )
    }
}
