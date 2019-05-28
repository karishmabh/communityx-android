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

import java.util.ArrayList

class NewGroupActivity : AppCompatActivity(), GroupAdapter.IUsersSelected, SelectedMembersAdapter.IUserRemoved, AppConstant {

    @BindView(R.id.recycler_user_list)
    internal var recyclerUsers: RecyclerView? = null
    @BindView(R.id.recycler_added_users)
    internal var recyclerAddedUsers: RecyclerView? = null
    @BindView(R.id.image_next)
    internal var imageNext: FloatingActionButton? = null

    private var selectedMembersAdapter: SelectedMembersAdapter? = null
    private var groupAdapter: GroupAdapter? = null
    private val selectedUsersList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_group)
        ButterKnife.bind(this)

        imageNext!!.hide()
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
        recyclerAddedUsers!!.adapter = selectedMembersAdapter

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
        recyclerUsers!!.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(recyclerUsers!!.context, linearLayoutManager.orientation)
        recyclerUsers!!.addItemDecoration(dividerItemDecoration)

        groupAdapter = GroupAdapter(usersList, this@NewGroupActivity, this)
        recyclerUsers!!.adapter = groupAdapter
    }

    override fun updatedUsersList(mSelectedUsers: Set<String>) {
        if (mSelectedUsers.size > 0) {
            selectedUsersList.clear()
            selectedUsersList.addAll(mSelectedUsers)

            recyclerAddedUsers!!.visibility = View.VISIBLE
            imageNext!!.show()
            recyclerAddedUsers!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            selectedMembersAdapter!!.notifyDataSetChanged()
        } else {
            imageNext!!.hide()
            recyclerAddedUsers!!.visibility = View.GONE
        }
    }

    override fun onUserRemoved(userName: String) {
        groupAdapter!!.uncheckUser(userName, imageNext)
    }
}
