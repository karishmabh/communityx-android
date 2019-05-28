package com.communityx.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.adapters.DonatedCreatorAdapter
import com.communityx.utils.CustomToolBarHelper

import java.util.ArrayList

class DonatedCreatedActivity : AppCompatActivity() {

    private val mDonatedList = ArrayList<String>()
    private var donatedCreatorAdapter: DonatedCreatorAdapter? = null

    @BindView(R.id.recycler_donated)
    internal var recyclerDonated: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donated_created)
        ButterKnife.bind(this)
        setupToolbar()
        setAdapter(mDonatedList)
    }

    private fun setAdapter(donatedList: ArrayList<String>) {
        recyclerDonated!!.layoutManager = LinearLayoutManager(this)
        donatedCreatorAdapter = DonatedCreatorAdapter(donatedList, this@DonatedCreatedActivity)
        recyclerDonated!!.adapter = donatedCreatorAdapter
    }

    private fun setupToolbar() {
        val customToolBarUtils = CustomToolBarHelper(this)
        customToolBarUtils.setTitle("Donated by (219)")
        customToolBarUtils.enableBackPress()
    }
}
