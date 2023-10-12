package com.testproject.pokemonapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * @author Jemmy
 * Created on 11/10/2023 at 22:00.
 */

fun View.showSnackbar(message: String) {
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG,
    ).show()
}
