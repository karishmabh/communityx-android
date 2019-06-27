package com.communityx.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.adapters.InvitationAdapter
import com.communityx.models.myallies.invitation.AlliesInvitationResponse
import com.communityx.models.myallies.invitation.DataX
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.fragment_invitation.*
import java.util.*

class InvitationFragment : Fragment() {
    private var invitationAdapter: InvitationAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_invitation, container, false)
        ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllInvitaionsList()
    }

    private fun getAllInvitaionsList() {
        DataManager.getAlliesInvitation(activity!!, object: ResponseListener<AlliesInvitationResponse> {
            override fun onSuccess(response: AlliesInvitationResponse) {
                var data = response.data
                var userData = data.get(0).data

                setAdapter(userData)
            }

            override fun onError(error: Any) {
                Utils.showError(activity, frame_root, error)
            }
        })
    }

    fun setAdapter(mInvitationList: List<DataX>) {
        recycler_invitation_list!!.layoutManager = LinearLayoutManager(activity)
        invitationAdapter = InvitationAdapter(mInvitationList, activity!!)
        recycler_invitation_list!!.adapter = invitationAdapter
    }
}
