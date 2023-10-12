package com.testproject.pokemonlist.ui.pokemonlist.adapter

import com.testproject.model.PokemonResponseModel

/**
 * @author Jemmy
 * Created on 10/10/2023 at 16:30.
 */
interface PokemonListener {
    fun onPokemonClicked(data: PokemonResponseModel)
}