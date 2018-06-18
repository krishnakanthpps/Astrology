package com.anagha.astrology;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import dashboard.FragmentDrawer;
import dashboard.LogOutActivity;
import models.NavDrawerItem;
import utilitys.BaseActivity;
import utilitys.WebCall;

/**
 * Created by harsha on 4/17/2018.
 */

public class SelectedSignDashBoard extends BaseActivity implements AdapterView.OnItemClickListener,
        FragmentDrawer.FragmentDrawerListener {
    private Context _ctx = SelectedSignDashBoard.this;
    private FragmentDrawer drawerFragment;
    private Toolbar mToolbar;
    private TextView loginUserNameTv;
    static SharedPreferences sPrefs;

    WebView myWebView;
    ProgressBar progress;

    // private ViewPager viewPager;
    //private TabLayout tabLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        initUI();
        uiListener();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.selected_sign;
    }

    private void uiListener() {
        //fab.setOnClickListener(this);
    }

    private void initUI() {
        sPrefs = _ctx.getSharedPreferences(WebCall.SharedPreference_Name, 0);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.selectedsign_fragment_navigation_drawer);
        drawerFragment.setUp(R.id.selectedsign_fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.selectedsign_drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);


        myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.getSettings().setJavaScriptEnabled(true);

       /* viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        setupViewPager(viewPager);*/
        myWebView.loadUrl("http://192.168.2.12/astro/backend/web/index.php?r=site/dashas");

        loginUserNameTv = (TextView) findViewById(R.id.loggedinusernameTV);
        loginUserNameTv.setText(sPrefs.getString("userName", null));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    /*side menu clicked position based navigations*/
    private void displayView(int position) {
        Intent webabout;
        Toast mytoast;
        NavDrawerItem o = drawerFragment.getSelectedItem(position);
        switch (position) {
            case 0://nav_item_home
                break;
            case 1://DailyHoroscope
               /* webabout = new Intent(this, AboutusActivity.class);
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);*/
                mytoast = Toast.makeText(getApplicationContext(), "Welcome : DailyHoroscope", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 2://Your chart Details
               /* webabout = new Intent(this, WebPageActivity.class);
                webabout.putExtra("help_app_detail", "openToall_contactus");
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);*/

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Your chart Details", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 3://Personality
               /* webabout = new Intent(this, WebPageActivity.class);
                webabout.putExtra("help_app_detail", "openToall_about");
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);*/

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Personality", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 4://Career
               /* Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mulugu"));
                startActivity(browserIntent);*/

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Career", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 5://Money Matters
                //shareIt();
                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Money Matters", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 6://Health
               // shareIt();
                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Health", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 7://Marriage
                //shareIt();
                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Marriage", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 8://Childrean
                //shareIt();
                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Childrean", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 9://Logout
                webabout = new Intent(this, LogOutActivity.class);
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void shareIt() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Download free Sri Astrology app on google play store");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Download free Sri Astrology app on google play store Sri Astrology app Android Link.");
        startActivity(Intent.createChooser(sharingIntent, "Share Sri Astrology App using"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
    }

   /* private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DailyFragment(), "Daily");
        adapter.addFragment(new WeeklyFragment(), "Weekly");
        adapter.addFragment(new MonthlyFragment(), "Monthly");
        adapter.addFragment(new YearlyFragment(), "Yearly");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }*/

   /* class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }*/

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
           /* if (url.startsWith("mailto:")) {
                MailTo mt = MailTo.parse(url);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{mt.getTo()});
                i.putExtra(Intent.EXTRA_SUBJECT, mt.getSubject());
                i.putExtra(Intent.EXTRA_TEXT, mt.getBody());
                _ctx.startActivity(i);
                return true;
            }*/
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.GONE);
            SelectedSignDashBoard.this.progress.setProgress(100);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress.setVisibility(View.VISIBLE);
            SelectedSignDashBoard.this.progress.setProgress(0);
            super.onPageStarted(view, url, favicon);
        }
    }

}
