package com.ghazifardhan.glintstestghazi.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ghazifardhan.glintstestghazi.R
import com.ghazifardhan.glintstestghazi.adapter.menu.MenuAdapter
import com.ghazifardhan.glintstestghazi.helpers.RealmHelperKotlin
import com.ghazifardhan.glintstestghazi.models.menu.Menu
import com.ghazifardhan.glintstestghazi.ui.book.create_book.CreateBookActivity
import com.ghazifardhan.glintstestghazi.ui.book.list_books.ListBooksActivity
import com.ghazifardhan.glintstestghazi.ui.login.LoginActivity
import com.ghazifardhan.glintstestghazi.ui.login.LoginViewModel
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), MenuAdapter.OnItemClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var mLayoutManager: GridLayoutManager
    private lateinit var mAdapter: MenuAdapter

    private lateinit var realm: Realm
    private lateinit var realmHelper: RealmHelperKotlin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup Realm
        Realm.init(this)
        val configuration = RealmConfiguration.Builder().build()
        realm = Realm.getInstance(configuration)
        realmHelper = RealmHelperKotlin(realm)

        setUpViewModel()
        setUpUi()
    }

    fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.init()
        viewModel.getMainMenu()?.observe(this, Observer {
            mAdapter.notifyDataSetChanged()
        })
    }

    fun setUpUi() {
        mLayoutManager = GridLayoutManager(this, 2)
        mLayoutManager.orientation = RecyclerView.VERTICAL
        reyclerView.layoutManager = mLayoutManager
        mAdapter = MenuAdapter(viewModel.getMainMenu()?.value as MutableList<Menu>, this)
        reyclerView.adapter = mAdapter
    }

    override fun onItemClick(menu: Menu) {
        when (menu.id) {
            1 -> {
                val i = Intent(this@MainActivity, CreateBookActivity::class.java)
                startActivity(i)
            }
            2 -> {
                val i = Intent(this@MainActivity, ListBooksActivity::class.java)
                startActivity(i)
            }
            3 -> {
                realmHelper.deleteToken()
                val i = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
            else -> {
                toast(menu.name)
            }
        }
    }
}
