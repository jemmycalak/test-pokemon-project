package com.testproject.pokemonapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testproject.pokemonapp.data.local.dao.PokemonDAO
import com.testproject.pokemonapp.data.model.PokemonResponseModel

@Database(
    entities = [
        PokemonResponseModel::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class PokemonDB : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "pokemnon_db"
        private val LOCK = Any()
        private var sInstance: PokemonDB? = null

        @JvmStatic
        fun getInstance(context: Context): PokemonDB {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        PokemonDB::class.java,
                        DATABASE_NAME,
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return sInstance!!
        }
    }

    abstract fun pokemonDAO(): PokemonDAO
}
