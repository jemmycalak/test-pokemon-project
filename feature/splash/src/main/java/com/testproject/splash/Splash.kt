package com.testproject.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.testproject.core.theme.PokemonAppTheme
import kotlinx.coroutines.delay

/**
 * @author Jemmy
 * Created on 01/11/2023 at 15:37.
 */

@Composable
internal fun SplashComponet(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {},
) {
    LaunchedEffect(true) {
        delay(3000)
        onNavigateToHome()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = com.testproject.core.R.drawable.pokemon_ball),
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
