package com.testproject.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.testproject.model.pokemon.sprites.Sprites
import com.testproject.model.pokemon.stats.Stats
import com.testproject.model.pokemon.types.Types
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("sprites") var sprites: Sprites? = Sprites(),
    @SerializedName("stats") var stats: ArrayList<Stats> = arrayListOf(),
    @SerializedName("types") var types: ArrayList<Types> = arrayListOf(),
) : Parcelable
