package br.com.objective.marvelapp.data.models

import br.com.objective.marvelapp.domain.entities.Hero
import br.com.objective.marvelapp.domain.entities.Heroes
import br.com.objective.marvelapp.domain.entities.Image
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class HeroesResponse(
    @Json(name="data")
    val data: DataResponse?
)

@JsonClass(generateAdapter = true)
class DataResponse(
    @Json(name="offset")
    val offset: Int?,

    @Json(name="limit")
    val limit: Int?,

    @Json(name="total")
    val total: Int?,

    @Json(name="count")
    val count: Int?,

    @Json(name="results")
    val results: List<HeroResponse>?
)

class HeroResponse(
    @Json(name="name")
    val name: String?,

    @Json(name="thumbnail")
    val thumbnail: ImageResponse?
)

class ImageResponse(
    @Json(name="path")
    val path: String?,

    @Json(name="extension")
    val extension: String?
)

fun Heroes.Companion.fromHeroesResponse(response: HeroesResponse): Heroes {
    try {
        val data = response.data!!
        return Heroes(
            data.offset!!,
            data.limit!!,
            data.total!!,
            data.count!!,
            data.results!!.map {
                Hero.fromHeroResponse(it)
            }
        )
    } catch (e: Exception) {
        // TODO: Handle exception
        throw e
    }
}

private fun Hero.Companion.fromHeroResponse(response: HeroResponse): Hero {
    return Hero(
        response.name!!,
        Image.fromImageResponse(response.thumbnail!!)
    )
}

private fun Image.Companion.fromImageResponse(response: ImageResponse): Image {
    return Image(
        response.path!!,
        response.extension!!
    )
}


