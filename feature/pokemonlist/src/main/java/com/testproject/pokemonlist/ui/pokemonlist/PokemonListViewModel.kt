package com.testproject.pokemonlist.ui.pokemonlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testproject.pokemonapp.core.Resource
import com.testproject.pokemonusecase.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {

    private val _event = MutableStateFlow<PokemonListEvent>(PokemonListEvent.OnLoading)
    private val event: StateFlow<PokemonListEvent> = _event

    val offset = MutableStateFlow(0)
    private val _keyword = mutableStateOf("")
    val keyword: State<String> get() = _keyword

    val pokemonData = combine(event) { data ->
        data[0]
    }

    fun searchPokemon(query: String) {
        _keyword.value = query
        getPokemonList(query, 0)
    }

    fun getPokemonList(
        query: String,
        offset: Int,
        limit: Int = 100,
    ) {
        viewModelScope.launch {
            repository.getPokemonList(offset, limit).collect {
                when (it) {
                    is Resource.Success -> {
                        _event.emit(PokemonListEvent.OnSuccess(it.data.results))
                    }

                    is Resource.Failure -> {
                        _event.emit(PokemonListEvent.OnNetworkError)
                    }

                    else -> Unit
                }
            }
        }
    }
}
