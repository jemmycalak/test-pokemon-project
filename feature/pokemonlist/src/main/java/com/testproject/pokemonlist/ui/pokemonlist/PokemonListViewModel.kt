package com.testproject.pokemonlist.ui.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testproject.model.PokemonListResponseModel
import com.testproject.pokemonapp.core.Resource
import com.testproject.pokemonusecase.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {
    /*private val _listPokemon = MutableLiveData<Resource<PokemonListResponseModel>>()
    val listPokemon: LiveData<Resource<PokemonListResponseModel>> get() = _listPokemon

    private val _event = MutableLiveData<Event<PokemonListEvent>>()
    val event: LiveData<Event<PokemonListEvent>> get() = _event*/

    val pokemonData: SharedFlow<Resource<PokemonListResponseModel>> =
        repository.getPokemonList(1, 100).stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Resource.Loading(),
        )

    /*fun getPokemonList(offset: Int, limit: Int) {
        viewModelScope.launch {
            repository.getPokemonList(offset, limit).collect {
                when (it) {
                    is Resource.Success -> {
                        _listPokemon.value = it
                    }

                    is Resource.Loading -> Unit
                    is Resource.Failure -> {
                        _event.value = Event(PokemonListEvent.OnNetworkError)
                    }

                    else -> Unit
                }
            }
        }
    }*/
}
