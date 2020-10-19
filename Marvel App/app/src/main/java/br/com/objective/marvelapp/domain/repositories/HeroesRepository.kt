package br.com.objective.marvelapp.domain.repositories

import br.com.objective.marvelapp.domain.entities.Heroes

interface HeroesRepository {
    suspend fun getHeroes(limit: Int, offset: Int): Heroes
    suspend fun getHeroes(limit: Int, offset: Int, nameStartsWith: String): Heroes
}