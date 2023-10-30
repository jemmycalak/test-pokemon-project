package com.testproject.pokemonapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.testproject.core.theme.PokemonAppTheme
import com.testproject.pokemonapp.R

/**
 * @author Jemmy
 * Created on 27/10/2023 at 14:05.
 */

@Composable
fun SplashComponet(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.pokemon_ball),
            contentDescription = "pokemon_ball",
            modifier = modifier.width(150.dp).height(150.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashComponenPreview() {
    PokemonAppTheme {
        SplashComponet()
    }
}
