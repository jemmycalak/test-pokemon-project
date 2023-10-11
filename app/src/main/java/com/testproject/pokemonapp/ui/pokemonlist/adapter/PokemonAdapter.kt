package com.testproject.pokemonapp.ui.pokemonlist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.testproject.pokemonapp.data.model.PokemonResponseModel

/**
 * @author Jemmy
 * Created on 10/10/2023 at 15:13.
 */
class PokemonAdapter(
    private val listener: PokemonListener,
) : ListAdapter<PokemonResponseModel, PokemonViewHolder>(COMPARATOR) {

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<PokemonResponseModel>() {
            override fun areItemsTheSame(
                oldItem: PokemonResponseModel,
                newItem: PokemonResponseModel,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PokemonResponseModel,
                newItem: PokemonResponseModel,
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
