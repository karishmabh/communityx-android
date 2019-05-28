package com.communityx.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.adapters.DonatedByAdapter
import com.communityx.utils.CustomToolBarHelper
import kotlinx.android.synthetic.main.activity_donated_by.*

import java.util.ArrayList

class DonatedByActivity : AppCompatActivity() {

    private val likesList = ArrayList<String>()
    private var donatedByAdapter: DonatedByAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donated_by)
        ButterKnife.bind(this)
        setAdapter(likesList)
        setupToolbar()

    }

    fun setAdapter(donatedByList: ArrayList<String>) {
        recycler_likes!!.layoutManager = LinearLayoutManager(this)
        donatedByAdapter = DonatedByAdapter(donatedByList, this)
        recycler_likes!!.adapter = donatedByAdapter
    }

    private fun setupToolbar() {
        val customToolBarUtils = CustomToolBarHelper(this)
        customToolBarUtils.setTitle(getString(R.string.string_donated_by))
        customToolBarUtils.enableBackPress()
    }
}

