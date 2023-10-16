package com.testproject.mypokemon.histories.history

/**
 * @author Jemmy
 * Created on 11/10/2023 at 11:32.
 */
sealed class HistoryPokemonEvent {
    object OnEmptyData : HistoryPokemonEvent()
}
