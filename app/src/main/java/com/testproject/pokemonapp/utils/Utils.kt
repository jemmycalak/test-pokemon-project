package com.testproject.pokemonapp

// Generated random ID from 1 to 100 included
fun String.getIdFromUrl(): String {
    return split("/").dropLast(1).last()
}
