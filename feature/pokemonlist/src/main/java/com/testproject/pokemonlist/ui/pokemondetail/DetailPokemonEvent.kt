package com.testproject.pokemonlist.ui.pokemondetail

import com.testproject.model.Pokemon

/**
 * @author Jemmy
 * Created on 11/10/2023 at 11:32.
 */
sealed class DetailPokemonEvent {
    data class OnShowDetailPokemon(
        val data: Pokemon,
    ) : DetailPokemonEvent()
    object OnShowLoading : DetailPokemonEvent()
    object OnNetworkError : DetailPokemonEvent()

    object OnSuccessInsertPokemon : DetailPokemonEvent()
    data class OnSuccessRenamePokemon(val name: String) : DetailPokemonEvent()
    object OnSuccessReleasePokemon : DetailPokemonEvent()
}
