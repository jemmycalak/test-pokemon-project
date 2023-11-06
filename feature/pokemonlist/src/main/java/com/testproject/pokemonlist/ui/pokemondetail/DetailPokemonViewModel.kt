package com.testproject.pokemonlist.ui.pokemondetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testproject.model.PokemonResponseModel
import com.testproject.pokemonapp.core.Resource
import com.testproject.pokemonlist.ui.navigation.POKEMONID_ARGS
import com.testproject.pokemonusecase.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val repository: PokemonRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _pokemon = mutableStateOf<PokemonResponseModel?>(null)
    val pokemon: MutableState<PokemonResponseModel?> get() = _pokemon

    private val pokemonId: Int = checkNotNull(savedStateHandle[POKEMONID_ARGS])
    private val _event = MutableStateFlow<DetailPokemonEvent?>(null)
    val event: StateFlow<DetailPokemonEvent?> get() = _event

    fun setPokemon(pokemon: PokemonResponseModel) {
        _pokemon.value = pokemon
    }

    fun getDetailPokemon() {
        viewModelScope.launch {
            when (val resource = repository.fetchPokemonById("$pokemonId")) {
                is Resource.Failure -> _event.value = DetailPokemonEvent.OnNetworkError
                Resource.Loading -> _event.value = DetailPokemonEvent.OnShowLoading
                is Resource.Success -> {
                    _event.value = DetailPokemonEvent.OnShowDetailPokemon(resource.data)
                }
            }
        }
    }

    fun cathPokemon() {
        pokemon.value?.let { value ->
            viewModelScope.launch {
                repository.insertPokemon(value)
                _event.value = DetailPokemonEvent.OnSuccessInsertPokemon
            }
        }
    }

    fun renamePokemon(newName: String = "") {
        pokemon.value?.let { value ->
            viewModelScope.launch {
                repository.insertPokemon(
                    value.copy(
                        name = newName,
                    ),
                )
                _event.value = DetailPokemonEvent.OnSuccessRenamePokemon(value.name)
            }
        }
    }

    fun releasePokemon() {
        pokemon.value?.let { value ->
            viewModelScope.launch {
                repository.deletePokemon(value.id)
                _event.value = DetailPokemonEvent.OnSuccessReleasePokemon
            }
        }
    }
}
