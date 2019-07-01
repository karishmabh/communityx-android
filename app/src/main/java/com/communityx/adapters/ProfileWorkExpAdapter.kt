package com.communityx.adapters

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.transition.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.activity.AddExperienceActivity
import com.communityx.database.fakemodels.ProfileAboutModel
import com.communityx.models.profile.Education
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import java.util.ArrayList

class ProfileWorkExpAdapter (private val mContext: Context, private val list: List<Education>) : RecyclerView.Adapter<ProfileWorkExpAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var bool_work_exp : Boolean = false
    private var bool_club : Boolean = false
    private var bool_education : Boolean = false
    private var bool_interest : Boolean = false

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
       return ViewHolder(mInflater.inflate(R.layout.item_profile_info, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindData(list[position])

        viewHolder.imageEdit.setOnClickListener {
            if (viewHolder.textHeading.text == "Work Experience") {
                val intent = Intent(mContext, AddExperienceActivity::class.java)
                mContext.startActivity(intent)
            }
        }
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.constraint_root)
        lateinit var constraintLayout: ConstraintLayout
        @BindView(R.id.text_heading)
        lateinit var textHeading: TextView
        @BindView(R.id.text_title)
        lateinit var textTitle: TextView
        @BindView(R.id.text_subtitle)
        lateinit var textSubTitle: TextView
        @BindView(R.id.text_duration)
        lateinit var textDuration: TextView
        @BindView(R.id.image_logo)
        lateinit var imageLogo: ImageView
        @BindView(R.id.image_edit)
        lateinit var imageEdit: ImageView
        @BindView(R.id.view_gradient)
        lateinit var viewGradient: View

        init {
            ButterKnife.bind(this, itemView)
        }

        open fun bindData(education: Education) {

            if (education.datatype == "club" && !bool_club) {
                textHeading.text = "Club"
                bool_club = true
                viewGradient.visibility = View.VISIBLE
            } else if (education.datatype == "club" && bool_club) {
                hideVisibility()
            }

            if (education.datatype == "edu" && !bool_education) {
                textHeading.text = "Education"
                bool_education = true
            } else if (education.datatype == "edu" && bool_education) {
                hideVisibility()
            }

            if (education.datatype == "interest" && !bool_interest) {
                textHeading.text = "Interest"
                bool_interest = true
                viewGradient.visibility = View.VISIBLE
            } else if (education.datatype == "interest" && bool_interest) {
                hideVisibility()
            }

            if (education.datatype == "we" && !bool_work_exp) {
                textHeading.text = "Work Experience"
                bool_work_exp = true
                viewGradient.visibility = View.VISIBLE
            } else if (education.datatype == "we" && bool_work_exp) {
                hideVisibility()
            }

            textTitle.text = education.name

            if (education.role != null)
                textSubTitle.text = education.role
            else
                textSubTitle.visibility = View.GONE
        }

        fun hideVisibility() {
            textHeading.visibility = View.GONE
            imageEdit.visibility = View.GONE
        }
    }
}