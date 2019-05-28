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

import java.util.ArrayList

class ConnectAlliesActivity : AppCompatActivity() {
    private val alliesList = ArrayList<String>()
    private var communityAlliesAdapter: CommunityAlliesAdapter? = null

    @BindView(R.id.text_title)
    internal var textTitle: TextView? = null
    @BindView(R.id.text_description)
    internal var textDescription: TextView? = null
    @BindView(R.id.recycler_view)
    internal var recyclerView: RecyclerView? = null
    @BindView(R.id.button_community)
    internal var buttonCommunity: Button? = null
    @BindString(R.string.connect_with_allias)
    internal var textAllias: String? = null
    @BindString(R.string.we_found_global)
    internal var textTitleDescription: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_allies)
        ButterKnife.bind(this)

        textTitle!!.text = textAllias
        textDescription!!.text = textTitleDescription

        setAdapter(alliesList)
    }

    @OnClick(R.id.button_community)
    internal fun buttonCommunityTapped() {
        startActivity(Intent(this@ConnectAlliesActivity, DashboardActivity::class.java))
    }

    fun setAdapter(alliesList: ArrayList<String>) {
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        communityAlliesAdapter = CommunityAlliesAdapter(alliesList, this@ConnectAlliesActivity)
        recyclerView!!.adapter = communityAlliesAdapter
    }
}
