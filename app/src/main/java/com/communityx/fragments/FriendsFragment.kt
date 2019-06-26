package com.communityx.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.adapters.MyAllFriendsAdapter

class FriendsFragment : Fragment() {

    @BindView(R.id.recycler_my_friends)
    internal var recyclerViewFriends: RecyclerView? = null
    @BindString(R.string.all_friends)
    internal var allFriends: String? = null

    private var myAllFriendsAdapter: MyAllFriendsAdapter? = null
    private var parentFragment: MyAllFriendsFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friends, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentFragment = getParentFragment() as MyAllFriendsFragment?

        initAllFriends()
    }

    private fun getAllFriendsList() {

    }

    private fun initAllFriends() {
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerViewFriends!!.layoutManager = linearLayoutManager
        val dividerItemDecoration =
            DividerItemDecoration(recyclerViewFriends!!.context, linearLayoutManager.orientation)
        recyclerViewFriends!!.addItemDecoration(dividerItemDecoration)

        myAllFriendsAdapter = MyAllFriendsAdapter(activity!!, viewModel.getFakeAllMyFriends().getValue())
        recyclerViewFriends!!.adapter = myAllFriendsAdapter
    }
}
