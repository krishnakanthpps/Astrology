package dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.anagha.astrology.R;

import java.util.ArrayList;
import java.util.List;

import adapters.SettingOptionsAdapter;
import models.SettingsOptions;
import utilitys.BaseActivity;
import utilitys.WebCall;

/**
 * Created by support on 10/9/2015.
 */
public class Help_AppDetails extends BaseActivity implements View.OnClickListener {
    private Context _context = Help_AppDetails.this;
    private Toolbar mToolbar;
    private ListView helpListView;
    private TextView androidVersionNameTV;
    FloatingActionButton fabHelpAppDetails_share;
    SettingOptionsAdapter user_vendor_options_adapter;
    List<SettingsOptions> helpApp_options_List;
    static SharedPreferences sPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("Support and help");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        onInitUi();
        onUiListener();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.app_details;
    }

    public void onInitUi() {
        helpListView = (ListView) findViewById(R.id.helplist);
        sPrefs = getSharedPreferences(WebCall.SharedPreference_Name, 0);
        androidVersionNameTV = (TextView) findViewById(R.id.appversion);
        try {
            androidVersionNameTV.setText("App Version: " + _context.getPackageManager().getPackageInfo(_context.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException pnfe) {
            Log.e("NameNotFound GBFragment", pnfe.toString());
        }
        fabHelpAppDetails_share = (FloatingActionButton) findViewById(R.id.fab);
        helpApp_options_List = new ArrayList<SettingsOptions>();
        /*        helpApp_options_List.add(new SettingsOptions(1, "About GD", R.drawable.about_app));
         */
        helpApp_options_List.add(new SettingsOptions(1, "About Author", R.drawable.info_android));
        helpApp_options_List.add(new SettingsOptions(2, "FAQ's", R.drawable.info_android));
/*
        helpApp_options_List.add(new SettingsOptions(7, "Add Store", R.drawable.info_android));
*/
        helpApp_options_List.add(new SettingsOptions(3, "Support", R.drawable.info_android));
        helpApp_options_List.add(new SettingsOptions(4, "Contact Us", R.drawable.info_android));
        helpApp_options_List.add(new SettingsOptions(5, "Feedback", R.drawable.info_android));
        helpApp_options_List.add(new SettingsOptions(6, "Privacy Notice", R.drawable.info_android));
        helpApp_options_List.add(new SettingsOptions(7, "Terms of use", R.drawable.info_android));

        /*   helpApp_options_List.add(new UserVendorOptionModel(6, "Change Password", R.drawable.change_password));*/

        user_vendor_options_adapter = new SettingOptionsAdapter(_context, (ArrayList<SettingsOptions>) helpApp_options_List);
        helpListView.setAdapter(user_vendor_options_adapter);
    }


    public void onUiListener() {
        fabHelpAppDetails_share.setOnClickListener(this);
        helpListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentRegisterScreen;
                switch (position) {
                    case 0://about gd
                       /* intentRegisterScreen = new Intent(_context, HelpItemsWebViewActivity.class);
                        intentRegisterScreen.putExtra("help_app_detail", "openToall_about");
                        intentRegisterScreen.putExtra("from_notification_click","from_fcm_messagingserviceclasss_creen");

                        startActivity(intentRegisterScreen);
                        Help_AppDetails.this.overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
        */                break;
                    case 1://faqs
                        /*intentRegisterScreen = new Intent(_context, HelpItemsWebViewActivity.class);
                        intentRegisterScreen.putExtra("help_app_detail", "openToall_faqs");
                        intentRegisterScreen.putExtra("from_notification_click","from_fcm_messagingserviceclasss_creen");

                        startActivity(intentRegisterScreen);
                        Help_AppDetails.this.overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                 */       break;
                    case 2:// addstore
                       /* if (NetWorkConnectionCheck.checkInternetConnection(_context)) {
                            if (getBooleanForLoginStatus("loggedin")) {
                                Intent addStoreActivity = new Intent(_context, AddStoreActivity.class);
                                startActivity(addStoreActivity);
                                Help_AppDetails.this.overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(_context, R.style.AppCompatAlertDialogStyle);
                                builder.setCancelable(false);
                                builder.
                                        setTitle(_context.getString(R.string.signin)).
                                        setMessage(_context.getString(R.string.signinmessage)).
                                        setIcon(R.drawable.warning_red).
                                        setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                        } else {
                            new WebCall(_context).DialogForWifi_Enable_CloseDialog(_context.getString(R.string.internet_enable),_context.getString(R.string.internet_enable_message), R.drawable.warning_red);

                        }*/
                        break;
                    case 3://support
                       /* if (NetWorkConnectionCheck.checkInternetConnection(_context)) {
                            if (getBooleanForLoginStatus("loggedin")) {
                                Intent intentSupport = new Intent(_context, Support.class);
                                startActivity(intentSupport);
                                overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(_context, R.style.AppCompatAlertDialogStyle);
                                builder.setCancelable(false);
                                builder.
                                        setTitle(_context.getString(R.string.signin)).
                                        setMessage(_context.getString(R.string.signinmessage)).
                                        setIcon(R.drawable.warning_red).
                                        setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                        } else {
                            new WebCall(_context).DialogForWifi_Enable_CloseDialog(_context.getString(R.string.internet_enable),_context.getString(R.string.internet_enable_message), R.drawable.warning_red);
                        }*/

                        break;
                    case 4://Contact Us
                        /*intentRegisterScreen = new Intent(_context, HelpItemsWebViewActivity.class);
                        intentRegisterScreen.putExtra("help_app_detail", "openToall_contactus");
                        intentRegisterScreen.putExtra("from_notification_click","from_fcm_messagingserviceclasss_creen");

                        startActivity(intentRegisterScreen);
                        Help_AppDetails.this.overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
             */           break;
                    /*case 5://Change Password
                        if (NetWorkConnectionCheck.checkInternetConnection(_context)) {
                            if (getBooleanForLoginStatus("loggedin")) {
                                Intent changePassword = new Intent(_context, ChangePassword.class);
                                startActivity(changePassword);
                                Help_AppDetails.this.overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(_context, R.style.AppCompatAlertDialogStyle);
                                builder.setCancelable(false);
                                builder.setTitle("Login").setMessage("For this Option Login Manditory").setIcon(R.drawable.warning_red).
                                        setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                        } else {
                            new WebCall(_context).DialogForWifi_Enable_CloseDialog("Internet & GPS Enable", "For this Request Please enable Intenet & GPS", R.drawable.error_icon);
                        }
                        break;*/
                    case 5://feedback
                      /*  Intent intentFB = new Intent(_context, FeedBack_Activity.class);
                        startActivity(intentFB);
                        overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
*/
                        break;
                    case 6://Privacy notice
                       /* intentRegisterScreen = new Intent(_context, HelpItemsWebViewActivity.class);
                        intentRegisterScreen.putExtra("help_app_detail", "openToall_privacy");
                        intentRegisterScreen.putExtra("from_notification_click","from_fcm_messagingserviceclasss_creen");

                        startActivity(intentRegisterScreen);
                        Help_AppDetails.this.overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
*/
                        break;
                    case 7://terms and conditions
                        /*intentRegisterScreen = new Intent(_context, HelpItemsWebViewActivity.class);
                        intentRegisterScreen.putExtra("help_app_detail", "openToall_terms");
                        intentRegisterScreen.putExtra("from_notification_click","from_fcm_messagingserviceclasss_creen");

                        startActivity(intentRegisterScreen);
                        Help_AppDetails.this.overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                     */   break;
                    default:
                        break;
                }
            }
        });
    }
    private void shareIt() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Download free Grocer Deals app on google play store");
        sharingIntent.putExtra(Intent.EXTRA_TEXT,"Download free Grocer Deals app on google play store https://play.google.com/store/apps/details?id=com.eww.mshoppy Grocer Deals app Android Link.");
        startActivity(Intent.createChooser(sharingIntent, "Share our App using"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
    }

    @Override
    public void onClick(View v) {
        shareIt();
    }

    private boolean getBooleanForLoginStatus(String loggedin) {
        SharedPreferences.Editor editor = sPrefs.edit();
        return sPrefs.getBoolean("loggedin", false);
    }
}
