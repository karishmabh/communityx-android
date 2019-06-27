package com.communityx.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import com.communityx.adapters.InvitationAdapter
import com.communityx.adapters.SuggestionAdapter

import java.util.ArrayList

class SuggestionFragment : Fragment() {
    private val suggestionList = ArrayList<String>()
    private var suggestionAdapter: SuggestionAdapter? = null

    @BindView(R.id.recycler_suggestion_list)
    internal var recyclerSuggestionList: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_suggestion, container, false)
        ButterKnife.bind(this, view)
        setAdapter(suggestionList)

        return view
    }

    fun setAdapter(mSuggestionList: ArrayList<String>) {
        recyclerSuggestionList!!.layoutManager = LinearLayoutManager(activity)
        suggestionAdapter = SuggestionAdapter(mSuggestionList, activity!!)
        recyclerSuggestionList!!.adapter = suggestionAdapter
    }
}
