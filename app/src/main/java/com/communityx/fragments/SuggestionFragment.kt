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
import com.communityx.models.myallies.all_allies.AllAlliesResponse
import com.communityx.models.myallies.all_allies.DataX
import com.communityx.models.signup.SignUpResponse
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.fragment_suggestion.*

class SuggestionFragment : Fragment() , SuggestionAdapter.IOnAddFriendClicked {

    private var suggestionAdapter: SuggestionAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_suggestion, container, false)
        ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllSuggestionsList()
    }

    private fun getAllSuggestionsList() {
        progress_bar?.visibility = View.VISIBLE
        DataManager.getAlliesSuggestions(activity!!, object : ResponseListener<AllAlliesResponse> {
            override fun onSuccess(response: AllAlliesResponse) {
                progress_bar?.visibility = View.GONE
                var data = response.data
                var userData = data.get(0).data

                (parentFragment as MyAllFriendsFragment)?.updateTabText(2, userData.size)
                setAdapter(userData)
            }

            override fun onError(error: Any) {
                progress_bar?.visibility = View.GONE
                Utils.showError(activity, frame_root, error)
            }
        })
    }

    fun setAdapter(dataX: List<DataX>) {
        recycler_suggestion_list?.layoutManager = LinearLayoutManager(activity)
        suggestionAdapter = SuggestionAdapter(dataX, activity!!, this)
        recycler_suggestion_list?.adapter = suggestionAdapter
    }

    private fun sendInvitation(id: String) {
        progress_bar?.visibility = View.VISIBLE
        DataManager.sendInvitation(activity!!, id, object : ResponseListener<SignUpResponse> {
            override fun onSuccess(response: SignUpResponse) {
                progress_bar?.visibility = View.GONE
                Toast.makeText(activity, "Request Sent successfully!", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Any) {
                progress_bar?.visibility = View.GONE
                Utils.showError(activity, frame_root, error)
            }
        })
    }

    override fun sendInvitationTapped(id: String) {
        sendInvitation(id)
    }
}
