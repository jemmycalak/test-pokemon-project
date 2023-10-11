package com.testproject.pokemonapp.ui.pokemondetail

/**
 * @author Jemmy
 * Created on 11/10/2023 at 11:32.
 */
sealed class DetailPokemonEvent {
    object OnSuccessInsertPokemon : DetailPokemonEvent()
    data class OnSuccessRenamePokemon(val name: String) : DetailPokemonEvent()
    object OnSuccessReleasePokemon : DetailPokemonEvent()
}
