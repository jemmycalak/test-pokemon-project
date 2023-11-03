package com.testproject.pokemonlist.ui.pokemonlist

import com.testproject.model.PokemonResponseModel

/**
 * @author Jemmy
 * Created on 11/10/2023 at 11:32.
 */
sealed class PokemonListEvent {
    data class OnSuccess(
        val data: List<PokemonResponseModel>,
    ) : PokemonListEvent()

    object OnLoading : PokemonListEvent()

    object OnNetworkError : PokemonListEvent()
}
