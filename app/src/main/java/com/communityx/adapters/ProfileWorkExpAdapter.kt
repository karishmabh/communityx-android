package com.communityx.adapters

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.activity.AddExperienceActivity
import com.communityx.activity.EditClubActivity
import com.communityx.activity.EditEducationActivity
import com.communityx.activity.ProfileActivity
import com.communityx.models.profile.Education
import kotlinx.android.synthetic.main.activity_add_experience.*
import java.util.*

class ProfileWorkExpAdapter(private val mContext: Context, private val list: List<Education>) :
    RecyclerView.Adapter<ProfileWorkExpAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var bool_work_exp: Boolean = false
    private var bool_club: Boolean = false
    private var bool_education: Boolean = false
    private var bool_interest: Boolean = false

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(mInflater.inflate(R.layout.item_profile_info, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindData(list[position])

        viewHolder.imageEdit.setOnClickListener {
            if (list[position].datatype == "we") {
                val intent = Intent(mContext, AddExperienceActivity::class.java)
                intent.putExtra("data", list[position])
                mContext.startActivity(intent)
                (mContext as ProfileActivity).overridePendingTransition(R.anim.anim_slide_up, R.anim.anim_stay)
            }

            if (list[position].datatype == "club") {
                val intent = Intent(mContext, EditClubActivity::class.java)
                intent.putExtra("data", list as ArrayList<Education>)
                mContext.startActivity(intent)
                (mContext as ProfileActivity).overridePendingTransition(R.anim.anim_slide_up, R.anim.anim_stay)
            }

            if (list[position].datatype == "edu") {
                val intent = Intent(mContext, EditEducationActivity::class.java)
                intent.putExtra("data", list[position])
                mContext.startActivity(intent)
                (mContext as ProfileActivity).overridePendingTransition(R.anim.anim_slide_up, R.anim.anim_stay)
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
                textHeading.text = mContext.getString(R.string.string_clubs_and_organization)
                bool_club = true
                viewGradient.visibility = View.VISIBLE
            } else if (education.datatype == "club" && bool_club) {
                hideVisibilityClub()
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
                textHeading.text = mContext.getString(R.string.string_work_experience)
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
        }

        fun hideVisibilityClub() {
            textHeading.visibility = View.GONE
            imageEdit.visibility = View.GONE
        }
    }
}