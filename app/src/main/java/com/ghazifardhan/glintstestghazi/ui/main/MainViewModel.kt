package com.ghazifardhan.glintstestghazi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ghazifardhan.glintstestghazi.models.menu.Menu
import com.ghazifardhan.glintstestghazi.repository.main.MainRepository

class MainViewModel: ViewModel() {

    private var items: MutableLiveData<List<Menu>>? = null
    private var repository: MainRepository? = null

    fun init() {
        if (items != null) {
            return
        }
        repository = MainRepository().getInstance()
        items = repository!!.getMainMenu()
    }

    fun getMainMenu(): LiveData<List<Menu>>? = items

}