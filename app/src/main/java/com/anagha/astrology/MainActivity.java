package com.anagha.astrology;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONException;

import dashboard.DailyHoroscope;
import dashboard.Login;
import dashboard.Remidies;
import utilitys.BaseActivity;
import utilitys.Config;
import utilitys.WebCall;
import utilitys.WelcomeActivity;

public class MainActivity extends BaseActivity {
    public Context _context = MainActivity.this;
    static SharedPreferences sPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPrefs = getSharedPreferences(WebCall.SharedPreference_Name, 0);
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getStringExtra("screen") != null) {
            if (getLoginStatus(getResources().getString(R.string.signin_status_key))) {
                notificationRedirectionSplashScreen();
            } else {
                Intent intent = new Intent(_context, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                MainActivity.this.finish();
            }
        } else {
            navigationScreens();
        }

    }

    /*notifaication redirect activities when app closed if notification coming and clicked*/
    private void notificationRedirectionSplashScreen() {
        String screenValue = getIntent().getStringExtra("screen");
        Intent resultIntent = new Intent();

        switch (screenValue) {
            case "home":
                resultIntent = new Intent(getApplicationContext(), SelectedSignDashBoard.class);
                break;
            case "daily":
                resultIntent = new Intent(this, DailyHoroscope.class);
                resultIntent.putExtra("bhaavaam", "Daily Predictions");
                resultIntent.putExtra("call_from", "notification");
                startActivity(resultIntent);
                break;
            case "remedies":
                resultIntent = new Intent(this, Remidies.class);
                //resultIntent.putExtra("bhaavaam", "");
                resultIntent.putExtra("call_from", "notification");
                startActivity(resultIntent);
                break;
            case "notification":
                resultIntent = new Intent(this, Notifications.class);
                //resultIntent.putExtra("bhaavaam", "");
                resultIntent.putExtra("call_from", "notification");
                startActivity(resultIntent);
                break;
            default:
                break;
        }
        resultIntent.putExtra("from_notification_click", "from_notification_splash_screen");
        startActivity(resultIntent);
        overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
        this.finish();

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
