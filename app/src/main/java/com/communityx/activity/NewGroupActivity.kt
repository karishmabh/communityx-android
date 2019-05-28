package com.communityx.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.GroupAdapter
import com.communityx.adapters.SelectedMembersAdapter
import com.communityx.utils.AppConstant
import kotlinx.android.synthetic.main.activity_new_group.*

import java.util.ArrayList

class NewGroupActivity : AppCompatActivity(), GroupAdapter.IUsersSelected, SelectedMembersAdapter.IUserRemoved, AppConstant {

    private var selectedMembersAdapter: SelectedMembersAdapter? = null
    private var groupAdapter: GroupAdapter? = null
    private val selectedUsersList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_group)
        ButterKnife.bind(this)

        image_next!!.hide()
        setRecyclerViewChat()
    }

    @OnClick(R.id.imageBack)
    internal fun backTapped() {
        super.onBackPressed()
    }

    @OnClick(R.id.image_next)
    internal fun proceedToNext() {
        selectedUsersList.clear()
        selectedUsersList.addAll(groupAdapter!!.selectedUsersList)

        startActivity(Intent(this, CreateGroupActivity::class.java)
                .putExtra(AppConstant.INTENT_USERLIST, selectedUsersList))
    }

    private fun setRecyclerViewChat() {
        //TODO hard codes strings

        selectedMembersAdapter = SelectedMembersAdapter(selectedUsersList, this, this)
        recycler_added_users!!.adapter = selectedMembersAdapter

        val usersList = ArrayList<String>()
        usersList.add("Anthony")
        usersList.add("Amar")
        usersList.add("Akbar")
        usersList.add("Anamika")
        usersList.add("Bthony")
        usersList.add("Bony")
        usersList.add("Cony")
        usersList.add("Cinthony")

        val linearLayoutManager = LinearLayoutManager(this)
        recycler_user_list!!.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(recycler_user_list!!.context, linearLayoutManager.orientation)
        recycler_user_list!!.addItemDecoration(dividerItemDecoration)

        groupAdapter = GroupAdapter(usersList, this, this)
        recycler_user_list!!.adapter = groupAdapter
    }

    override fun updatedUsersList(mSelectedUsers: Set<String>) {
        if (mSelectedUsers.isNotEmpty()) {
            selectedUsersList.clear()
            selectedUsersList.addAll(mSelectedUsers)

            recycler_added_users!!.visibility = View.VISIBLE
            image_next!!.show()
            recycler_added_users!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            selectedMembersAdapter!!.notifyDataSetChanged()
        } else {
            image_next!!.hide()
            recycler_added_users!!.visibility = View.GONE
        }
    }

    override fun onUserRemoved(userName: String) {
        groupAdapter!!.uncheckUser(userName, image_next)
    }
}
