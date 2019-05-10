package com.communityx.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.fragments.CreateEventFragment;
import com.communityx.fragments.CreatePostContentFragment;
import com.communityx.fragments.CrowdFundingFragment;
import com.communityx.fragments.PostReportingFragment;
import com.communityx.utils.CustomToolBarUtils;
import com.communityx.utils.Utils;

public class CreatePostActivity extends AppCompatActivity {

    @BindView(R.id.image_content)
    ImageView imageContent;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.image_reporting)
    ImageView imageReporting;
    @BindView(R.id.text_reporting)
    TextView textReporting;
    @BindView(R.id.image_crowdfunding)
    ImageView imageCrowdfunding;
    @BindView(R.id.text_crowdfunding)
    TextView textCrowdfunding;
    @BindView(R.id.image_create_event)
    ImageView imageEvent;
    @BindView(R.id.text_create_event)
    TextView textEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        ButterKnife.bind(this);
        setUpToobar();
        postContentClicked();
    }

    private void setUpToobar() {
        CustomToolBarUtils customToolBarUtils = new CustomToolBarUtils(this);
        customToolBarUtils.setLogoIcon(R.drawable.ic_praise_back_arrow);
        customToolBarUtils.setTitle(R.string.create_post);
        customToolBarUtils.getImageLogo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.image_content, R.id.text_content})
    void postContentClicked() {
        Utils.replaceFragment(this, new CreatePostContentFragment(), false, "fragment_post");
        makeActiveText(textContent,imageContent,R.drawable.ic_create_post_content_select);
    }

    @OnClick({R.id.text_reporting, R.id.image_reporting})
    void postReportingClicked() {
        Utils.replaceFragment(this, new PostReportingFragment(), false, "fragment_post");
        makeActiveText(textReporting,imageReporting,R.drawable.ic_create_post_reporting_select);
    }

    @OnClick({R.id.text_crowdfunding, R.id.image_crowdfunding})
    void postCrowdfundingClicked() {
        Utils.replaceFragment(this, new CrowdFundingFragment(), false, "fragment_post");
        makeActiveText(textCrowdfunding,imageCrowdfunding,R.drawable.ic_create_post_crowdfunding_select);
    }

    @OnClick({R.id.text_create_event, R.id.image_create_event})
    void createEventClicked() {
        Utils.replaceFragment(this, new CreateEventFragment(), false, "fragment_post");
        makeActiveText(textEvent,imageEvent,R.drawable.ic_create_post_event_select);
    }

    @OnClick({R.id.text_create_event, R.id.image_create_event})
    void postCreateEvent() {
        makeActiveText(textEvent,imageEvent,R.drawable.ic_create_post_event_select);
    }

    private void makeActiveText(TextView activeText, ImageView activeImage, int imgRes) {

        imageContent.setImageResource(R.drawable.ic_create_post_content_deselect);
        textContent.setTextColor(getResources().getColor(R.color.colorLightGrey));
        imageReporting.setImageResource(R.drawable.ic_create_post_reporting_deselect);
        textReporting.setTextColor(getResources().getColor(R.color.colorLightGrey));
        imageCrowdfunding.setImageResource(R.drawable.ic_create_post_crowdfunding_deselect);
        textCrowdfunding.setTextColor(getResources().getColor(R.color.colorLightGrey));
        imageEvent.setImageResource(R.drawable.ic_create_post_event_deselect);
        textEvent.setTextColor(getResources().getColor(R.color.colorLightGrey));

        activeImage.setImageResource(imgRes);
        activeText.setTextColor(getResources().getColor(R.color.colorBlackTitle));
    }
}
