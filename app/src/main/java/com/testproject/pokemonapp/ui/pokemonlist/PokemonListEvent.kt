package com.testproject.pokemonapp.ui.pokemonlist

/**
 * @author Jemmy
 * Created on 11/10/2023 at 11:32.
 */
sealed class PokemonListEvent {
    object OnNetworkError : PokemonListEvent()
}
