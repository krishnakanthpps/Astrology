package com.anagha.astrology;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import utilitys.BaseActivity;

/**
 * Created by harsha on 5/31/2018.
 */

public class UserProfileView extends BaseActivity {
    private Context _ctx = UserProfileView.this;
    private Toolbar mToolbar;
    @Override
    protected int getLayoutResource() {
        return R.layout.profile_view;
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
        getMenuInflater().inflate(R.menu.profile_edit_menu_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_profile_edit:
                Intent userProfileEdit = new Intent(_ctx, UserProfileUpdateView.class);
                Gson gson = new Gson();
                /*String myJson = gson.toJson(element);
                userProfileEdit.putExtra("myjson", myJson);*/
                startActivity(userProfileEdit);
                overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
