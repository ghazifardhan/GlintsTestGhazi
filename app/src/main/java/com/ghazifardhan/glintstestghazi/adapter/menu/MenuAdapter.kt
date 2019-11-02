package com.ghazifardhan.glintstestghazi.adapter.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ghazifardhan.glintstestghazi.R
import com.ghazifardhan.glintstestghazi.models.menu.Menu

class MenuAdapter(items: MutableList<Menu>, onItemClickListener: OnItemClickListener): RecyclerView.Adapter<MenuViewHolder>() {

    val VIEW_TYPE_NORMAL = 1
    private var items: MutableList<Menu>? = items
    private var mOnItemClickListener: OnItemClickListener = onItemClickListener

    interface OnItemClickListener {
        fun onItemClick(menu: Menu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu_list, parent, false),
            items
        )
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.onBind(position, mOnItemClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_NORMAL
    }

    override fun getItemCount(): Int {
        return if (items != null && items!!.size > 0) {
            items!!.size
        } else {
            0
        }
    }

    fun addItems(menu: List<Menu>) {
        items!!.addAll(menu)
        notifyDataSetChanged()
    }

    fun clearItems() {
        items!!.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View, menuList: MutableList<Menu>?) :
        MenuViewHolder(itemView) {

        internal var mTitle: TextView? = null
        internal var mCardView: CardView? = null

        init {
            items = menuList

            mTitle = itemView.findViewById(R.id.title)
            mCardView = itemView.findViewById(R.id.card_view)
        }

        override fun onBind(position: Int, listener: OnItemClickListener) {
            super.onBind(position, listener)

            mTitle!!.text = items!![position].name

            mCardView!!.setOnClickListener {
                listener.onItemClick(items!![position])
            }
        }


        protected override fun clear() {

        }
    }

}