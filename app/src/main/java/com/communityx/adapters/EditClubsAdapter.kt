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
import com.communityx.activity.EditClubActivity
import com.communityx.models.signup.CauseData
import com.communityx.utils.AppConstant
import kotlinx.android.synthetic.main.activity_edit_club.*

class EditClubsAdapter(private val mArrayList: ArrayList<CauseData>?, private val mActvity: Activity) : AppConstant,
        RecyclerView.Adapter<EditClubsAdapter.EventHolder>() {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mActvity)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EventHolder {
        val view = mLayoutInflater.inflate(R.layout.item_suggest_cause, viewGroup, false)
        return EventHolder(view)
    }

    override fun onBindViewHolder(eventHolder: EventHolder, i: Int) {
        eventHolder.textClubName.text = mArrayList?.get(i)?.cause_name
        eventHolder.textClubRole.text = mArrayList?.get(i)?.cause_role

        eventHolder.imageCross.setOnClickListener {
            (mActvity as EditClubActivity).causesDataList.removeAt(i)
            (mActvity).clubsAdapter?.notifyDataSetChanged()

            if (mActvity.causesDataList.size == 0) {
                mActvity.relative_no_data.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        if (mArrayList != null) {
            return mArrayList.size
        } else {
            return 0
        }
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
}
