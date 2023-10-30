package com.testproject.pokemonapp.ui.home

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.testproject.core.theme.PokemonAppTheme
import com.testproject.mypokemon.histories.history.MyPokemon
import com.testproject.pokemonapp.component.BottomBar
import com.testproject.pokemonlist.ui.pokemondetail.DetailPokemon
import com.testproject.pokemonlist.ui.pokemonlist.PokemonList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    PokemonMain()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonMain() {
    val rememberNavCompose = rememberNavController()
    val activity = (LocalContext.current as Activity)
    val home = stringResource(id = ScreenNav.HOME.title)
    val history = stringResource(id = ScreenNav.HISTORY.title)
    val detailPokemon = stringResource(id = ScreenNav.DETAIL_POKEMON.title)

    Scaffold(
        bottomBar = {
            BottomBar(navController = rememberNavCompose)
        },
    ) { paddingValues ->
        NavHost(
            navController = rememberNavCompose,
            startDestination = home,
            Modifier.padding(paddingValues),
        ) {
            composable(home) {
                PokemonList()
            }
            composable(history) {
                MyPokemon()
            }
            composable(detailPokemon) {
                DetailPokemon()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    PokemonAppTheme {
        PokemonMain()
    }
}
