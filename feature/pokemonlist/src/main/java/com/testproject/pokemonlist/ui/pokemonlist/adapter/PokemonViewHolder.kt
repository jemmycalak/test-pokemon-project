package com.testproject.pokemonlist.ui.pokemonlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.testproject.model.PokemonResponseModel
import com.testproject.core.getIdFromUrl
import com.testproject.pokemonlist.R
import com.testproject.pokemonlist.databinding.ItemPokemonBinding

/**
 * @author Jemmy
 * Created on 10/10/2023 at 15:14.
 */
class PokemonViewHolder(
    private val binding: ItemPokemonBinding,
    private val listener: PokemonListener,
) : ViewHolder(binding.root) {

    private val animation: Animation by lazy {
        AnimationUtils.loadAnimation(
            binding.root.context,
            R.anim.anim_item,
        )
    }

    fun bind(data: PokemonResponseModel) {
        binding.data = data
        binding.root.animation = animation
        Glide
            .with(binding.root.context)
            .load(getImageUrl(data.url))
            .placeholder(R.drawable.pokemon_ball)
            .into(binding.image)
        binding.root.setOnClickListener { listener.onPokemonClicked(data) }
    }

    private fun getImageUrl(url: String): String {
        val IMAGE_HOST_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
        return try {
            val id = url.getIdFromUrl()
            "$IMAGE_HOST_URL/$id.png"
        } catch (e: Exception) {
            ""
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            listener: PokemonListener,
        ): PokemonViewHolder {
            return PokemonViewHolder(
                ItemPokemonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                listener,
            )
        }
    }
}
