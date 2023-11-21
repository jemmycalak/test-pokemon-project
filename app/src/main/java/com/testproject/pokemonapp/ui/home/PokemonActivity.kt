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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.testproject.core.theme.PokemonAppTheme
import com.testproject.mypokemon.histories.navigation.historyPokemonListNavGraph
import com.testproject.pokemonapp.component.BottomBar
import com.testproject.pokemonlist.ui.navigation.detailPokemonNavGraph
import com.testproject.pokemonlist.ui.navigation.navigateToDetailPokemon
import com.testproject.pokemonlist.ui.navigation.navigateToPokemonList
import com.testproject.pokemonlist.ui.navigation.pokemonListNavGraph
import com.testproject.splash.navigation.SPLASH_ROUTE
import com.testproject.splash.navigation.splashNavGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val activity = (LocalContext.current as Activity)
    Scaffold(
        bottomBar = {
            BottomBar(navController = rememberNavCompose)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { paddingValues ->
        NavHostPokemon(
            modifier = Modifier.padding(paddingValues),
            navController = rememberNavCompose,
            showSnackBar = { message, action ->
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = action,
                        duration = SnackbarDuration.Short,
                    )
                }
            },
        )
    }
}

@Composable
fun NavHostPokemon(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    showSnackBar: (String, String?) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = SPLASH_ROUTE,
        modifier,
    ) {
        splashNavGraph(
            onNavigateToHome = navController::navigateToPokemonList,
        )
        pokemonListNavGraph(
            onPokeminItemClicked = navController::navigateToDetailPokemon,
            showSnackbar = showSnackBar,
        )
        historyPokemonListNavGraph(
            onPokeminItemClicked = navController::navigateToDetailPokemon,
        )
        detailPokemonNavGraph(
            onBack = { navController.popBackStack() },
            showSnackBar = showSnackBar
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    PokemonAppTheme {
        PokemonMain()
    }
}
