package com.testproject.core.extention

/**
 * @author Jemmy
 * Created on 03/11/2023 at 13:40.
 */
fun String.getImageUrl(): String {
    val IMAGE_HOST_URL =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
    return try {
        val id = getIdFromUrl()
        "$IMAGE_HOST_URL/$id.png"
    } catch (e: Exception) {
        ""
    }
}

fun String.getIdFromUrl(): String {
    return split("/").dropLast(1).last()
}