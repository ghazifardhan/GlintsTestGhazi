package com.ghazifardhan.glintstestghazi.ui.login

import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ghazifardhan.glintstestghazi.api.InitLibrary
import com.ghazifardhan.glintstestghazi.models.login.Login
import com.ghazifardhan.glintstestghazi.repository.login.LoginRepository
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response
import java.io.UnsupportedEncodingException

class LoginViewModel: ViewModel() {

    private val networkService = InitLibrary()
    private val compositeDisposable = CompositeDisposable()
    private var loginRepository: LoginRepository

    init {
        loginRepository = LoginRepository(networkService, compositeDisposable)
    }

    fun doLogin(email: String, password: String): LiveData<Response<Login>> {
        val authKey = getAuthToken(email, password)
        return loginRepository.doLogin(authKey)
    }

    private fun getAuthToken(username: String, password: String): String {
        var data: ByteArray? = null
        try {
            data = ("$username:$password").toByteArray()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP)
    }

}