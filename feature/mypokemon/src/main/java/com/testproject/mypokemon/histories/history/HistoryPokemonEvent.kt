package com.testproject.mypokemon.histories.history

import com.testproject.model.PokemonResponseModel

/**
 * @author Jemmy
 * Created on 11/10/2023 at 11:32.
 */
sealed class HistoryPokemonEvent {
    object OnEmptyData : HistoryPokemonEvent()
    data class OnSuccess(
        val data: List<PokemonResponseModel>,
    ) : HistoryPokemonEvent()
}
