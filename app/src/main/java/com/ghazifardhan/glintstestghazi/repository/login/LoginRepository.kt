package com.ghazifardhan.glintstestghazi.repository.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ghazifardhan.glintstestghazi.Initializer
import com.ghazifardhan.glintstestghazi.api.InitLibrary
import com.ghazifardhan.glintstestghazi.helpers.RealmHelperKotlin
import com.ghazifardhan.glintstestghazi.models.login.Login
import com.ghazifardhan.glintstestghazi.models.realm.JwtTokenKotlin
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.Response

class LoginRepository(
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

    fun doLogin(authKey: String): MutableLiveData<Response<Login>> {
        val response: MutableLiveData<Response<Login>> = MutableLiveData()
        compositeDisposable.add(
            networkService.getInstance().login(authKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it.isSuccessful) {
                            val jwtToken = JwtTokenKotlin(1, it.body()?.data?.jwt)

                            realmHelper = RealmHelperKotlin(realm)
                            realmHelper.save(jwtToken)
                        }
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