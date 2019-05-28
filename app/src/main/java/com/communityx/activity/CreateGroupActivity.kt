package com.communityx.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.SelectedMembersAdapter
import com.communityx.utils.AppConstant
import kotlinx.android.synthetic.main.activity_create_group.*

import java.util.ArrayList

class CreateGroupActivity : AppCompatActivity(), AppConstant, SelectedMembersAdapter.IUserRemoved {

    private var mUsersList: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)
        ButterKnife.bind(this)

        getIntentData()
    }

    @OnClick(R.id.imageBack)
    internal fun backTapped() {
        super.onBackPressed()
    }

    private fun getIntentData() {
        mUsersList = intent.extras!!.get(AppConstant.INTENT_USERLIST) as ArrayList<String>

        if (mUsersList == null) return
        recycler_added_users!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val selectedMembersAdapter = SelectedMembersAdapter(mUsersList, this, this)
        recycler_added_users!!.adapter = selectedMembersAdapter

        text_participant!!.text = "Participants: " + mUsersList!!.size
    }

    override fun onUserRemoved(userName: String) {

    }
}
