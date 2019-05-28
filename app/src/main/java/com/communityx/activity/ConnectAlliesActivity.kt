package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.CommunityAlliesAdapter
import kotlinx.android.synthetic.main.activity_connect_allies.*
import kotlinx.android.synthetic.main.layout_top_view.*

import java.util.ArrayList

class ConnectAlliesActivity : AppCompatActivity() {
    private val alliesList = ArrayList<String>()
    private var communityAlliesAdapter: CommunityAlliesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_allies)
        ButterKnife.bind(this)

        text_title!!.text = getString(R.string.connect_with_allias)
        text_description!!.text = getString(R.string.we_found_global)

        setAdapter(alliesList)
    }

    @OnClick(R.id.button_community)
    internal fun buttonCommunityTapped() {
        startActivity(Intent(this, DashboardActivity::class.java))
    }

    fun setAdapter(alliesList: ArrayList<String>) {
        recycler_view!!.layoutManager = LinearLayoutManager(this)
        communityAlliesAdapter = CommunityAlliesAdapter(alliesList, this)
        recycler_view!!.adapter = communityAlliesAdapter
    }
}
