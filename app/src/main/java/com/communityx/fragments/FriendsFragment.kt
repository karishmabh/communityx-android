package com.communityx.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.adapters.MyAllFriendsAdapter
import com.communityx.models.myallies.all_allies.AllAlliesResponse
import com.communityx.models.myallies.all_allies.DataX
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.fragment_friends.*
import java.util.*

class FriendsFragment : Fragment() {

    private var myAllFriendsAdapter: MyAllFriendsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_friends, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllFriendsList()
    }

    private fun getAllFriendsList() {
        progress_bar?.visibility = View.VISIBLE
        DataManager.getAllAllies(activity!!, object: ResponseListener<AllAlliesResponse> {
            override fun onSuccess(response: AllAlliesResponse) {
                progress_bar?.visibility = View.GONE
                var data = response.data
                var userData = data.get(0).data
                sortList(userData)

                (parentFragment as MyAllFriendsFragment)?.updateTabText(0, userData.size)
                initAllFriends(userData)
            }

            override fun onError(error: Any) {
                progress_bar?.visibility = View.GONE
                Utils.showError(activity, constraint_top, error)
            }
        })
    }

    private fun sortList(dataX: List<DataX>) {
        val compareByFirstName = { o1: DataX, o2: DataX -> o1.profile.first_name.compareTo(o2.profile.first_name) }
        Collections.sort(dataX, compareByFirstName)
    }

    private fun initAllFriends(dataX: List<DataX>) {
        val linearLayoutManager = LinearLayoutManager(activity)
        recycler_my_friends?.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(activity, linearLayoutManager.orientation)
        recycler_my_friends?.addItemDecoration(dividerItemDecoration)

        myAllFriendsAdapter = MyAllFriendsAdapter(activity!!, dataX)
        recycler_my_friends?.adapter = myAllFriendsAdapter
    }
}
