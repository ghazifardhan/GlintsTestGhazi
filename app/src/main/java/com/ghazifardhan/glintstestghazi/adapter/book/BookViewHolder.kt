package com.ghazifardhan.glintstestghazi.adapter.book

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ghazifardhan.glintstestghazi.R
import com.ghazifardhan.glintstestghazi.models.books.Data
import kotlinx.android.synthetic.main.item_book_list.view.*

class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(data: Data?, listener: BookAdapter.OnEditClickListener) {
        if (data != null) {
            itemView.title.text = data.bookTitle
            itemView.desc.text = data.bookDesc
            itemView.total_item.text = data.totalItem.toString()
            itemView.supplier.text = data.supplier
            itemView.transaction_date.text = data.transactionDate

            itemView.card_view.setOnClickListener {
                listener.onEditClick(data, it)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): BookViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book_list, parent, false)
            return BookViewHolder(view)
        }
    }
}