package com.testproject.pokemonapp.ui.pokemonlist.adapter

import com.testproject.pokemonapp.data.model.PokemonResponseModel

/**
 * @author Jemmy
 * Created on 10/10/2023 at 16:30.
 */
interface PokemonListener {
    fun onPokemonClicked(data: PokemonResponseModel)
}