package com.ghazifardhan.glintstestghazi.repository.books

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.ghazifardhan.glintstestghazi.Initializer
import com.ghazifardhan.glintstestghazi.api.InitLibrary
import com.ghazifardhan.glintstestghazi.helpers.RealmHelperKotlin
import com.ghazifardhan.glintstestghazi.models.books.Book
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.Response

class CreateBookRepository(
    private val networkService: InitLibrary,
    private val compositeDisposable: CompositeDisposable
) {

    private var realm: Realm
    private var realmHelper: RealmHelperKotlin
    private var token: String? = ""

    init {
        val context: Context = Initializer.ctx!!

        Realm.init(context)
        val configuration = RealmConfiguration.Builder().build()
        realm = Realm.getInstance(configuration)
        realmHelper = RealmHelperKotlin(realm)
        token = realmHelper.loadToken()?.jwt
    }

    fun doCreateBook(title: String, desc: String, tItem: String, supplier: String, tDate: String): MutableLiveData<Response<Book>> {
        val response: MutableLiveData<Response<Book>> = MutableLiveData()
        compositeDisposable.add(
            networkService.getInstance().doCreateBook("Bearer $token", title, desc, tItem, supplier, tDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        response.value = it
                    },
                    {
                        it.printStackTrace()
                    }
                )
        )

        return response
    }
}