package com.testproject.pokemonlist.ui.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testproject.model.PokemonResponseModel
import com.testproject.pokemonapp.core.Resource
import com.testproject.core.Event
import com.testproject.pokemonusecase.PokemonRepository
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
                else -> Unit
            }
        }
    }
}
