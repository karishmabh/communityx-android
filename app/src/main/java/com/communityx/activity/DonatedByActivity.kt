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

import java.util.ArrayList

class DonatedByActivity : AppCompatActivity() {
    @BindView(R.id.recycler_likes)
    internal var recyclerLikes: RecyclerView? = null
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
        recyclerLikes!!.layoutManager = LinearLayoutManager(this)
        donatedByAdapter = DonatedByAdapter(donatedByList, this@DonatedByActivity)
        recyclerLikes!!.adapter = donatedByAdapter
    }

    private fun setupToolbar() {
        val customToolBarUtils = CustomToolBarHelper(this)
        customToolBarUtils.setTitle("Donated by (219)")
        customToolBarUtils.enableBackPress()
    }
}

