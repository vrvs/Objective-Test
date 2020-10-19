package br.com.objective.marvelapp.domain.use_cases

abstract class BaseUseCase<T> {

    abstract suspend fun execute(): T

    fun treatException(e: Exception): Exception {
        return e
    }
}