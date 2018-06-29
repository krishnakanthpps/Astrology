package com.anagha.astrology;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import dashboard.FragmentDrawer;
import models.Userdetailview;
import retrofit2.Response;
import retrofitrelated.ProfileViewResultResponse;
import utilitys.BaseActivity;
import utilitys.WebCall;

/**
 * Created by harsha on 6/5/2018.
 */

public class UserProfileUpdateView extends BaseActivity {
    private Context _ctx = UserProfileUpdateView.this;
    private Toolbar mToolbar;
    static SharedPreferences sPrefs;

    private TextView userprofile_update_userNameTV;
    private TextView userprofile_update_userEmailTV;
    private TextView userprofile_update_userMobileTV;
    private TextView userprofile_update_dateofbirthTV;
    private TextView userprofile_update_timeofbirthTV;

    private EditText userprofile_update_userFNET;
    private EditText userprofile_update_userLET;
    private EditText userprofile_update_address_line_oneET;
    private EditText userprofile_update_cityET;
    private EditText userprofile_update_stateET;
    private EditText userprofile_update_countryET;
    Userdetailview shareresponse;

    @Override
    protected int getLayoutResource() {
        return R.layout.profile_update;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initUI();
        uiListener();
    }

    private void uiListener() {

    }

    private void initUI() {
        sPrefs = _ctx.getSharedPreferences(WebCall.SharedPreference_Name, 0);


        userprofile_update_userNameTV = (TextView) findViewById(R.id.userprofile_update_userNameTV);
        userprofile_update_userEmailTV = (TextView) findViewById(R.id.userprofile_update_userEmailTV);
        userprofile_update_userMobileTV = (TextView) findViewById(R.id.userprofile_update_userMobileTV);
        userprofile_update_dateofbirthTV = (TextView) findViewById(R.id.userprofile_update_dateofbirthTV);
        userprofile_update_timeofbirthTV = (TextView) findViewById(R.id.userprofile_update_timeofbirthTV);

        userprofile_update_userFNET = (EditText) findViewById(R.id.userprofile_update_userFNET);
        userprofile_update_userLET = (EditText) findViewById(R.id.userprofile_update_userLET);
        userprofile_update_address_line_oneET = (EditText) findViewById(R.id.userprofile_update_address_line_oneET);
        userprofile_update_cityET = (EditText) findViewById(R.id.userprofile_update_cityET);
        userprofile_update_stateET = (EditText) findViewById(R.id.userprofile_update_stateET);
        userprofile_update_countryET = (EditText) findViewById(R.id.userprofile_update_countryET);
        //user_profile_view_countryTV = (EditText) findViewById(R.id.user_profile_view_countryTV);

        String res = getIntent().getStringExtra("profileView");

        Gson gson = new Gson();
        Type type = new TypeToken<Userdetailview>() {
        }.getType();
        shareresponse = gson.fromJson(res, type);

        userprofile_update_userNameTV.setText(shareresponse.getUsername());
        userprofile_update_userEmailTV.setText(shareresponse.getEmail());
        userprofile_update_userMobileTV.setText(shareresponse.getMobile());
        userprofile_update_dateofbirthTV.setText(shareresponse.getDateofbirth());
        userprofile_update_timeofbirthTV.setText(shareresponse.getTimeofbirth());

        userprofile_update_userFNET.setText(shareresponse.getFirst_name());
        userprofile_update_userLET.setText(shareresponse.getLast_name());
        userprofile_update_address_line_oneET.setText(shareresponse.getAddress());
        userprofile_update_cityET.setText(shareresponse.getCurrent_city());
        userprofile_update_stateET.setText(shareresponse.getCurrent_state());
        userprofile_update_countryET.setText(shareresponse.getCurrent_country());




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit_feed_back_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_menu_submit:
               /* Intent userProfileEdit = new Intent(_ctx, UserProfileUpdateView.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(element);
                userProfileEdit.putExtra("myjson", myJson);
                startActivity(userProfileEdit);
                overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);*/
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
