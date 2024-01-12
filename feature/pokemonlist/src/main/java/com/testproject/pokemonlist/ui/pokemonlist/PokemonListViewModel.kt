package com.testproject.pokemonlist.ui.pokemonlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testproject.model.PokemonResponseModel
import com.testproject.pokemonapp.core.Resource
import com.testproject.pokemonusecase.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {

    private val _event = MutableStateFlow<PokemonListEvent>(PokemonListEvent.OnLoading)
    private val event: StateFlow<PokemonListEvent> = _event

    private val offset = MutableStateFlow(0)
    private val count = MutableStateFlow(0)

    private val _keyword = mutableStateOf("")
    val keyword: State<String> get() = _keyword

    private val pokemonList = MutableStateFlow(emptyList<PokemonResponseModel>())

    val pokemonData = combine(event) { data ->
        data[0]
    }

    fun searchPokemon(query: String) {
        _keyword.value = query
        pokemonList.value = emptyList()
        offset.value = 0
        getPokemonList(query)
    }

    fun getPokemonList(
        query: String = "",
        limit: Int = 10,
    ) {
        viewModelScope.launch {
            repository.getPokemonList(offset.value, limit).collect {
                when (it) {
                    is Resource.Success -> {
                        count.value = it.data.count
                        pokemonList.value = pokemonList.value + it.data.results

                        _event.emit(PokemonListEvent.OnSuccess(pokemonList.value))
                    }

                    is Resource.Failure -> {
                        _event.emit(PokemonListEvent.OnNetworkError)
                    }

                    else -> Unit
                }
            }
        }
    }

    fun onLoadMore() {
        val isLoadmoreAvailable = pokemonList.value.size < count.value
        if (isLoadmoreAvailable) {
            offset.value = offset.value + 10
            getPokemonList()
        }
    }

    fun onRefreshing() {
        viewModelScope.launch {
            pokemonList.value = emptyList()
            offset.value = 0

            _event.emit(PokemonListEvent.OnSuccess(pokemonList.value))
            getPokemonList()
        }
    }
}
