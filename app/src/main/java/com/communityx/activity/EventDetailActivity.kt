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

import java.util.ArrayList

class EventDetailActivity : AppCompatActivity() {

    @BindView(R.id.recycler_going)
    internal var recyclerGoing: RecyclerView? = null
    @BindView(R.id.recycler_interested)
    internal var recyclerInterested: RecyclerView? = null
    @BindView(R.id.image_like)
    internal var imageLike: ImageView? = null

    @BindDrawable(R.drawable.drawable_star)
    internal var drawableStar: Drawable? = null
    @BindDrawable(R.drawable.ic_interested_popup_going_select)
    internal var drawableGoing: Drawable? = null
    @BindDrawable(R.drawable.ic_interested_popup_not_interested_select)
    internal var drawableNotInterested: Drawable? = null

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

        recyclerGoing!!.layoutManager = LinearLayoutManager(this@EventDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        recyclerInterested!!.layoutManager = LinearLayoutManager(this@EventDetailActivity, LinearLayoutManager.HORIZONTAL, false)

        recyclerGoing!!.adapter = MultipleImagesAdapter(this@EventDetailActivity, imagesArrayList)
        recyclerInterested!!.adapter = MultipleImagesAdapter(this@EventDetailActivity, imagesArrayList)
    }

    @OnClick(R.id.button_interested)
    internal fun openBottomSheet() {
        openDialog()
    }

    @OnClick(R.id.image_like)
    internal fun likeTapped() {
        imageLike!!.setImageResource(if (isLike) R.drawable.ic_my_community_like_deselect else R.drawable.ic_my_community_like_select)
        isLike = !isLike
        AnimationUtils.pulse(imageLike, 1, 300)
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
        setTint(radioGoing, resources.getColor(R.color.colorLightGrey), drawableGoing)
        setTint(radioNotInterested, resources.getColor(R.color.colorLightGrey), drawableNotInterested)

        radioInterested.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                setTint(radioInterested, resources.getColor(R.color.colorAccent), drawableStar)
            } else {
                setTint(radioInterested, resources.getColor(R.color.colorLightGrey), drawableStar)
            }
        }

        radioGoing.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                setTint(radioGoing, resources.getColor(R.color.colorAccent), drawableGoing)
            } else {
                setTint(radioGoing, resources.getColor(R.color.colorLightGrey), drawableGoing)
            }
        }

        radioNotInterested.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                setTint(radioNotInterested, resources.getColor(R.color.colorAccent), drawableNotInterested)
            } else {
                setTint(radioNotInterested, resources.getColor(R.color.colorLightGrey), drawableNotInterested)
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
