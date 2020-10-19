package br.com.objective.marvelapp.data.repositories

import android.content.Context
import br.com.objective.marvelapp.R
import br.com.objective.marvelapp.data.data_sources.MarvelApi
import br.com.objective.marvelapp.data.models.fromHeroesResponse
import br.com.objective.marvelapp.domain.entities.Heroes
import br.com.objective.marvelapp.domain.repositories.HeroesRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.math.BigInteger
import java.security.MessageDigest

class HeroesRepositoryImpl(private val context: Context): HeroesRepository, KoinComponent {

    private val marvelApi: MarvelApi by inject()

    override suspend fun getHeroes(limit: Int, offset: Int): Heroes {
        val publicKey = context.getString(R.string.public_key)
        val privateKey = context.getString(R.string.private_key)
        val timeNow = System.currentTimeMillis()
        val message = timeNow.toString()+privateKey+publicKey
        val md = MessageDigest.getInstance("MD5")
        val messageDigest = md.digest(message.toByteArray())
        val no = BigInteger(1, messageDigest)
        var hashtext: String = no.toString(16)
        while (hashtext.length < 32) {
            hashtext = "0$hashtext"
        }
        val response = marvelApi.getHeroes(
            publicKey,
            timeNow,
            hashtext,
            limit,
            offset
        ).await()
        return Heroes.fromHeroesResponse(response)
    }

    override suspend fun getHeroes(limit: Int, offset: Int, nameStartsWith: String): Heroes {
        val publicKey = context.getString(R.string.public_key)
        val privateKey = context.getString(R.string.private_key)
        val timeNow = System.currentTimeMillis()
        val message = timeNow.toString()+privateKey+publicKey
        val md = MessageDigest.getInstance("MD5")
        val messageDigest = md.digest(message.toByteArray())
        val no = BigInteger(1, messageDigest)
        var hashtext: String = no.toString(16)
        while (hashtext.length < 32) {
            hashtext = "0$hashtext"
        }
        val response = marvelApi.getHeroes(
            publicKey,
            timeNow,
            hashtext,
            limit,
            offset,
            nameStartsWith
        ).await()
        return Heroes.fromHeroesResponse(response)
    }
}