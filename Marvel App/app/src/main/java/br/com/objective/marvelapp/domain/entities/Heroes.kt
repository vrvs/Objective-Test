package br.com.objective.marvelapp.domain.entities

import java.io.Serializable


class Heroes(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Hero>
): Serializable {
    companion object {}
}

class Hero(
    val name: String,
    val thumbnail: Image
): Serializable {
    companion object {}
}

class Image(
    val path: String,
    val extension: String
): Serializable {
    companion object {}
}
