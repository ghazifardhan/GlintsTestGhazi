package com.ghazifardhan.glintstestghazi.ui.book.list_books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ghazifardhan.glintstestghazi.api.InitLibrary
import com.ghazifardhan.glintstestghazi.helpers.State
import com.ghazifardhan.glintstestghazi.models.books.Data
import com.ghazifardhan.glintstestghazi.repository.books.ListBooksDataSource
import com.ghazifardhan.glintstestghazi.repository.books.ListBooksDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class ListBooksViewModel: ViewModel() {

    private val networkService = InitLibrary()

    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 15
    private var listBooksDataSourceFactory: ListBooksDataSourceFactory
    var bookList: LiveData<PagedList<Data>>
    private val config: PagedList.Config

    init {
        listBooksDataSourceFactory = ListBooksDataSourceFactory(compositeDisposable, networkService)
        config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        bookList = LivePagedListBuilder<Int, Data>(listBooksDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap<ListBooksDataSource,
            State>(listBooksDataSourceFactory.listBooksDS, ListBooksDataSource::state)

    fun swipeRefresh() {
        listBooksDataSourceFactory.listBooksDS.value?.invalidate()
    }

    fun retry() {
        listBooksDataSourceFactory.listBooksDS.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return bookList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}