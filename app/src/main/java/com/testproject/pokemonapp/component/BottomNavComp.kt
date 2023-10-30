package com.testproject.pokemonapp.component

import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.testproject.pokemonapp.ui.home.ScreenNav

/**
 * @author Jemmy
 * Created on 30/10/2023 at 13:11.
 */

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val items = listOf(
        ScreenNav.HOME,
        ScreenNav.HISTORY,
    )

    NavigationBar(modifier = modifier, containerColor = Color.White) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            val title = stringResource(id = screen.title)
            val icon = painterResource(id = screen.icon)
            val isSelected = currentDestination?.route == title
            NavigationBarItem(
                icon = {
                    Image(
                        painter = icon,
                        contentDescription = title,
                    )
                },
                label = { Text(stringResource(id = screen.title)) },
                selected = isSelected,
                onClick = {
                    navController.navigate(title) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
            )
        }
    }
}
