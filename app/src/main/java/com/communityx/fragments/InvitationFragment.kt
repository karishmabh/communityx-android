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
import com.communityx.adapters.InvitationAdapter
import com.communityx.models.logout.LogoutResponse
import com.communityx.models.myallies.all_allies.AllAlliesResponse
import com.communityx.models.myallies.all_allies.DataX
import com.communityx.models.myallies.all_allies.UpdateInvitationRequest
import com.communityx.network.DataManager
import com.communityx.network.ResponseListener
import com.communityx.utils.AppConstant
import com.communityx.utils.AppConstant.ACCEPTED
import com.communityx.utils.AppConstant.REJECTED
import com.communityx.utils.Utils
import kotlinx.android.synthetic.main.fragment_invitation.*

class InvitationFragment : Fragment(), InvitationAdapter.IInvitationCallback , AppConstant {

    private var invitationAdapter: InvitationAdapter? = null
    private var listInvitation: ArrayList<DataX> =  ArrayList()

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
        progress_bar?.visibility = View.VISIBLE
        DataManager.getAlliesInvitation(activity!!, object: ResponseListener<AllAlliesResponse> {
            override fun onSuccess(response: AllAlliesResponse) {
                progress_bar?.visibility = View.GONE
                var data = response.data
                var userData = data.get(0).data

                listInvitation.clear()
                listInvitation.addAll(userData)

                changeTabCount(userData.size)
                if (isAdded) {
                    changeVisiblity(userData.isNullOrEmpty())
                    setAdapter()
                }
            }

            override fun onError(error: Any) {
                progress_bar?.visibility = View.GONE
                Utils.showError(activity, frame_root, error)
            }
        })
    }

    fun changeTabCount(count: Int) {
        if (isAdded) {
            (parentFragment as MyAllFriendsFragment).updateTabText(1, count)
        }
    }

    fun changeVisiblity(isEmpty: Boolean) {
        text_no_record.visibility = if (isEmpty) View.VISIBLE else View.GONE
        recycler_invitation_list.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    fun setAdapter() {
        recycler_invitation_list?.layoutManager = LinearLayoutManager(activity)
        invitationAdapter = InvitationAdapter(listInvitation, activity!!, this)
        recycler_invitation_list?.adapter = invitationAdapter
    }

    private fun updateInvitation(updateInvitationRequest: UpdateInvitationRequest, position: Int) {
        progress_bar?.visibility = View.VISIBLE
        DataManager.updateInvitation(activity!!, updateInvitationRequest, object : ResponseListener<LogoutResponse> {
            override fun onSuccess(response: LogoutResponse) {
                progress_bar?.visibility = View.GONE
                Toast.makeText(activity, response.data.get(0), Toast.LENGTH_LONG).show()

                invitationAdapter?.updateItem(position)
                changeTabCount(invitationAdapter?.itemCount!!)
                changeVisiblity(invitationAdapter?.itemCount == 0)
            }

            override fun onError(error: Any) {
                progress_bar?.visibility = View.GONE
                Utils.showError(activity, frame_root, error)
            }
        })
    }

    override fun onInvitationAccept(position: Int) {
        updateInvitation(UpdateInvitationRequest(listInvitation.get(position).id, ACCEPTED), position)
    }

    override fun onInvitationDeclined(position: Int) {
        updateInvitation(UpdateInvitationRequest(listInvitation.get(position).id, REJECTED), position)
    }
}
