package com.testproject.pokemonlist.ui.pokemondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testproject.model.Pokemon
import com.testproject.model.PokemonResponseModel
import com.testproject.pokemonapp.core.Resource
import com.testproject.core.Event
import com.testproject.pokemonusecase.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {

    private val _loadPokemon = MutableLiveData<Resource<Pokemon>>()
    val loadPokemon: LiveData<Resource<Pokemon>> get() = _loadPokemon

    private val _event = MutableLiveData<Event<DetailPokemonEvent>>()
    val event: LiveData<Event<DetailPokemonEvent>> get() = _event

    fun getDetailPokemon(pokemonId: String) {
        viewModelScope.launch {
            _loadPokemon.value = repository.fetchPokemonById(pokemonId)
        }
    }

    fun cathPokemon(pokemon: PokemonResponseModel) {
        viewModelScope.launch {
            repository.insertPokemon(pokemon)
            _event.value = Event(DetailPokemonEvent.OnSuccessInsertPokemon)
        }
    }

    fun renamePokemon(pokemon: PokemonResponseModel) {
        viewModelScope.launch {
            repository.insertPokemon(pokemon)
            _event.value = Event(DetailPokemonEvent.OnSuccessRenamePokemon(pokemon.name))
        }
    }

    fun releasePokemon(pokemon: PokemonResponseModel) {
        viewModelScope.launch {
            repository.deletePokemon(pokemon.id)
            _event.value = Event(DetailPokemonEvent.OnSuccessReleasePokemon)
        }
    }
}
