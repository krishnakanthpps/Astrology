package com.anagha.astrology;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import utilitys.BaseActivity;
import utilitys.NetworkConnectionCheck;
import utilitys.WebCall;

public class NotificationDetails extends BaseActivity {
    private Context _ctx = NotificationDetails.this;
    private Toolbar mToolbar;
    static SharedPreferences sPrefs;
    TextView notificationTitle;
    TextView notificationDescription;
    TextView notificationDate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Details");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initUI();
        uiListener();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.notification_details;
    }

    private void uiListener() {
    }

    private void initUI() {
        sPrefs = _ctx.getSharedPreferences(WebCall.SharedPreference_Name, 0);
        notificationTitle = (TextView)findViewById(R.id.notificationTitle_TV);
        notificationDescription = (TextView)findViewById(R.id.notificationdetails_TV);
        notificationDate = (TextView)findViewById(R.id.notificationdate_TV);


        notificationTitle.setText(Html.fromHtml(getIntent().getStringExtra("notificationTitle")));
        notificationDescription.setText(Html.fromHtml(getIntent().getStringExtra("notificationDescription")));
        notificationDate.setText(Html.fromHtml(getIntent().getStringExtra("notificationDated")));



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
    }
}

