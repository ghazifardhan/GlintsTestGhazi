package com.ghazifardhan.glintstestghazi.repository.books

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ghazifardhan.glintstestghazi.api.InitLibrary
import com.ghazifardhan.glintstestghazi.models.books.Data
import io.reactivex.disposables.CompositeDisposable

class ListBooksDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: InitLibrary
): DataSource.Factory<Int, Data>() {

    val listBooksDS = MutableLiveData<ListBooksDataSource>()

    override fun create(): DataSource<Int, Data> {
        val listBooksDataSource = ListBooksDataSource(networkService, compositeDisposable)
        listBooksDS.postValue(listBooksDataSource)
        return listBooksDataSource
    }

}