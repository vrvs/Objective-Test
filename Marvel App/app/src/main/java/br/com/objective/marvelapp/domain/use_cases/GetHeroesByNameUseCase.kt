package br.com.objective.marvelapp.domain.use_cases

import br.com.objective.marvelapp.domain.entities.Heroes
import br.com.objective.marvelapp.domain.repositories.HeroesRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

class GetHeroesByNameUseCase(val limit: Int, val offset: Int, val nameStartsWith: String) : BaseUseCase<Heroes>(), KoinComponent {

    private val heroesRepository: HeroesRepository by inject()

    override suspend fun execute(): Heroes {
        try {
            return heroesRepository.getHeroes(limit, offset, nameStartsWith)
        } catch (e: Exception) {
            throw treatException(e)
        }
    }
}