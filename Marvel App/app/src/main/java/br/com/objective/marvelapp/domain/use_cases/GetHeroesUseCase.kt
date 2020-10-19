package br.com.objective.marvelapp.domain.use_cases

import br.com.objective.marvelapp.domain.entities.Heroes
import br.com.objective.marvelapp.domain.repositories.HeroesRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

class GetHeroesUseCase(val limit: Int, val offset: Int) : BaseUseCase<Heroes>(), KoinComponent {

    private val heroesRepository: HeroesRepository by inject()

    override suspend fun execute(): Heroes {
        try {
            return heroesRepository.getHeroes(limit, offset)
        } catch (e: Exception) {
            throw treatException(e)
        }
    }
}