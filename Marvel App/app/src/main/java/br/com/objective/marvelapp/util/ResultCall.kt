package br.com.objective.marvelapp.util

import androidx.lifecycle.MutableLiveData

class ResultCall<T>: MutableLiveData<Data<T>>() {
    fun success(data: T) {
        super.postValue(Data(data))
    }

    fun failure(exception: Exception) {
        super.postValue(Data(exception))
    }
}

data class Data<T>(
    val data: T? = null,
    val exception: Exception? = null
) {
    constructor(data: T) : this(data, null)
    constructor(exception: Exception) : this(null, exception)

    fun isSuccessful(): Boolean {
        return data != null
    }
}