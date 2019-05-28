package com.communityx.activity

import android.app.Dialog
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.Window
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import butterknife.BindDrawable
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.MultipleImagesAdapter
import com.communityx.utils.AnimationUtils
import com.communityx.utils.CustomToolBarHelper
import kotlinx.android.synthetic.main.activity_event_detail.*

import java.util.ArrayList

class EventDetailActivity : AppCompatActivity() {

    internal var isLike = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        ButterKnife.bind(this)
        setUpToobar()
        setRecyclerImages()
    }

    private fun setUpToobar() {
        val customToolBarUtils = CustomToolBarHelper(this)
        customToolBarUtils.setTitle(R.string.event_detail)
        customToolBarUtils.enableBackPress()
    }

    private fun setRecyclerImages() {
        val imagesArrayList = ArrayList<String>()
        imagesArrayList.add("1")
        imagesArrayList.add("1")
        imagesArrayList.add("1")
        imagesArrayList.add("1")

        recycler_going!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_interested!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recycler_going!!.adapter = MultipleImagesAdapter(this, imagesArrayList)
        recycler_interested!!.adapter = MultipleImagesAdapter(this, imagesArrayList)
    }

    @OnClick(R.id.button_interested)
    internal fun openBottomSheet() {
        openDialog()
    }

    @OnClick(R.id.image_like)
    internal fun likeTapped() {
        image_like!!.setImageResource(if (isLike) R.drawable.ic_my_community_like_deselect else R.drawable.ic_my_community_like_select)
        isLike = !isLike
        AnimationUtils.pulse(image_like, 1, 300)
    }

    fun openDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = dialog.window
        window!!.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        window.setContentView(R.layout.dialog_interested)
        window.setGravity(Gravity.RIGHT or Gravity.BOTTOM)
        dialog.setCancelable(true)

        val radioInterested = dialog.findViewById<RadioButton>(R.id.radio_button_1)
        val radioGoing = dialog.findViewById<RadioButton>(R.id.radio_button_2)
        val radioNotInterested = dialog.findViewById<RadioButton>(R.id.radio_button_3)

        radioInterested.isChecked = true
        setTint(radioGoing, resources.getColor(R.color.colorLightGrey), resources.getDrawable(R.drawable.ic_interested_popup_going_select))
        setTint(radioNotInterested, resources.getColor(R.color.colorLightGrey), resources.getDrawable(R.drawable.ic_interested_popup_not_interested_select))

        radioInterested.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                setTint(radioInterested, resources.getColor(R.color.colorAccent), resources.getDrawable(R.drawable.drawable_star))
            } else {
                setTint(radioInterested, resources.getColor(R.color.colorLightGrey), resources.getDrawable(R.drawable.drawable_star))
            }
        }

        radioGoing.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                setTint(radioGoing, resources.getColor(R.color.colorAccent), resources.getDrawable(R.drawable.ic_interested_popup_going_select))
            } else {
                setTint(radioGoing, resources.getColor(R.color.colorLightGrey), resources.getDrawable(R.drawable.ic_interested_popup_going_select))
            }
        }

        radioNotInterested.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                setTint(radioNotInterested, resources.getColor(R.color.colorAccent), resources.getDrawable(R.drawable.ic_interested_popup_not_interested_select))
            } else {
                setTint(radioNotInterested, resources.getColor(R.color.colorLightGrey), resources.getDrawable(R.drawable.ic_interested_popup_not_interested_select))
            }
        }

        dialog.show()
    }

    private fun setTint(radioButton: RadioButton, color: Int, drawable1: Drawable?) {
        var drawable = drawable1
        drawable = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTint(drawable!!, color)
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)

        radioButton.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }
}
