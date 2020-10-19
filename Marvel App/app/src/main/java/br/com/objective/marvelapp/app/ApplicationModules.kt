package br.com.objective.marvelapp.app

import br.com.objective.data.data_sources.remote.MarvelApiGenerator
import br.com.objective.marvelapp.data.repositories.HeroesRepositoryImpl
import br.com.objective.marvelapp.domain.repositories.HeroesRepository
import br.com.objective.marvelapp.presentation.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val marvelApiModule = module {
    single {
        MarvelApiGenerator()
            .getMarveApi()
    }
}

val repositoryModule = module {
    single {
        HeroesRepositoryImpl(get()) as HeroesRepository
    }
}

val viewModelModule = module {
    viewModel {
        ListViewModel()
    }
}