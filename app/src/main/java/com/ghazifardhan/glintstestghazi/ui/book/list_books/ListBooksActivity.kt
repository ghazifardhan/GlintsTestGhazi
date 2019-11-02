package com.ghazifardhan.glintstestghazi.ui.book.list_books

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ghazifardhan.glintstestghazi.R
import com.ghazifardhan.glintstestghazi.adapter.book.BookAdapter
import com.ghazifardhan.glintstestghazi.helpers.State
import com.ghazifardhan.glintstestghazi.models.books.Data
import kotlinx.android.synthetic.main.activity_list_books.*
import org.jetbrains.anko.toast

class ListBooksActivity : AppCompatActivity(), BookAdapter.OnEditClickListener {

    private lateinit var viewModel: ListBooksViewModel
    private lateinit var mAdapter: BookAdapter
    private lateinit var mLinearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_books)

        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.title = "List Books"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        instantiateData()
        setUpUi()
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        swipeRefresh.isRefreshing = true
        viewModel.swipeRefresh()
    }

    fun setUpUi() {
        swipeRefresh.setOnRefreshListener {
            viewModel.swipeRefresh()
        }
    }

    fun instantiateData() {
        viewModel = ViewModelProviders.of(this)
            .get(ListBooksViewModel::class.java)

        initAdapter()
        initState()
    }

    private fun initAdapter() {
        mAdapter = BookAdapter(this) { viewModel.retry() }
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = mAdapter

        viewModel.bookList.observe(this, Observer {
            displayList(it)
        })
    }

    private fun displayList(communities: PagedList<Data>) {
        mAdapter.submitList(communities)
        mAdapter.notifyDataSetChanged()
        swipeRefresh.isRefreshing = false
    }

    private fun initState() {
        txt_error.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility = if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                mAdapter.setState(state ?: State.DONE)
            }
        })
    }

    override fun onEditClick(data: Data, view: View) {
        toast(data.bookTitle)
    }
}
