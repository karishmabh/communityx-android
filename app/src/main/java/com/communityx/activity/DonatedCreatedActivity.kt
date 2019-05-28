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
import kotlinx.android.synthetic.main.activity_donated_created.*

import java.util.ArrayList

class DonatedCreatedActivity : AppCompatActivity() {

    private val mDonatedList = ArrayList<String>()
    private var donatedCreatorAdapter: DonatedCreatorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donated_created)
        ButterKnife.bind(this)
        setupToolbar()
        setAdapter(mDonatedList)
    }

    private fun setAdapter(donatedList: ArrayList<String>) {
        recycler_donated!!.layoutManager = LinearLayoutManager(this)
        donatedCreatorAdapter = DonatedCreatorAdapter(donatedList, this)
        recycler_donated!!.adapter = donatedCreatorAdapter
    }

    private fun setupToolbar() {
        val customToolBarUtils = CustomToolBarHelper(this)
        customToolBarUtils.setTitle(getString(R.string.string_donated_by))
        customToolBarUtils.enableBackPress()
    }
}
