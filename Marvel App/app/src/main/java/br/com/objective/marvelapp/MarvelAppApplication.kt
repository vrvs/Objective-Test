package br.com.objective.marvelapp

import android.app.Application
import br.com.objective.marvelapp.app.marvelApiModule
import br.com.objective.marvelapp.app.repositoryModule
import br.com.objective.marvelapp.app.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MarvelAppApplication: Application() {
    private var listofModules = listOf(
        marvelApiModule,
        repositoryModule,
        viewModelModule
    )

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MarvelAppApplication)
            modules(listofModules)
        }
    }
}