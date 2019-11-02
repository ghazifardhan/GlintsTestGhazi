package com.ghazifardhan.glintstestghazi.adapter.menu

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class MenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private var mCurrentPosition: Int = 0
    private var mListener: MenuAdapter.OnItemClickListener? = null

    protected abstract fun clear()

    open fun onBind(position: Int, listener: MenuAdapter.OnItemClickListener) {
        mCurrentPosition = position
        mListener = listener
        clear()
    }

    fun getCurrentPosition(): Int {
        return mCurrentPosition
    }
}