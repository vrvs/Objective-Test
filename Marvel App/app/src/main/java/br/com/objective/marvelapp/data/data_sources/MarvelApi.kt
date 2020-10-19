package br.com.objective.marvelapp.data.data_sources

import br.com.objective.marvelapp.data.models.HeroesResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MarvelApi {
    @Headers("Accept: application/json")
    @GET("v1/public/characters")
    fun getHeroes(
        @Query("apikey") apikey: String,
        @Query("ts") ts: Long,
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Deferred<HeroesResponse>

    @Headers("Accept: application/json")
    @GET("v1/public/characters")
    fun getHeroes(
        @Query("apikey") apikey: String,
        @Query("ts") ts: Long,
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nameStartsWith") nameStartsWith: String
    ): Deferred<HeroesResponse>
}