package com.testproject.pokemonapp.ui.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testproject.pokemonapp.core.Resource
import com.testproject.pokemonapp.data.model.PokemonResponseModel
import com.testproject.pokemonapp.data.repository.PokemonRepository
import com.testproject.pokemonapp.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {
    private val _listPokemon = MutableLiveData<List<PokemonResponseModel>>()
    val listPokemon: LiveData<List<PokemonResponseModel>> get() = _listPokemon

    private val _event = MutableLiveData<Event<PokemonListEvent>>()
    val event: LiveData<Event<PokemonListEvent>> get() = _event

    fun getPokemonList(offset: Int, limit: Int) {
        viewModelScope.launch {
            when (val resource = repository.getPokemonList(offset, limit)) {
                is Resource.Success -> {
                    _listPokemon.value = resource.data.results
                }
                is Resource.Loading -> Unit
                is Resource.Failure -> {
                    _event.value = Event(PokemonListEvent.OnNetworkError)
                }
            }
        }
    }
}
