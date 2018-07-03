package com.anagha.astrology;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import utilitys.BaseActivity;
import utilitys.NetworkConnectionCheck;
import utilitys.WebCall;

public class MyChartDetailsActivity extends BaseActivity {
    public Context _ctx = MyChartDetailsActivity.this;
    static SharedPreferences sPrefs;
    private Toolbar mToolbar;

    @Override
    protected int getLayoutResource() {
        return R.layout.option_mychart_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(getIntent().getStringExtra("bhaavaam"));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initUI();
        uiListener();

    }

    private void initUI() {
        sPrefs = getSharedPreferences(WebCall.SharedPreference_Name, 0);


        if (NetworkConnectionCheck.checkInternetConnection(_ctx)) {
            //userBhaavaasView();
        } else {
            new WebCall(_ctx).DialogForWifi_Enable_CloseDialog(_ctx.getString(R.string.internet_enable), _ctx.getString(R.string.internet_enable_message), R.drawable.warning_red);
        }


    }

    private void uiListener() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
    }
}
