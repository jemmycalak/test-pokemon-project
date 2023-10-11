package com.testproject.pokemonapp.ui.histories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testproject.pokemonapp.data.model.PokemonResponseModel
import com.testproject.pokemonapp.data.repository.PokemonRepository
import com.testproject.pokemonapp.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonHistoryListViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {
    private val _listPokemon = MutableLiveData<List<PokemonResponseModel>>()
    val listPokemon: LiveData<List<PokemonResponseModel>> get() = _listPokemon

    private val _event = MutableLiveData<Event<HistoryPokemonEvent>>()
    val event: LiveData<Event<HistoryPokemonEvent>> get() = _event

    fun getPokemonList() {
        viewModelScope.launch {
            val resource = repository.getPokemonListHistory()
            _listPokemon.value = resource
            if (resource.isEmpty()) {
                _event.value = Event(HistoryPokemonEvent.OnEmptyData)
            }
        }
    }
}
