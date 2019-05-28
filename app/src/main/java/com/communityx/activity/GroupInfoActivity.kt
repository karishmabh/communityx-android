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
import kotlinx.android.synthetic.main.activity_group_info.*

class GroupInfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_info)
        ButterKnife.bind(this)

        setGroupToolbar(this, getString(R.string.string_charity_life), getString(R.string.string_created_by_you))
        setUpRecyclerData()
    }

    private fun setUpRecyclerData() {
        recycler_view!!.layoutManager = LinearLayoutManager(this)
        recycler_view!!.adapter = GroupInfoAdapter(this)
    }
}
