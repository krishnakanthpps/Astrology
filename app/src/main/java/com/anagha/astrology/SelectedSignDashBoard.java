package com.anagha.astrology;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import dashboard.Login;
import models.ChakrasResult;
import models.NavDrawerItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofitrelated.APIService;
import retrofitrelated.APIUrl;
import retrofitrelated.LoginResult;
import utilitys.BaseActivity;
import utilitys.NetworkConnectionCheck;
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

    private TextView nameTV;
    private TextView starTV;
    private TextView moonsignTV;

    private TextView navaamsa_DOBTV;
    private TextView navaamsa_TimeTV;
    private TextView navaamsa_ariesTV;
    private TextView navaamsa_taurusTV;
    private TextView navaamsa_geminiTV;
    private TextView navaamsa_cancerTV;
    private TextView navaamsa_leoTV;
    private TextView navaamsa_virgoTV;
    private TextView navaamsa_libraTV;
    private TextView navaamsa_scorpioTV;
    private TextView navaamsa_sagittariusTV;
    private TextView navaamsa_capricornTV;
    private TextView navaamsa_aquariesTV;
    private TextView navaamsa_piscesTV;


    private TextView janmaamsa_DOBTV;
    private TextView janmaamsa_TimeTV;
    private TextView janmaamsa_oneTV;
    private TextView janmaamsa_twoTV;
    private TextView janmaamsa_threeTV;
    private TextView janmaamsa_fourTV;
    private TextView janmaamsa_fiveTV;
    private TextView janmaamsa_sixTV;
    private TextView janmaamsa_sevenTV;
    private TextView janmaamsa_eightTV;
    private TextView janmaamsa_nineTV;
    private TextView janmaamsa_tenTV;
    private TextView janmaamsa_elevenTV;
    private TextView janmaamsa_tweleveTV;

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

        loginUserNameTv = (TextView) findViewById(R.id.loggedinusernameTV);
        loginUserNameTv.setText(sPrefs.getString("userName", null));


        myWebView.loadUrl("http://expertwebworx.in/astro/backend/web/index.php?r=site/dashas&id=" + Integer.parseInt(sPrefs.getString("userid", null)));


        nameTV = (TextView) findViewById(R.id.nameTV);
        starTV = (TextView) findViewById(R.id.starTV);
        moonsignTV = (TextView) findViewById(R.id.moonsignTV);

        nameTV.setText(sPrefs.getString("userName", null));
        navaamsa_DOBTV = (TextView) findViewById(R.id.navaamsaDOB);
        navaamsa_TimeTV = (TextView) findViewById(R.id.navaamsaDOBTIMETV);

        /* private TextView navaamsa_ariesTV;
    private TextView navaamsa_taurusTV;
    private TextView navaamsa_geminiTV;
    private TextView navaamsa_cancerTV;
    private TextView navaamsa_leoTV;
    private TextView navaamsa_virgoTV;
    private TextView navaamsa_libraTV;
    private TextView navaamsa_scorpioTV;
    private TextView navaamsa_sagittariusTV;
    private TextView navaamsa_capricornTV;
    private TextView navaamsa_aquariesTV;
    private TextView navaamsa_piscesTV;*/


        navaamsa_ariesTV = (TextView) findViewById(R.id.ariesTV);
        navaamsa_taurusTV = (TextView) findViewById(R.id.taurusTV);
        navaamsa_geminiTV = (TextView) findViewById(R.id.geminiTV);
        navaamsa_cancerTV = (TextView) findViewById(R.id.cancerTV);
        navaamsa_leoTV = (TextView) findViewById(R.id.leoTV);
        navaamsa_virgoTV = (TextView) findViewById(R.id.virgoTV);
        navaamsa_libraTV = (TextView) findViewById(R.id.libraTV);
        navaamsa_scorpioTV = (TextView) findViewById(R.id.scorpioTV);
        navaamsa_sagittariusTV = (TextView) findViewById(R.id.sagittariusTV);
        navaamsa_capricornTV = (TextView) findViewById(R.id.capricornTV);
        navaamsa_aquariesTV = (TextView) findViewById(R.id.aquariusTV);
        navaamsa_piscesTV = (TextView) findViewById(R.id.piscesTV);


        janmaamsa_DOBTV = (TextView) findViewById(R.id.janmaamsaDOBTV);
        janmaamsa_TimeTV = (TextView) findViewById(R.id.janmaamsaDOBTIMETV);

        janmaamsa_oneTV = (TextView) findViewById(R.id.oneTV);
        janmaamsa_twoTV = (TextView) findViewById(R.id.twoTV);
        janmaamsa_threeTV = (TextView) findViewById(R.id.threeTV);
        janmaamsa_fourTV = (TextView) findViewById(R.id.fourTV);
        janmaamsa_fiveTV = (TextView) findViewById(R.id.fiveTV);
        janmaamsa_sixTV = (TextView) findViewById(R.id.sixTV);
        janmaamsa_sevenTV = (TextView) findViewById(R.id.sevenTV);
        janmaamsa_eightTV = (TextView) findViewById(R.id.eightTV);
        janmaamsa_nineTV = (TextView) findViewById(R.id.nineTV);
        janmaamsa_tenTV = (TextView) findViewById(R.id.tenTV);
        janmaamsa_elevenTV = (TextView) findViewById(R.id.elevenTV);
        janmaamsa_tweleveTV = (TextView) findViewById(R.id.tweleveTV);


        if (NetworkConnectionCheck.checkInternetConnection(_ctx)) {
            userChakrasView();
        } else {
            new WebCall(_ctx).DialogForWifi_Enable_CloseDialog(_ctx.getString(R.string.internet_enable), _ctx.getString(R.string.internet_enable_message), R.drawable.warning_red);
        }
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
               /* webabout = new Intent(this, BhaavasActivity.class);
                webabout.putExtra("bhaavaam", "");
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);*/
                mytoast = Toast.makeText(getApplicationContext(), "Welcome : DailyHoroscope", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 2://Your chart Details
                Intent mychart = new Intent(this, MyChartDetailsActivity.class);
                mychart.putExtra("bhaavaam", "MyChart Details");
                startActivity(mychart);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Your chart Details", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 3://Personality
                webabout = new Intent(this, BhaavasActivity.class);
                //webabout.putExtra("bhaavaam", "self");
                webabout.putExtra("bhaavaam", "Personality");
                webabout.putExtra("bhaavaam_no", "1");
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);

               /* mytoast = Toast.makeText(getApplicationContext(), "Welcome : Personality", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();*/
                break;
            case 4://Career
                webabout = new Intent(this, BhaavasActivity.class);
                //webabout.putExtra("bhaavaam", "career");
                webabout.putExtra("bhaavaam", "Career");
                webabout.putExtra("bhaavaam_no", "11");
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);


                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Career", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 5://Money Matters
                webabout = new Intent(this, BhaavasActivity.class);
                //webabout.putExtra("bhaavaam", "savings");
                webabout.putExtra("bhaavaam", "Money Matters");
                webabout.putExtra("bhaavaam_no", "12");
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Money Matters", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 6://Health
                webabout = new Intent(this, BhaavasActivity.class);
                //webabout.putExtra("bhaavaam", "health");
                webabout.putExtra("bhaavaam", "Health");
                webabout.putExtra("bhaavaam_no", "9");
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Health", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 7://Marriage
                webabout = new Intent(this, BhaavasActivity.class);
                //webabout.putExtra("bhaavaam", "marriage");
                webabout.putExtra("bhaavaam", "Marriage");
                webabout.putExtra("bhaavaam_no", "8");
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Marriage", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 8://Childrean
                webabout = new Intent(this, BhaavasActivity.class);
                //webabout.putExtra("bhaavaam", "children");
                webabout.putExtra("bhaavaam", "Children");
                webabout.putExtra("bhaavaam_no", "6");
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Children", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 9://Remidies
               /* webabout = new Intent(this, BhaavasActivity.class);
                //webabout.putExtra("bhaavaam", "children");
                webabout.putExtra("bhaavaam", "Children");
                webabout.putExtra("bhaavaam_no", "6");
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);*/

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Remidies", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 10://Logout
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


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
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


    private void userChakrasView() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //building retrofit object
        // .baseUrl(APIUrl.BASE_URL)
        //.baseUrl("http://192.168.2.65/astro-apii/backend/web/")
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);


        //Defining the user object as we need to pass it with the call
        //User user = new User(name, email, password, gender);

        //defining the call
        Call<ChakrasResult> call = service.loadChakras(sPrefs.getString("userid", null));

        //calling the api
        call.enqueue(new Callback<ChakrasResult>() {
            @Override
            public void onResponse(Call<ChakrasResult> call, Response<ChakrasResult> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                //displaying the message from the response as toast
                if (response.body().getStatus().toString().equalsIgnoreCase("success")) {
                    //here save success result object values and navigate to dashboard
                  /*  Toast mytoast = Toast.makeText(getApplicationContext(), "Welcome :" + response.body().getMessage(), Toast.LENGTH_SHORT);
                    mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                    mytoast.show();*/

                    starTV.setText(response.body().getRasi());
                    moonsignTV.setText(response.body().getRasi());
                    try {
                        //Log.i("aries",response.body().getResult().getNavamsha_Aries());
                        navaamsa_ariesTV.setText(response.body().getResult().getNavamsha_Aries());
                        //Log.i("taurus",response.body().getResult().getNavamsha_Taurus());
                        navaamsa_taurusTV.setText(response.body().getResult().getNavamsha_Taurus());
                        //Log.i("gemini",response.body().getResult().getNavamsha_Gemini());
                        navaamsa_geminiTV.setText(response.body().getResult().getNavamsha_Gemini());
                        //Log.i("cancer",response.body().getResult().getNavamsha_Cancer());
                        navaamsa_cancerTV.setText(response.body().getResult().getNavamsha_Cancer());
                        //Log.i("leo",response.body().getResult().getNavamsha_Leo());
                        navaamsa_leoTV.setText(response.body().getResult().getNavamsha_Leo());
                        //Log.i("virgo",response.body().getResult().getNavamsha_Virgo());
                        navaamsa_virgoTV.setText(response.body().getResult().getNavamsha_Virgo());
                        //Log.i("libra",response.body().getResult().getNavamsha_Libra());
                        navaamsa_libraTV.setText(response.body().getResult().getNavamsha_Libra());
                        //Log.i("scorpio",response.body().getResult().getNavamsha_Scorpio());
                        navaamsa_scorpioTV.setText(response.body().getResult().getNavamsha_Scorpio());
                        //Log.i("sagittarius",response.body().getResult().getNavamsha_Saggitarius());
                        navaamsa_sagittariusTV.setText(response.body().getResult().getNavamsha_Saggitarius());
                        //Log.i("capricorn",response.body().getResult().getNavamsha_Capricon());
                        navaamsa_capricornTV.setText(response.body().getResult().getNavamsha_Capricon());
                        //Log.i("aquaries",response.body().getResult().getNavamsha_Aquarius());
                        navaamsa_aquariesTV.setText(response.body().getResult().getNavamsha_Aquarius());
                        //Log.i("pisces",response.body().getResult().getNavamsha_Pisces());
                        navaamsa_piscesTV.setText(response.body().getResult().getNavamsha_Pisces());
                        //Log.i("aries",response.body().getResult().getNavamsha_Pisces());


                        navaamsa_DOBTV.setText(response.body().getDateofbirth());
                        navaamsa_TimeTV.setText(response.body().getTimeofbirth() + " ");

                        janmaamsa_oneTV.setText(response.body().getResult().getJanma_Aries());
                        janmaamsa_twoTV.setText(response.body().getResult().getJanma_Taurus());
                        janmaamsa_threeTV.setText(response.body().getResult().getJanma_Gemini());
                        janmaamsa_fourTV.setText(response.body().getResult().getJanma_Cancer());
                        janmaamsa_fiveTV.setText(response.body().getResult().getJanma_Leo());
                        janmaamsa_sixTV.setText(response.body().getResult().getJanma_Virgo());
                        janmaamsa_sevenTV.setText(response.body().getResult().getJanma_Libra());
                        janmaamsa_eightTV.setText(response.body().getResult().getJanma_Scorpio());
                        janmaamsa_nineTV.setText(response.body().getResult().getJanma_Saggitarius());
                        janmaamsa_tenTV.setText(response.body().getResult().getJanma_Capricon());
                        janmaamsa_elevenTV.setText(response.body().getResult().getJanma_Aquarius());
                        janmaamsa_tweleveTV.setText(response.body().getResult().getJanma_Pisces());

                        janmaamsa_DOBTV.setText(response.body().getDateofbirth());
                        janmaamsa_TimeTV.setText(response.body().getTimeofbirth() + " ");

                    } catch (NullPointerException npe) {
                        Log.i("DashBoard Chakras", npe.getMessage());
                    }
                } else {
                    Toast mytoast = Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG);
                    mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                    mytoast.show();
                }
            }

            @Override
            public void onFailure(Call<ChakrasResult> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "error :" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
