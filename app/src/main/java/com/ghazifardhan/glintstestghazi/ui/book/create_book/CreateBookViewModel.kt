package com.ghazifardhan.glintstestghazi.ui.book.create_book

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ghazifardhan.glintstestghazi.api.InitLibrary
import com.ghazifardhan.glintstestghazi.models.books.Book
import com.ghazifardhan.glintstestghazi.models.login.Login
import com.ghazifardhan.glintstestghazi.repository.books.CreateBookRepository
import com.ghazifardhan.glintstestghazi.repository.login.LoginRepository
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response

class CreateBookViewModel: ViewModel() {

    private val networkService = InitLibrary()
    private val compositeDisposable = CompositeDisposable()
    private var createBookRepository: CreateBookRepository

    init {
        createBookRepository = CreateBookRepository(networkService, compositeDisposable)
    }

    fun doCreateBook(title: String, desc: String, tItem: String, supplier: String, tDate: String): LiveData<Response<Book>> {
        return createBookRepository.doCreateBook(title, desc, tItem, supplier, tDate)
    }

}