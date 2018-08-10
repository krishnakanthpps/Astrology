package com.anagha.astrology;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.concurrent.TimeUnit;

import models.Bhavaasmodel;
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

public class BhaavasActivity extends BaseActivity {
    public Context _ctx = BhaavasActivity.this;
    static SharedPreferences sPrefs;
    private Toolbar mToolbar;

    private TextView regularInfoTV;
    private TextView specialInfoTV;
    private TextView progressTV;
    private TextView staticTV;
    private TextView lagnaResultTV;
    private CircularProgressBar circularProgressBar;

    @Override
    protected int getLayoutResource() {
        return R.layout.menu_item_detail_layout;
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
        regularInfoTV = (TextView) findViewById(R.id.regularTV);
        specialInfoTV = (TextView) findViewById(R.id.specialTV);
        progressTV = (TextView) findViewById(R.id.progressTV);
        staticTV = (TextView) findViewById(R.id.staticTV);
        lagnaResultTV = (TextView) findViewById(R.id.lagnaResultTV);
        lagnaResultTV.setVisibility(View.GONE);
        circularProgressBar = (CircularProgressBar) findViewById(R.id.progressBarCircle);
        if (NetworkConnectionCheck.checkInternetConnection(_ctx)) {
            try {
                userBhaavaasView();
            } catch (NullPointerException npe) {
                Toast.makeText(_ctx, "Invalid Details", Toast.LENGTH_LONG).show();
            }
        } else {
            new WebCall(_ctx).DialogForWifi_Enable_CloseDialog(_ctx.getString(R.string.internet_enable), _ctx.getString(R.string.internet_enable_message), R.drawable.warning_red);
        }


    }

    private void uiListener() {

    }

    private void userBhaavaasView() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Bhaavaas...");
        progressDialog.show();


        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        //Defining the user object as we need to pass it with the call
        //User user = new User(name, email, password, gender);
        //defining the call
        try {
            Call<Bhavaasmodel> call = service.loadBhaavas(sPrefs.getString("userid", null), getIntent().getStringExtra("bhaavaam_no"));

            //calling the api
            call.enqueue(new Callback<Bhavaasmodel>() {
                @SuppressLint("ResourceType")
                @Override
                public void onResponse(Call<Bhavaasmodel> call, Response<Bhavaasmodel> response) {
                    //hiding progress dialog
                    progressDialog.dismiss();
                    //displaying the message from the response as toast
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        try {
                           // regularInfoTV.setText(Html.fromHtml("<b>This section of your horoscope also influences following aspects of your life:</b><br><br><p>" + response.body().getBhaavasreg_information() + "</p>"));
                            regularInfoTV.setText(Html.fromHtml("<font color='blue'>This section of your horoscope also influences following aspects of your life:</font></b><p>" + response.body().getBhaavasreg_information()));

                            specialInfoTV.setVisibility(View.VISIBLE);
                            progressTV.setVisibility(View.VISIBLE);
                            circularProgressBar.setVisibility(View.VISIBLE);
                            staticTV.setVisibility(View.VISIBLE);
                            circularProgressBar.setProgressBarWidth(20);
                            circularProgressBar.setBackgroundProgressBarWidth(15);
                            progressTV.setText(response.body().getPercentage() + "/100");
                            staticTV.setText(Html.fromHtml("<b>Your  <font color='red'>" + getIntent().getStringExtra("bhaavaam") + " </font> chart score is:</b><br>"));
                            if (response.body().getLagna_result().length() > 0) {
                                lagnaResultTV.setText(Html.fromHtml("<font color='blue'>General predictions as per the ascendent you are born in are:</font><br><p>" + response.body().getLagna_result() + "</p>"));
                                lagnaResultTV.setVisibility(View.VISIBLE);
                            } else {
                                lagnaResultTV.setVisibility(View.GONE);
                            }
                            if (Integer.parseInt(response.body().getPercentage()) >= 0 && Integer.parseInt(response.body().getPercentage()) <= 25) {
                                //red colored
                                circularProgressBar.setColor(ContextCompat.getColor(_ctx, R.color.progress_red));
                                circularProgressBar.setBackgroundColor(ContextCompat.getColor(_ctx, R.color.greyhint));
                                int animationDuration = 2500; // 2500ms = 2,5s
                                circularProgressBar.setProgressWithAnimation(Integer.parseInt(response.body().getPercentage()), animationDuration); // Default duration = 1500ms
                           /* circularProgressBar.setTooltipText("dad");
                            circularProgressBar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            circularProgressBar.setColor(ContextCompat.getColor(_ctx, R.color.greyhint));*/
                            } else if (Integer.parseInt(response.body().getPercentage()) >= 25 && Integer.parseInt(response.body().getPercentage()) <= 50) {
                                //green colored
                                circularProgressBar.setColor(ContextCompat.getColor(_ctx, R.color.colorAccent));
                                circularProgressBar.setBackgroundColor(ContextCompat.getColor(_ctx, R.color.greyhint));
                                int animationDuration = 2500; // 2500ms = 2,5s
                                circularProgressBar.setProgressWithAnimation(Integer.parseInt(response.body().getPercentage()), animationDuration); // Default duration = 1500ms

                            } else if (Integer.parseInt(response.body().getPercentage()) >= 50 && Integer.parseInt(response.body().getPercentage()) <= 75) {
                                //orange colored
                                circularProgressBar.setColor(ContextCompat.getColor(_ctx, R.color.colorPrimary));
                                circularProgressBar.setBackgroundColor(ContextCompat.getColor(_ctx, R.color.greyhint));
                                int animationDuration = 2500; // 2500ms = 2,5s
                                circularProgressBar.setProgressWithAnimation(Integer.parseInt(response.body().getPercentage()), animationDuration); // Default duration = 1500ms

                            } else {
                                //green colored
                                circularProgressBar.setColor(ContextCompat.getColor(_ctx, R.color.progress_green));
                                circularProgressBar.setBackgroundColor(ContextCompat.getColor(_ctx, R.color.greyhint));
                                int animationDuration = 2500; // 2500ms = 2,5s
                                circularProgressBar.setProgressWithAnimation(Integer.parseInt(response.body().getPercentage()), animationDuration); // Default duration = 1500ms

                            }
                            /*     specialInfoTV.setText(Html.fromHtml("<b><font color='blue'>What Your Horoscope says about your <font color='red'>" + getIntent().getStringExtra("bhaavaam") + " </font> is:</b><p>" + response.body().getRegular_information() + "</p>" +
                                    "<p>" + response.body().getHundredpercentage_information() + "</p>"));
*/
                            specialInfoTV.setText(Html.fromHtml("<font color='blue'>What Your Horoscope says about your </font><font color='red'>" + getIntent().getStringExtra("bhaavaam") + "</font><font color='blue'> is:</font><p>" + response.body().getRegular_information() + "</p>" +
                                    "<p>" + response.body().getHundredpercentage_information() + "</p>"));


                        } catch (NullPointerException npe) {
                            Log.i("npe", npe.getMessage().toString());
                        } catch (NumberFormatException nfe) {
                            Log.i("npe", nfe.getMessage().toString());
                            // Toast.makeText(_ctx,"Unable to load",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast mytoast = Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG);
                        mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                        mytoast.show();
                    }
                }

                @Override
                public void onFailure(Call<Bhavaasmodel> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "error :" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (NullPointerException npe) {
            Toast.makeText(_ctx, "Unable to load", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
    }

}
