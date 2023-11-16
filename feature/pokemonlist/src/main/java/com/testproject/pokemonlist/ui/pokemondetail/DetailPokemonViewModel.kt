package com.testproject.pokemonlist.ui.pokemondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testproject.core.extention.getIdFromUrl
import com.testproject.model.Pokemon
import com.testproject.model.PokemonResponseModel
import com.testproject.pokemonapp.core.Resource
import com.testproject.pokemonlist.ui.navigation.ISCATCHED_ARGS
import com.testproject.pokemonlist.ui.navigation.POKEMONDATA_ARGS
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
    val pokemon: PokemonResponseModel = checkNotNull(savedStateHandle[POKEMONDATA_ARGS])
    val isCatched: Boolean = checkNotNull(savedStateHandle[ISCATCHED_ARGS])

    private val _pokemonDetail = MutableStateFlow<Pokemon?>(null)
    private val _event = MutableStateFlow<DetailPokemonEvent?>(null)
    val event: StateFlow<DetailPokemonEvent?> get() = _event

    fun getDetailPokemon() {
        viewModelScope.launch {
            val pokemonId = pokemon.url.getIdFromUrl()
            when (val resource = repository.fetchPokemonById(pokemonId)) {
                is Resource.Failure -> _event.value = DetailPokemonEvent.OnNetworkError
                Resource.Loading -> _event.value = DetailPokemonEvent.OnShowLoading
                is Resource.Success -> {
                    _event.value = DetailPokemonEvent.OnShowDetailPokemon(resource.data.copy(
                        name = pokemon.name
                    ))
                    _pokemonDetail.value = resource.data
                }
            }
        }
    }

    fun cathPokemon() {
        pokemon.let { value ->
            viewModelScope.launch {
                repository.insertPokemon(value)
                _event.value = DetailPokemonEvent.OnSuccessCatchPokemon(_pokemonDetail.value)
            }
        }
    }

    fun renamePokemon(newName: String = "") {
        pokemon.let { value ->
            viewModelScope.launch {
                val newPokemon = value.copy(
                    name = newName,
                )
                repository.upsertPokemon(newPokemon)
                _event.value = DetailPokemonEvent.OnSuccessRenamePokemon(_pokemonDetail.value?.copy(
                    name = newName
                ))
            }
        }
    }

    fun releasePokemon() {
        pokemon.let { value ->
            viewModelScope.launch {
                repository.deletePokemon(value.id)
                _event.value = DetailPokemonEvent.OnSuccessReleasePokemon
            }
        }
    }
}
