package com.ghazifardhan.glintstestghazi.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ghazifardhan.glintstestghazi.helpers.RealmHelperKotlin
import com.ghazifardhan.glintstestghazi.models.realm.JwtTokenKotlin
import com.ghazifardhan.glintstestghazi.ui.login.LoginActivity
import com.ghazifardhan.glintstestghazi.ui.main.MainActivity
import io.realm.Realm
import io.realm.RealmConfiguration

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mHandler: Handler
    private lateinit var realm: Realm
    private lateinit var realmHelper: RealmHelperKotlin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup Realm
        Realm.init(this)
        val configuration = RealmConfiguration.Builder().build()
        realm = Realm.getInstance(configuration)
        realmHelper = RealmHelperKotlin(realm)

        mHandler = Handler()

        val runnable = object: Runnable {
            override fun run() {
                val jwt: JwtTokenKotlin? = realmHelper.loadToken()

                if (jwt?.jwt == null) {
                    val i = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }
        }

        mHandler.postDelayed(runnable, 200)
    }
}
