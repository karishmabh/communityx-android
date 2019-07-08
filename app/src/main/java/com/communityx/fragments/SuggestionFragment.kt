package com.communityx.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.adapters.SuggestionAdapter
import com.communityx.models.logout.LogoutResponse
import com.communityx.models.myallies.all_allies.AllAlliesResponse
import com.communityx.models.myallies.all_allies.DataX
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.fragment_suggestion.*

class SuggestionFragment : Fragment() , SuggestionAdapter.IOnAddFriendClicked {

    private var suggestionAdapter: SuggestionAdapter? = null
    private var listSuggestions: ArrayList<DataX> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_suggestion, container, false)
        ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllSuggestionsList()
        Utils.hideSoftKeyboard(activity)
    }

    private fun getAllSuggestionsList() {
        progress_bar?.visibility = View.VISIBLE
        DataManager.getAlliesSuggestions(activity!!, object : ResponseListener<AllAlliesResponse> {
            override fun onSuccess(response: AllAlliesResponse) {
                progress_bar?.visibility = View.GONE
                var data = response.data
                var userData = data.get(0).data

                listSuggestions.clear()
                listSuggestions = userData as ArrayList<DataX>

                updateTabListCount(userData.size)

                if (isAdded) {
                    changeVisibility(listSuggestions.isNullOrEmpty())
                    setAdapter()
                }
            }

            override fun onError(error: Any) {
                progress_bar?.visibility = View.GONE
                Utils.showError(activity, frame_root, error)
            }
        })
    }

    fun updateTabListCount(count: Int) {
        if (isAdded) {
            (parentFragment as MyAllFriendsFragment)?.updateTabText(2, count)
        }
    }

    fun changeVisibility(isEmpty: Boolean) {
        text_no_record.visibility = if (isEmpty) View.VISIBLE else View.GONE
        recycler_suggestion_list.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    fun setAdapter() {
        recycler_suggestion_list?.layoutManager = LinearLayoutManager(activity)
        suggestionAdapter = SuggestionAdapter(listSuggestions, activity!!, this)
        recycler_suggestion_list?.adapter = suggestionAdapter
    }

    private fun sendInvitation(id: String, position: Int) {
        progress_bar?.visibility = View.VISIBLE
        DataManager.sendInvitation(activity!!, id, object : ResponseListener<LogoutResponse> {
            override fun onSuccess(response: LogoutResponse) {
                if (isAdded) {
                    progress_bar?.visibility = View.GONE
                    suggestionAdapter?.updateItem(position)

                    updateTabListCount(suggestionAdapter?.itemCount!!)
                    changeVisibility(suggestionAdapter?.itemCount == 0)

                    Toast.makeText(activity, "Request Sent successfully!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(error: Any) {
                progress_bar?.visibility = View.GONE
                Utils.showError(activity, frame_root, error)
            }
        })
    }

    override fun sendInvitationTapped(id: String, position: Int) {
        sendInvitation(id, position)
    }
}
