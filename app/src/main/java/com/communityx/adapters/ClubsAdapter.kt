package com.communityx.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.fragments.SignUpMemberOfClub
import com.communityx.models.signup.CauseData
import com.communityx.utils.AppConstant

class ClubsAdapter(private val mArrayList: List<CauseData>, private val mActvity: Activity, private val signUpMemberOfClub: SignUpMemberOfClub,
                   private val iClubCallback: IClubCallback) : AppConstant,
        RecyclerView.Adapter<ClubsAdapter.EventHolder>() {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mActvity)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EventHolder {
        val view = mLayoutInflater.inflate(R.layout.item_suggest_cause, viewGroup, false)
        return EventHolder(view)
    }

    override fun onBindViewHolder(eventHolder: EventHolder, i: Int) {
        eventHolder.textClubName.text = mArrayList[i].cause_name
        eventHolder.textClubRole.text = mArrayList[i].cause_role

        eventHolder.imageCross.setOnClickListener {
            signUpMemberOfClub.causesDataList.removeAt(i)
            signUpMemberOfClub.clubsAdapter?.notifyDataSetChanged()
            iClubCallback.onItemRemoved()
        }
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    inner class EventHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.text_suggest_cause_value)
        lateinit var textClubName: TextView
        @BindView(R.id.text_suggest_club_value)
        lateinit var textClubRole: TextView
        @BindView(R.id.image_cross)
        lateinit var imageCross: ImageView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    interface IClubCallback {
        public fun onItemRemoved()
    }
}
