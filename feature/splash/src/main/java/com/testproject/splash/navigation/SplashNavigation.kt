package com.testproject.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.testproject.splash.SplashComponet

/**
 * @author Jemmy
 * Created on 01/11/2023 at 15:37.
 */

const val SPLASH_ROUTE = "splashpokemon_route"
fun NavGraphBuilder.splashNavGraph(onNavigateToHome: (String) -> Unit) {
    composable(route = SPLASH_ROUTE) {
        SplashComponet(onNavigateToHome = onNavigateToHome)
    }
}
