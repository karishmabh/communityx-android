package com.communityx.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatSpinner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import butterknife.BindView
import butterknife.ButterKnife
import com.communityx.R
import kotlinx.android.synthetic.main.fragment_sign_up_member_of_club.*

import java.util.Objects

class SignUpMemberOfClub : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_member_of_club, null)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner_club_name!!.adapter = ArrayAdapter(
            Objects.requireNonNull<Context>(context),
            R.layout.item_member_of_club,
            R.id.text_item,
            arrayOf(
                "Amnesty International",
                "Best Buddies",
                "Rotary Interact",
                "DECA",
                "Model United Nations",
                "Gay/Straight Alliance",
                "National Business Honor Society",
                "National Technology Honor Society",
                "Mock Trial Club",
                "Student Government",
                "Yoga Club",
                "Animal Rights Organizatio",
                "Entrepreneur Club",
                "Human Rights Club"
            )
        )
        spinner_role!!.adapter =
            ArrayAdapter(context!!, R.layout.item_member_of_club, R.id.text_item, arrayOf("President", "President"))
    }
}
