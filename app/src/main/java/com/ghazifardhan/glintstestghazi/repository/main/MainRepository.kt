package com.ghazifardhan.glintstestghazi.repository.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ghazifardhan.glintstestghazi.models.menu.Menu

class MainRepository {

    private var instance: MainRepository? = null
    private var dataSet: ArrayList<Menu> = ArrayList<Menu>()

    fun getInstance(): MainRepository {
        if (instance == null) {
            instance = MainRepository()
        }
        return instance as MainRepository
    }

    fun getMainMenu(): MutableLiveData<List<Menu>> {
        setMainMenu()
        val data: MutableLiveData<List<Menu>> = MutableLiveData()
        data.value = dataSet
        return data
    }

    private fun setMainMenu() {

        dataSet.add(Menu(1, "Create Book Data"))
        dataSet.add(Menu(2, "List of Book Data"))
        dataSet.add(Menu(3, "Logout"))

    }
}