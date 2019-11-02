package com.ghazifardhan.glintstestghazi.ui.book.create_book

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.ghazifardhan.glintstestghazi.R
import com.ghazifardhan.glintstestghazi.ui.login.LoginViewModel
import com.ghazifardhan.glintstestghazi.ui.main.MainActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_create_book.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import com.ghazifardhan.glintstestghazi.models.general.Error
import java.text.SimpleDateFormat
import java.util.*

class CreateBookActivity : AppCompatActivity() {

    private lateinit var viewModel: CreateBookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_book)

        supportActionBar?.title = "Create Book Data"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        initViewModel()
        initLayout()
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
                    toast("Success Create Book Data, Please go to list book to view the data")
                } else if (it.code() == 422) {
                    val gson = Gson()
                    val errorModel = gson.fromJson(it.errorBody()?.charStream(), Error::class.java)
                    showError(errorModel)
                }
            })
        }

        transaction_date.setOnFocusChangeListener { _, b ->
            if (b) {
                showDatePicker()
            }
        }

        transaction_date.setOnClickListener {
            showDatePicker()
        }


    }

    fun showDatePicker() {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var transactionDate = ""
        val md = MaterialDialog(this).show {
            title(text = "Pilih Tanggal Lahir")
            datePicker() { _, date ->
                Log.d("datePicker", date.toString())
                transactionDate = formatter.format(Date(date.timeInMillis))
                Log.d("datePicker", transactionDate)
            }
            lifecycleOwner(this@CreateBookActivity)
        }
        md.onDismiss {
            Handler().postDelayed({
                transaction_date.setText(transactionDate)
            }, 100)
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
