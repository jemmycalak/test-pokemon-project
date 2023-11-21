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
    data class OnSuccessCatchPokemon(
        val data: Pokemon?,
    ) : DetailPokemonEvent()

    data class OnSuccessRenamePokemon(
        val data: Pokemon?,
    ) : DetailPokemonEvent()
    object OnSuccessReleasePokemon : DetailPokemonEvent()
}
