package com.ghazifardhan.glintstestghazi.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ghazifardhan.glintstestghazi.R
import com.ghazifardhan.glintstestghazi.models.general.Error
import com.ghazifardhan.glintstestghazi.ui.main.MainActivity
import com.ghazifardhan.glintstestghazi.ui.main.MainViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViewModel()
        initLayout()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    private fun initLayout() {

        login.setOnClickListener {

            val email = username.text.toString()
            val password = password.text.toString()

            viewModel.doLogin(email, password).observe(this, Observer {
                Log.d("HttpCode", it.code().toString())
                if (it.isSuccessful && it.code() == 200) {
                    toast("Logged in")
                    val i = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(i)
                } else if (it.code() == 401) {
                    val gson = Gson()
                    val errorModel = gson.fromJson(it.errorBody()?.charStream(), Error::class.java)
                    toast(errorModel.message)
                }
            })
        }

    }
}
