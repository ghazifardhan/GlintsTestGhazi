package com.ghazifardhan.glintstestghazi.helpers

import android.util.Log
import com.ghazifardhan.glintstestghazi.models.realm.JwtTokenKotlin
import io.realm.Realm

class RealmHelperKotlin(private var realm: Realm) {

    fun save(jwtToken: JwtTokenKotlin?) {

        realm.beginTransaction()
        realm.executeTransactionAsync ({
            val jwt = it.createObject(JwtTokenKotlin::class.java)
            jwt.id = jwtToken?.id
            jwt.jwt = jwtToken?.jwt
        },{
            Log.d("REALM","On Success: Data Written Successfully!")
            readData()
        },{
            Log.d("REALM","On Error: Error in saving Data!")
        })
        realm.commitTransaction()
    }

    private fun readData() {
        val students = realm.where(JwtTokenKotlin::class.java).findAll()
        var response=""
        students.forEach {
            response = response + "Name: ${it.id}, Age: ${it.jwt}" +"\n"
            Log.d("Response:", response)
        }
    }

    fun loadToken(): JwtTokenKotlin? {
        realm.beginTransaction()
        realm.commitTransaction()
        return realm.where(JwtTokenKotlin::class.java).findFirst()
    }

    fun deleteToken() {
        realm.beginTransaction()
        val model = realm.where(JwtTokenKotlin::class.java).findAll()
        model.deleteAllFromRealm()
        realm.commitTransaction()
    }

}