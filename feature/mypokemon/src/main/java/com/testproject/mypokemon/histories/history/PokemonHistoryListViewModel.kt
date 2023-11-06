package com.testproject.mypokemon.histories.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testproject.pokemonusecase.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonHistoryListViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {
    private val _event = MutableStateFlow<HistoryPokemonEvent>(HistoryPokemonEvent.OnEmptyData)
    val event: StateFlow<HistoryPokemonEvent> get() = _event

    fun getPokemonList() {
        viewModelScope.launch {
            val resource = repository.getPokemonListHistory()
            _event.value = HistoryPokemonEvent.OnSuccess(resource)
            if (resource.isEmpty()) {
                _event.value = HistoryPokemonEvent.OnEmptyData
            }
        }
    }
}
