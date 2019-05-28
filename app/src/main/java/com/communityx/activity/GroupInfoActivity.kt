package com.communityx.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.adapters.GroupInfoAdapter
import com.communityx.utils.Utils

class GroupInfoActivity : BaseActivity() {

    @BindView(R.id.image_tail_one)
    internal var imageEdit: ImageView? = null
    @BindView(R.id.image_tail_two)
    internal var imageAdd: ImageView? = null
    @BindView(R.id.recycler_view)
    internal var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_info)
        ButterKnife.bind(this)

        setGroupToolbar(this, "Charity Life", "Created by You, today at 7:15 pm")
        setUpRecyclerData()
    }

    private fun setUpRecyclerData() {
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.adapter = GroupInfoAdapter(this)
    }
}
