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

import java.util.ArrayList

class LikesActivity : AppCompatActivity() {
    @BindView(R.id.recycler_likes)
    internal var recyclerLikes: RecyclerView? = null
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
        recyclerLikes!!.layoutManager = LinearLayoutManager(this)
        likesAdapter = LikesAdapter(likesList, this@LikesActivity)
        recyclerLikes!!.adapter = likesAdapter
    }

    private fun setupToolbar() {
        val customToolBarUtils = CustomToolBarHelper(this)
        customToolBarUtils.setTitle("Likes(1k)")
        customToolBarUtils.enableBackPress()
    }
}

