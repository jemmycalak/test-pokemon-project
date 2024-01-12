package com.testproject.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author Jemmy
 * Created on 10/10/2023 at 15:28.
 */
@Entity(tableName = "pokemon_list")
@Parcelize
data class PokemonResponseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val url: String = "",
) : Parcelable
