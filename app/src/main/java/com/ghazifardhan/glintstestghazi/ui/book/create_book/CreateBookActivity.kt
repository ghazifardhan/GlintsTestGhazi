package com.ghazifardhan.glintstestghazi.ui.book.create_book

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ghazifardhan.glintstestghazi.R
import com.ghazifardhan.glintstestghazi.ui.login.LoginViewModel
import com.ghazifardhan.glintstestghazi.ui.main.MainActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_create_book.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import com.ghazifardhan.glintstestghazi.models.general.Error

class CreateBookActivity : AppCompatActivity() {

    private lateinit var viewModel: CreateBookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_book)

        supportActionBar?.title = "Create Book Data"

        initViewModel()
        initLayout()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CreateBookViewModel::class.java)
    }

    private fun initLayout() {

        submit.setOnClickListener {

            val title = book_title.text.toString()
            val desc = book_desc.text.toString()
            val tItem = total_item.text.toString()
            val supplier = supplier.text.toString()
            val tDate = transaction_date.text.toString()

            viewModel.doCreateBook(title, desc, tItem, supplier, tDate).observe(this, Observer {
                Log.d("HttpCode", it.code().toString())
                if (it.code() == 201) {
                    toast("Success")
                } else if (it.code() == 422) {
                    val gson = Gson()
                    val errorModel = gson.fromJson(it.errorBody()?.charStream(), Error::class.java)
                    showError(errorModel)
                }
            })
        }
    }

    private fun showError(error: Error) {
        if (error?.errors.bookTitle != null) {
            book_title.error = error.errors.bookTitle[0]
        }
        if (error?.errors.bookDesc != null) {
            book_desc.error = error.errors.bookTitle[0]
        }
        if (error?.errors.totalItem != null) {
            total_item.error = error.errors.bookTitle[0]
        }
        if (error?.errors.supplier != null) {
            supplier.error = error.errors.bookTitle[0]
        }
        if (error?.errors.transactionDate != null) {
            transaction_date.error = error.errors.bookTitle[0]
        }
    }
}
