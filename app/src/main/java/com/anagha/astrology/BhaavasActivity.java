package com.anagha.astrology;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Range;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import models.Bhavaasmodel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofitrelated.APIService;
import retrofitrelated.APIUrl;
import retrofitrelated.ProfileViewResultResponse;
import utilitys.BaseActivity;
import utilitys.NetworkConnectionCheck;
import utilitys.WebCall;

public class BhaavasActivity extends BaseActivity {
    public Context _ctx = BhaavasActivity.this;
    static SharedPreferences sPrefs;
    private Toolbar mToolbar;

    private TextView regularInfoTV;
    private TextView specialInfoTV;
    private ProgressBar progressBar;

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
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        if (NetworkConnectionCheck.checkInternetConnection(_ctx)) {
            userBhaavaasView();
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

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        //Defining the user object as we need to pass it with the call
        //User user = new User(name, email, password, gender);
        //defining the call
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
                        regularInfoTV.setText(response.body().getBhaavasreg_information());
                        /*if (response.body().getHundredpercentage_information().equalsIgnoreCase(" ")) {
                            specialInfoTV.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                        } else {*/
                        specialInfoTV.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);

                        if (new Range<Integer>(0, 25).contains(Integer.parseInt(response.body().getPercentage()))) {
                            //red colored
                            progressBar.setProgress(Integer.parseInt(response.body().getPercentage()));
                            //progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressone));

                            Drawable progressDrawable = progressBar.getProgressDrawable().mutate();
                            progressDrawable.setColorFilter(R.color.progress_red, android.graphics.PorterDuff.Mode.SRC_IN);
                            progressBar.setProgressTintList(ColorStateList.valueOf(R.color.progress_red));
                            progressBar.setProgressDrawable(progressDrawable);

                        } else if (new Range<Integer>(25, 50).contains(Integer.parseInt(response.body().getPercentage()))) {
                            //green colored
                            progressBar.setProgress(Integer.parseInt(response.body().getPercentage()));
                            //progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progresstwo));
                            Drawable progressDrawable = progressBar.getProgressDrawable().mutate();
                            progressDrawable.setColorFilter(R.color.progress_yellow, android.graphics.PorterDuff.Mode.SRC_IN);
                            progressBar.setProgressTintList(ColorStateList.valueOf(R.color.progress_yellow));
                            progressBar.setProgressDrawable(progressDrawable);

                        } else if (new Range<Integer>(50, 75).contains(Integer.parseInt(response.body().getPercentage()))) {
                            //orange colored
                            progressBar.setProgress(Integer.parseInt(response.body().getPercentage()));
                            //progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressthree));
                            Drawable progressDrawable = progressBar.getProgressDrawable().mutate();
                            progressDrawable.setColorFilter(getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                            progressBar.setProgressTintList(ColorStateList.valueOf(R.color.colorPrimary));
                            progressBar.setProgressDrawable(progressDrawable);
                        } else {
                            //green colored
                            //progressBar.setBackgroundColor(getResources().getColor(R.color.progress_yellow));
                            //progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressfour));
                            progressBar.setProgress(Integer.parseInt(response.body().getPercentage()));
                            Drawable progressDrawable = progressBar.getProgressDrawable().mutate();
                            progressDrawable.setColorFilter(R.color.progress_green, android.graphics.PorterDuff.Mode.SRC_IN);
                            progressBar.setProgressTintList(ColorStateList.valueOf(R.color.progress_green));
                            //progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(_ctx, Color.GREEN), PorterDuff.Mode.SRC_IN );
                            progressBar.setProgressDrawable(progressDrawable);

                        }
                        specialInfoTV.setText(Html.fromHtml(response.body().getRegular_information() + "<br>" + response.body().getHundredpercentage_information()));
                        //}
                    } catch (NullPointerException npe) {
                        Log.i("npe", npe.getMessage().toString());
                    }
                } else {
                    Toast mytoast = Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG);
                    mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                    mytoast.show();
                    // Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Bhavaasmodel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "error :" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
    }

}