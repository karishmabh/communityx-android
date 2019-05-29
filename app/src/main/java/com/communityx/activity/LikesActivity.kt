package com.communityx.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.adapters.LikesAdapter
import com.communityx.utils.CustomToolBarHelper
import kotlinx.android.synthetic.main.activity_likes.*

import java.util.ArrayList

class LikesActivity : AppCompatActivity() {

    private val likesList = ArrayList<String>()
    private var likesAdapter: LikesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_likes)
        ButterKnife.bind(this)
        setAdapter(likesList)
        setupToolbar()
    }

    fun setAdapter(likesList: ArrayList<String>) {
        recycler_likes!!.layoutManager = LinearLayoutManager(this)
        likesAdapter = LikesAdapter(likesList, this)
        recycler_likes!!.adapter = likesAdapter
    }

    private fun setupToolbar() {
        val customToolBarUtils = CustomToolBarHelper(this)
        customToolBarUtils.setTitle(getString(R.string.string_likes))
        customToolBarUtils.enableBackPress()
    }
}

