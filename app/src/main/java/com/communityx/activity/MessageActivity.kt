package com.communityx.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.adapters.ChatAdapter

import java.util.ArrayList

class MessageActivity : AppCompatActivity() {

    @BindView(R.id.recycler_chat)
    internal var recyclerViewChat: RecyclerView? = null
    @BindView(R.id.edit_chat)
    internal var editChat: EditText? = null

    private var mChatList: ArrayList<String>? = null
    private var mChatAdapter: ChatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        ButterKnife.bind(this)

        setRecyclerViewChat()
        setEditChatLocationClick()
    }

    private fun setEditChatLocationClick() {
        editChat!!.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= editChat!!.right - editChat!!.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    startActivityForResult(Intent(this@MessageActivity, SendLocationActivity::class.java), 101)
                    return@OnTouchListener true
                }
            }
            false
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            try {
                val filename = data!!.extras!!.getString("bitmap")
                mChatList!!.add(filename)
                mChatAdapter!!.notifyDataSetChanged()
                recyclerViewChat!!.scrollToPosition(mChatList!!.size - 1)
            } catch (e: Exception) {

            }

        }
    }

    @OnClick(R.id.imageBack)
    internal fun backTapped() {
        super.onBackPressed()
    }

    @OnClick(R.id.image_send)
    internal fun sendMessageTapped() {
    }

    private fun setRecyclerViewChat() {
        mChatList = ArrayList()
        mChatList!!.add("S")
        mChatList!!.add("S")
        mChatList!!.add("S")
        mChatList!!.add("S")
        mChatList!!.add("d")
        mChatList!!.add("R")
        mChatList!!.add("R")
        mChatList!!.add("R")
        mChatList!!.add("R")

        recyclerViewChat!!.layoutManager = LinearLayoutManager(this)
        mChatAdapter = ChatAdapter(this@MessageActivity, mChatList)
        recyclerViewChat!!.adapter = mChatAdapter
    }

    @OnClick(R.id.topView)
    internal fun tappedToolbar() {
        startActivity(Intent(this, GroupInfoActivity::class.java))
    }
}
