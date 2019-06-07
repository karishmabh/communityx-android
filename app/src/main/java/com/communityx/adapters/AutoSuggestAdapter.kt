package com.communityx.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filterable
import java.util.*

class AutoSuggestAdapter(context: Context, resource: Int) : ArrayAdapter<String>(context, resource), Filterable {
    private val mlistData: MutableList<String>

    init {
        mlistData = ArrayList()
    }

    fun setData(list: List<String>) {
        mlistData.clear()
        mlistData.addAll(list)
    }

    override fun getCount(): Int {
        return mlistData.size
    }

    override fun getItem(position: Int): String? {
        return mlistData[position]
    }

    fun getObject(position: Int): String {
        return mlistData[position]
    }
}