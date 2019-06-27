package com.communityx.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.adapters.SuggestionAdapter
import com.communityx.models.myallies.invitation.AlliesInvitationResponse
import com.communityx.models.myallies.invitation.DataX
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.fragment_suggestion.*

class SuggestionFragment : Fragment() {
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
        DataManager.getAlliesSuggestions(activity!!, object : ResponseListener<AlliesInvitationResponse> {
            override fun onSuccess(response: AlliesInvitationResponse) {
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
        suggestionAdapter = SuggestionAdapter(dataX, activity!!)
        recycler_suggestion_list?.adapter = suggestionAdapter
    }
}
