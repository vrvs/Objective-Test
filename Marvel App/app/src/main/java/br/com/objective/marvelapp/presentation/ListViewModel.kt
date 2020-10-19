package br.com.objective.marvelapp.presentation

import androidx.lifecycle.ViewModel
import br.com.objective.marvelapp.domain.entities.Heroes
import br.com.objective.marvelapp.domain.use_cases.GetHeroesByNameUseCase
import br.com.objective.marvelapp.domain.use_cases.GetHeroesUseCase
import br.com.objective.marvelapp.util.ResultCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.ceil
import kotlin.math.floor

class ListViewModel : ViewModel() {

    private var offset: Int = 0
    private val limit: Int = 4
    private var total: Int = 0
    private var currentPage: Int = 0
    private var mode = 0

    fun getHeroes(): ResultCall<Heroes> {
        if (mode != 0) {
            offset = 0
            currentPage = 0
            mode = 0
        }
        val heroes = ResultCall<Heroes>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (total == 0 || ceil(total.toDouble()/limit) > currentPage) {
                    val result = GetHeroesUseCase(limit, offset).execute()
                    offset = result.offset + limit
                    total = result.total
                    currentPage = floor(offset.toDouble()/limit).toInt()
                    heroes.success(result)
                } else {
                    heroes.failure(Exception("")) // TODO: Do the right exception
                }
            } catch (e: Exception) {
                heroes.failure(e)
            }
        }
        return heroes
    }

    fun getHeroesPrevious(): ResultCall<Heroes> {
        if (mode != 0) {
            offset = 0
            currentPage = 0
            mode = 0
        }
        val heroes = ResultCall<Heroes>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (currentPage > 1) {
                    val result = GetHeroesUseCase(limit, offset-limit*2).execute()
                    offset = result.offset + limit
                    total = result.total
                    currentPage = floor(offset.toDouble()/limit).toInt()
                    heroes.success(result)
                } else {
                    heroes.failure(Exception("")) // TODO: Do the right exception
                }
            } catch (e: Exception) {
                heroes.failure(e)
            }
        }
        return heroes
    }

    fun getHeroes(search: String): ResultCall<Heroes> {
        if (mode != 1) {
            offset = 0
            total = 0
            currentPage = 0
            mode = 1
        }
        val heroes = ResultCall<Heroes>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (total == 0 || ceil(total.toDouble()/limit) > currentPage) {
                    val result = GetHeroesByNameUseCase(limit, offset, search).execute()
                    offset = result.offset + limit
                    total = result.total
                    currentPage = floor(offset.toDouble()/limit).toInt()
                    heroes.success(result)
                } else {
                    heroes.failure(Exception("")) // TODO: Do the right exception
                }
            } catch (e: Exception) {
                heroes.failure(e)
            }
        }
        return heroes
    }

    fun getHeroesPrevious(search: String): ResultCall<Heroes> {
        if (mode != 1) {
            offset = 0
            total = 0
            currentPage = 0
            mode = 1
        }
        val heroes = ResultCall<Heroes>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (currentPage > 1) {
                    val result = GetHeroesByNameUseCase(limit, offset-limit*2, search).execute()
                    offset = result.offset + limit
                    total = result.total
                    currentPage = floor(offset.toDouble()/limit).toInt()
                    heroes.success(result)
                } else {
                    heroes.failure(Exception("")) // TODO: Do the right exception
                }
            } catch (e: Exception) {
                heroes.failure(e)
            }
        }
        return heroes
    }

    fun resetMode() {
        mode = 2
    }
}