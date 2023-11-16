package com.testproject.pokemonlist.ui.renamepokemon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testproject.core.theme.PokemonAppTheme
import com.testproject.model.Pokemon
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowRenameDialog(
    modifier: Modifier = Modifier,
    pokemon: Pokemon? = null,
    onRenamePokemon: ((Pokemon) -> Unit?)? = null,
    isShowBottomSheet: Boolean = false,
    onDismissDialog: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }
    text = pokemon?.name.orEmpty()

    if (isShowBottomSheet) {
        ModalBottomSheet(onDismissRequest = onDismissDialog) {
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = modifier,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp,
                    text = "Rename Pokemon",
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = modifier.padding(10.dp))
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    singleLine = true,
                    label = { Text(text = "Name") },
                    placeholder = { Text(text = "Type your pokemon name here..") },
                )
                Spacer(modifier = modifier.padding(10.dp))
                Row(modifier.fillMaxWidth()) {
                    Spacer(modifier = modifier.padding(2.dp))
                    Button(
                        modifier = modifier
                            .weight(1f)
                            .alpha(0.8F),
                        onClick = {
                            scope.launch {
                                sheetState.hide()
                            }
                            onDismissDialog()
                        }) {
                        Text(text = "Cancel")
                    }

                    Spacer(modifier = modifier.padding(5.dp))
                    Button(
                        modifier = modifier.weight(1f),
                        onClick = {
                            scope.launch {
                                sheetState.hide()
                            }.invokeOnCompletion {
                                onRenamePokemon?.let { onSave ->
                                    pokemon?.let {
                                        onSave(
                                            it.copy(
                                                name = text
                                            )
                                        )
                                    }
                                }
                            }
                            onDismissDialog()
                        }) {
                        Text(text = "Save")
                    }
                    Spacer(modifier = modifier.padding(2.dp))
                }

                Spacer(modifier = modifier.padding(bottom = 20.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreShowRenameDialog() {
    PokemonAppTheme {
        ShowRenameDialog(onDismissDialog = {})
    }
}