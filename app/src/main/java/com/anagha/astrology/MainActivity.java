package com.anagha.astrology;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import dashboard.Login;
import utilitys.BaseActivity;
import utilitys.WebCall;
import utilitys.WelcomeActivity;

public class MainActivity extends BaseActivity {
    public Context _context = MainActivity.this;
    static SharedPreferences sPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPrefs = getSharedPreferences(WebCall.SharedPreference_Name, 0);
        navigationScreens();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    private void navigationScreens() {
        long FLASH_TIME = 2500;
        //for demoscreen already seen but nonlogin-PreloginDashboard
        if (getDemoScreensStatus(getResources().getString(R.string.app_demo_screens_visibility_boolean_status_key))) {
            //if sign is selected SelectedSignDashBoard
            //else show know your sign DashBoard

            if (getLoginStatus(getResources().getString(R.string.signin_status_key))) {
                        /* }
            if (getSignSelectedStatus(getResources().getString(R.string.know_your_sign_status_key))) {*/
                //Intent intent = new Intent(_context, SelectedSignDashBoard.class);
                Intent intent = new Intent(_context, SelectedSignDashBoard.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                MainActivity.this.finish();
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Intent intent = new Intent(_context, DashBoard.class);
                        Intent intent = new Intent(_context, Login.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                        MainActivity.this.finish();
                    }
                }, FLASH_TIME);
            }
            /* if (getLoginStatus(getResources().getString(R.string.app_login_status_key))) {
                Intent intent = new Intent(_context, DashBoard.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                MainActivity.this.finish();
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(_context, DashBoard.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                        MainActivity.this.finish();
                    }
                }, FLASH_TIME);
            }*/

        } else {
            //for demo screens
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(_context, WelcomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                    MainActivity.this.finish();
                }
            }, FLASH_TIME);
        }
    }

    private boolean getDemoScreensStatus(String demoScreenVisibleStatus) {
        return sPrefs.getBoolean(demoScreenVisibleStatus, false);
    }

    private boolean getSignSelectedStatus(String knowSignStatus) {
        return sPrefs.getBoolean(knowSignStatus, false);
    }

    private boolean getLoginStatus(String loginStatus) {
        return sPrefs.getBoolean(loginStatus, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}
