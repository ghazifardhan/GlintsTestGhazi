package com.ghazifardhan.glintstestghazi.repository.books

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.ghazifardhan.glintstestghazi.Initializer
import com.ghazifardhan.glintstestghazi.api.InitLibrary
import com.ghazifardhan.glintstestghazi.helpers.RealmHelperKotlin
import com.ghazifardhan.glintstestghazi.helpers.State
import com.ghazifardhan.glintstestghazi.models.books.Data
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmConfiguration

class ListBooksDataSource(
    private val networkService: InitLibrary,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Data>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null
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

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Data>
    ) {
        updateState(State.LOADING)
        compositeDisposable.add(
            networkService.getInstance().listBooks("Bearer $token", 1, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(response.data,
                            null,
                            2
                        )
                    },
                    {
                        it.printStackTrace()
                        updateState(State.ERROR)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Data>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            networkService.getInstance().listBooks("Bearer $token", params.key, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(response.data,
                            params.key + 1
                        )
                    },
                    {
                        it.printStackTrace()
                        updateState(State.ERROR)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Data>) {
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)

    }

}