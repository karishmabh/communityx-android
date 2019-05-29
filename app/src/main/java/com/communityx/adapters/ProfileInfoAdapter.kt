package com.communityx.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.database.fakemodels.ProfileAboutModel

import java.util.ArrayList

class ProfileInfoAdapter(private val mContext: Context, private val list: List<ProfileAboutModel>) : RecyclerView.Adapter<ProfileInfoAdapter.ViewHolder>() {

    internal val NORMAL_ITEM = 0
    internal val EXPANDED_ITEM = 1
    private var isOtherProfile = false
    private var fromDetialActivity = false

    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    fun setOtherProfile(otherProfile: Boolean) {
        isOtherProfile = otherProfile
    }

    fun setFromDetialActivity(fromDetialActivity: Boolean) {
        this.fromDetialActivity = fromDetialActivity
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return if (i == EXPANDED_ITEM) {
            ExpandedViewHolder(mInflater.inflate(R.layout.item_profile_info_expanded, viewGroup, false))
        } else ViewHolder(mInflater.inflate(R.layout.item_profile_info, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val model = list[i]
        viewHolder.bindData(model)
    }

    override fun getItemCount(): Int {
        return if (fromDetialActivity) list.size else 4
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && fromDetialActivity) EXPANDED_ITEM else NORMAL_ITEM
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.text_heading)
        lateinit var textHeading: TextView
        @BindView(R.id.text_title)
        lateinit var textTitle: TextView
        @BindView(R.id.text_subtitle)
        lateinit var textSibTitle: TextView
        @BindView(R.id.text_duration)
        lateinit var textDuration: TextView
        @BindView(R.id.image_logo)
        lateinit var imageLogo: ImageView
        @BindView(R.id.image_edit)
        lateinit var imageEdit: ImageView

        private val addedAboutHeading = ArrayList<String>()

        init {
            ButterKnife.bind(this, itemView)
            validateEditIconVisibility()
        }

        private fun validateEditIconVisibility() {
            imageEdit.visibility = if (isOtherProfile) View.GONE else View.VISIBLE
        }

        open fun bindData(model: ProfileAboutModel) {
            if (!addedAboutHeading.contains(model.heading)) {
                textHeading.visibility = View.VISIBLE
                textHeading.text = model.heading
                addedAboutHeading.add(model.heading)
                validateEditIconVisibility()
            } else {
                textHeading.visibility = View.GONE
                imageEdit.visibility = View.GONE
            }
            textTitle.text = model.title
            textSibTitle.text = model.subtitle
            if (model.duration != null) {
                textDuration.text = model.duration
            }
            textDuration.visibility = if (model.duration != null) View.VISIBLE else View.GONE
            imageLogo.setImageResource(if (model.logo != -1) model.logo else R.drawable.profile_placeholder)
        }
    }

    inner class ExpandedViewHolder(itemView: View) : ViewHolder(itemView) {

        override fun bindData(model: ProfileAboutModel) {
            super.bindData(model)
        }
    }
}
