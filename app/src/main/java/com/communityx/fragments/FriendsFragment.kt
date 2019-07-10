package com.communityx.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_friends.progress_bar
import kotlinx.android.synthetic.main.fragment_sign_up_professional.*
import kotlinx.android.synthetic.main.view_search.*
import java.util.*

class FriendsFragment : Fragment() {

    private val TRIGGER_AUTO_COMPLETE = 100
    private var myAllFriendsAdapter: MyAllFriendsAdapter? = null
    private lateinit var handler: Handler
    private val AUTO_COMPLETE_DELAY : Long = 30

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_friends, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllFriendsList("")

        edit_search.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE)
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE, AUTO_COMPLETE_DELAY)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        handler = Handler(object : Handler.Callback {
            override fun handleMessage(msg: Message): Boolean {
                if (msg.what === TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(edit_search.text)) {
                        getAllFriendsList(edit_search.text.toString())
                    } else {
                        getAllFriendsList("")
                    }
                }
                return false
            }
        })
    }

    fun getAllFriendsList(query : String) {
        progress_bar?.visibility = View.VISIBLE
        DataManager.getAllAllies(activity!!, query,  object: ResponseListener<AllAlliesResponse> {
            override fun onSuccess(response: AllAlliesResponse) {
                progress_bar?.visibility = View.GONE
                var data = response.data
                var userData = data[0].data
                sortList(userData)

                if (userData.isNotEmpty()) {
                    (parentFragment as MyAllFriendsFragment).updateTabText(0, userData.size)

                    if (isAdded) {
                        text_no_record.visibility = if (userData.isNotEmpty()) View.GONE else View.VISIBLE
                        recycler_my_friends.visibility = if (userData.isNotEmpty()) View.VISIBLE else View.GONE
                        initAllFriends(userData)
                    }
                } else {
                    if (isAdded) {
                        if (query == "") {
                            edit_search.visibility = View.GONE
                            recycler_my_friends.visibility = View.GONE
                            text_no_record.visibility = View.VISIBLE
                        } else {
                            recycler_my_friends.visibility = View.GONE
                            text_no_record.visibility = View.VISIBLE
                            text_no_record.text = "No allies found"
                        }
                    }
                }
            }

            override fun onError(error: Any) {
                progress_bar?.visibility = View.GONE
                Utils.showError(activity, constraint_top, error)
            }
        })
    }

    private fun sortList(dataX: List<DataX>) {
        val compareByFirstName = { o1: DataX, o2: DataX -> o1.name.capitalize().compareTo(o2.name.capitalize()) }
        Collections.sort(dataX, compareByFirstName)
    }

    private fun initAllFriends(dataX: List<DataX>) {

        if (myAllFriendsAdapter != null) {
            val linearLayoutManager = LinearLayoutManager(activity)
            recycler_my_friends?.layoutManager = linearLayoutManager

            myAllFriendsAdapter = MyAllFriendsAdapter(activity!!, dataX)
            recycler_my_friends?.adapter = myAllFriendsAdapter
        } else {
            val linearLayoutManager = LinearLayoutManager(activity)
            recycler_my_friends?.layoutManager = linearLayoutManager

            val dividerItemDecoration = DividerItemDecoration(activity, linearLayoutManager.orientation)
            recycler_my_friends?.addItemDecoration(dividerItemDecoration)

            myAllFriendsAdapter = MyAllFriendsAdapter(activity!!, dataX)
            recycler_my_friends?.adapter = myAllFriendsAdapter
        }
    }
}
