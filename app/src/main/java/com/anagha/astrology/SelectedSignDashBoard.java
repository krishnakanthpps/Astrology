package com.anagha.astrology;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.PayPal;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PayPalAccountNonce;
import com.braintreepayments.api.models.PayPalRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.models.PostalAddress;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import dashboard.DailyHoroscope;
import dashboard.FragmentDrawer;
import dashboard.Help_AppDetails;
import dashboard.Remidies;
import models.ChakrasResult;
import models.NavDrawerItem;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofitrelated.APIService;
import retrofitrelated.APIUrl;
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

    String token;
    // String amount = "100.00";
    // HashMap<String, String> paramsHash;
    private int REQUEST_CODE = 1234;
    private int REQUEST_CODE_PAYPAL = 1235;

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
        myWebView.loadUrl(APIUrl.WEB_DASAS_URL + Integer.parseInt(sPrefs.getString("userid", null)));
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
                Intent dailyhoroscope = new Intent(this, DailyHoroscope.class);
                dailyhoroscope.putExtra("bhaavaam", "Daily Predictions");
                dailyhoroscope.putExtra("call_from", "main");
                startActivity(dailyhoroscope);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Daily Predictions", Toast.LENGTH_SHORT);
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

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : MyChart Details", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 3://Personality
                webabout = new Intent(this, BhaavasActivity.class);
                webabout.putExtra("bhaavaam", "Self");
                webabout.putExtra("bhaavaam_no", "1");
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Self", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 4://Career
                webabout = new Intent(this, BhaavasActivity.class);
                webabout.putExtra("bhaavaam", "Career");
                webabout.putExtra("bhaavaam_no", "10");
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
                webabout.putExtra("bhaavaam_no", "11");
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
                webabout.putExtra("bhaavaam_no", "8");
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
                webabout.putExtra("bhaavaam_no", "7");
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
                webabout.putExtra("bhaavaam_no", "5");
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Children", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 9://Remidies
                webabout = new Intent(this, Remidies.class);
                webabout.putExtra("call_from", "main");
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Remidies", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 10://Settings
                webabout = new Intent(this, Help_AppDetails.class);
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);
                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Settings", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            case 11://Notifications
                webabout = new Intent(this, Notifications.class);
                webabout.putExtra("call_from", "main");
                startActivity(webabout);
                overridePendingTransition(
                        R.anim.activity_animation_right_to_left,
                        R.anim.right_to_left);

                mytoast = Toast.makeText(getApplicationContext(), "Welcome : Notifications", Toast.LENGTH_SHORT);
                mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                mytoast.show();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
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


        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        //building retrofit object
        // .baseUrl(APIUrl.BASE_URL)
        //.baseUrl("http://192.168.2.65/astro-apii/backend/web/")
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .client(okHttpClient)
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
                    moonsignTV.setText(response.body().getMoonsign());
                    try {
                        //Log.i("aries",response.body().getResult().getNavamsha_Aries());
                        navaamsa_ariesTV.setText(Html.fromHtml(response.body().getResult().getNavamsha_Aries()));
                        //Log.i("taurus",response.body().getResult().getNavamsha_Taurus());
                        navaamsa_taurusTV.setText(Html.fromHtml(response.body().getResult().getNavamsha_Taurus()));
                        //Log.i("gemini",response.body().getResult().getNavamsha_Gemini());
                        navaamsa_geminiTV.setText(Html.fromHtml(response.body().getResult().getNavamsha_Gemini()));
                        //Log.i("cancer",response.body().getResult().getNavamsha_Cancer());
                        navaamsa_cancerTV.setText(Html.fromHtml(response.body().getResult().getNavamsha_Cancer()));
                        //Log.i("leo",response.body().getResult().getNavamsha_Leo());
                        navaamsa_leoTV.setText(Html.fromHtml(response.body().getResult().getNavamsha_Leo()));
                        //Log.i("virgo",response.body().getResult().getNavamsha_Virgo());
                        navaamsa_virgoTV.setText(Html.fromHtml(response.body().getResult().getNavamsha_Virgo()));
                        //Log.i("libra",response.body().getResult().getNavamsha_Libra());
                        navaamsa_libraTV.setText(Html.fromHtml(response.body().getResult().getNavamsha_Libra()));
                        //Log.i("scorpio",response.body().getResult().getNavamsha_Scorpio());
                        navaamsa_scorpioTV.setText(Html.fromHtml(response.body().getResult().getNavamsha_Scorpio()));
                        //Log.i("sagittarius",response.body().getResult().getNavamsha_Saggitarius());
                        navaamsa_sagittariusTV.setText(Html.fromHtml(response.body().getResult().getNavamsha_Saggitarius()));
                        //Log.i("capricorn",response.body().getResult().getNavamsha_Capricon());
                        navaamsa_capricornTV.setText(Html.fromHtml(response.body().getResult().getNavamsha_Capricon()));
                        //Log.i("aquaries",response.body().getResult().getNavamsha_Aquarius());
                        navaamsa_aquariesTV.setText(Html.fromHtml(response.body().getResult().getNavamsha_Aquarius()));
                        //Log.i("pisces",response.body().getResult().getNavamsha_Pisces());
                        navaamsa_piscesTV.setText(Html.fromHtml(response.body().getResult().getNavamsha_Pisces()));
                        //Log.i("aries",response.body().getResult().getNavamsha_Pisces());


                        navaamsa_DOBTV.setText(response.body().getDateofbirth());
                        navaamsa_TimeTV.setText(response.body().getTimeofbirth() + " ");

                        janmaamsa_oneTV.setText(Html.fromHtml(response.body().getResult().getJanma_Aries()));
                        janmaamsa_twoTV.setText(Html.fromHtml(response.body().getResult().getJanma_Taurus()));
                        janmaamsa_threeTV.setText(Html.fromHtml(response.body().getResult().getJanma_Gemini()));
                        janmaamsa_fourTV.setText(Html.fromHtml(response.body().getResult().getJanma_Cancer()));
                        janmaamsa_fiveTV.setText(Html.fromHtml(response.body().getResult().getJanma_Leo()));
                        janmaamsa_sixTV.setText(Html.fromHtml(response.body().getResult().getJanma_Virgo()));
                        janmaamsa_sevenTV.setText(Html.fromHtml(response.body().getResult().getJanma_Libra()));
                        janmaamsa_eightTV.setText(Html.fromHtml(response.body().getResult().getJanma_Scorpio()));
                        janmaamsa_nineTV.setText(Html.fromHtml(response.body().getResult().getJanma_Saggitarius()));
                        janmaamsa_tenTV.setText(Html.fromHtml(response.body().getResult().getJanma_Capricon()));
                        janmaamsa_elevenTV.setText(Html.fromHtml(response.body().getResult().getJanma_Aquarius()));
                        janmaamsa_tweleveTV.setText(Html.fromHtml(response.body().getResult().getJanma_Pisces()));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* switch (item.getItemId()) {
            case R.id.action_menu_logout:
               // new getToken().execute();
               *//* DropInRequest dropInRequest = new DropInRequest().clientToken(token);
                startActivityForResult(dropInRequest.getIntent(this),REQUEST_CODE);
                *//*
                //PayPal.requestOneTimePayment(mBraintreeFragment, getPayPalRequest("1.00"));

                break;
            default:
                break;
        }*/
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                //String deviceData = result.getDeviceData();
                String strNonce = nonce.getNonce();
                HashMap paramsHash = new HashMap<>();
                paramsHash.put("amount", "10");
                paramsHash.put("nonce", strNonce);
                sendPayments(paramsHash);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(_ctx, "User Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("EDMT_ERROR", error.toString());
            }
        } else if (requestCode == REQUEST_CODE_PAYPAL) {
            if (resultCode == RESULT_OK) {
                try {

                    *//*DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                    PaymentMethodNonce nonce = result.getPaymentMethodNonce();*//*

                    BraintreeFragment mBraintreeFragment = BraintreeFragment.newInstance(this, token);
                    //PayPal.authorizeAccount(mBraintreeFragment);
                    // mBraintreeFragment.addListener(braintreeSuccessListener);
                    //mBraintreeFragment.addListener(braintreeErrorListener);
                    //mBraintreeFragment.addListener(braintreeCancelListener);
                    //mBraintreeFragment.addListener(braintreeCancelListener);
                    //PayPal.requestBillingAgreement(mBraintreeFragment, new PayPalRequest());
                    PayPalRequest request = new PayPalRequest("100")
                            .currencyCode("USD");
                    final HashMap paramsHash = new HashMap<>();
                    paramsHash.put("amount", request.getAmount());
                    PayPal.requestBillingAgreement(mBraintreeFragment, request);
                    //PayPalAccountNonce payPalAccountNonce;
                    mBraintreeFragment.addListener(new PaymentMethodNonceCreatedListener() {
                        @Override
                        public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
                            //PayPalAccountNonce payPalAccountNonce = (PayPalAccountNonce) paymentMethodNonce;
                            //String nonce = payPalAccountNonce.getNonce();
                            String nonce = paymentMethodNonce.getNonce();
                            if (paymentMethodNonce instanceof PayPalAccountNonce) {
                                PayPalAccountNonce payPalAccountNonce = (PayPalAccountNonce) paymentMethodNonce;
                                // Access additional information
                                String email = payPalAccountNonce.getEmail();
                                String firstName = payPalAccountNonce.getFirstName();
                                String lastName = payPalAccountNonce.getLastName();
                                String phone = payPalAccountNonce.getPhone();
                                // See PostalAddress.java for details
                                PostalAddress billingAddress = payPalAccountNonce.getBillingAddress();
                                PostalAddress shippingAddress = payPalAccountNonce.getShippingAddress();
                            }
                            paramsHash.put("nonce", nonce);
                        }
                    });
                    //PaymentMethodNonce nonce = listener.get();
                    //PayPalAccountNonce payPalAccountNonce = (PayPalAccountNonce)listener;
                    //String nonce = payPalAccountNonce.getNonce();
                    sendPayments(paramsHash);
                } catch (InvalidArgumentException e) {
                    // There was an issue with your authorization string.
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(_ctx, "User Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("EDMT_ERROR", error.toString());
            }
        } else {
            Toast.makeText(_ctx, "other option selected", Toast.LENGTH_LONG).show();
        }

    }

    PaymentMethodNonceCreatedListener listener = new PaymentMethodNonceCreatedListener() {
        @Override
        public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
            paymentMethodNonce.getNonce();
        }
    };

    private void sendPayments(final HashMap<String, String> paramsHashLoc) {
        RequestQueue queue = Volley.newRequestQueue(SelectedSignDashBoard.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIUrl.API_CHECK_OUT,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(_ctx, "res:" + response.toString(), Toast.LENGTH_LONG).show();
                        if (response.toString().contains("success")) {
                            Toast.makeText(_ctx, "Trasaction successful", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(_ctx, "Trasaction failed", Toast.LENGTH_LONG).show();

                        }
                        Log.d("EDMT_Log", response.toString());
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("EDMT_ERROR", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (paramsHashLoc == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key : params.keySet()) {
                    params.put(key, paramsHashLoc.get(key));
                }
                return paramsHashLoc;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }*/

}
