package com.anagha.astrology;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import models.ChakrasResult;
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

public class MyChartDetailsActivity extends BaseActivity {
    public Context _ctx = MyChartDetailsActivity.this;
    static SharedPreferences sPrefs;
    private Toolbar mToolbar;
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


    private TextView bavam_oneTV;
    private ProgressBar progressBarOne;
    private TextView bavam_twoTV;
    private ProgressBar progressBarTwo;
    private TextView bavam_threeTV;
    private ProgressBar progressBarThree;
    private TextView bavam_fourTV;
    private ProgressBar progressBarFour;
    private TextView bavam_fiveTV;
    private ProgressBar progressBarFive;
    private TextView bavam_sixTV;
    private ProgressBar progressBarSix;
    private TextView bavam_sevenTV;
    private ProgressBar progressBarSeven;
    private TextView bavam_eightTV;
    private ProgressBar progressBarEight;
    private TextView bavam_nineTV;
    private ProgressBar progressBarNine;
    private TextView bavam_tenTV;
    private ProgressBar progressBarTen;
    private TextView bavam_elevenTV;
    private ProgressBar progressBarEleven;
    private TextView bavam_tweleveTV;
    private ProgressBar progressBarTweleve;


    @Override
    protected int getLayoutResource() {
        return R.layout.option_mychart_details;
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

        nameTV = (TextView) findViewById(R.id.nameTV);
        starTV = (TextView) findViewById(R.id.starTV);
        moonsignTV = (TextView) findViewById(R.id.moonsignTV);

        nameTV.setText(sPrefs.getString("userName", null));
        navaamsa_DOBTV = (TextView) findViewById(R.id.navaamsaDOB);
        navaamsa_TimeTV = (TextView) findViewById(R.id.navaamsaDOBTIMETV);

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


        bavam_oneTV = (TextView) findViewById(R.id.bhavam_oneTV);
        progressBarOne = (ProgressBar) findViewById(R.id.bhavam_oneprogressBar);
        bavam_twoTV = (TextView) findViewById(R.id.bhavam_twoTV);
        progressBarTwo = (ProgressBar) findViewById(R.id.bhavam_twoprogressBar);
        bavam_threeTV = (TextView) findViewById(R.id.bhavam_threeTV);
        progressBarThree = (ProgressBar) findViewById(R.id.bhavam_threeprogressBar);
        bavam_fourTV = (TextView) findViewById(R.id.bhavam_fourTV);
        progressBarFour = (ProgressBar) findViewById(R.id.bhavam_fourprogressBar);
        bavam_fiveTV = (TextView) findViewById(R.id.bhavam_fiveTV);
        progressBarFive = (ProgressBar) findViewById(R.id.bhavam_fiveprogressBar);
        bavam_sixTV = (TextView) findViewById(R.id.bhavam_sixTV);
        progressBarSix = (ProgressBar) findViewById(R.id.bhavam_sixprogressBar);
        bavam_sevenTV = (TextView) findViewById(R.id.bhavam_sevenTV);
        progressBarSeven = (ProgressBar) findViewById(R.id.bhavam_sevenprogressBar);
        bavam_eightTV = (TextView) findViewById(R.id.bhavam_eightTV);
        progressBarEight = (ProgressBar) findViewById(R.id.bhavam_eightprogressBar);
        bavam_nineTV = (TextView) findViewById(R.id.bhavam_nineTV);
        progressBarNine = (ProgressBar) findViewById(R.id.bhavam_nineprogressBar);
        bavam_tenTV = (TextView) findViewById(R.id.bhavam_tenTV);
        progressBarTen = (ProgressBar) findViewById(R.id.bhavam_tenprogressBar);
        bavam_elevenTV = (TextView) findViewById(R.id.bhavam_elevenTV);
        progressBarEleven = (ProgressBar) findViewById(R.id.bhavam_elevenprogressBar);
        bavam_tweleveTV = (TextView) findViewById(R.id.bhavam_tweleveTV);
        progressBarTweleve = (ProgressBar) findViewById(R.id.bhavam_tweleveprogressBar);


        if (NetworkConnectionCheck.checkInternetConnection(_ctx)) {
            userChakrasView();
        } else {
            new WebCall(_ctx).DialogForWifi_Enable_CloseDialog(_ctx.getString(R.string.internet_enable), _ctx.getString(R.string.internet_enable_message), R.drawable.warning_red);
        }


    }

    private void uiListener() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
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
        Call<ChakrasResult> call = service.loadMyChartChakras(sPrefs.getString("userid", null));

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

                        //progressBarOne.setProgress(getDrawable(Integer.parseInt(response.body().getResult().getSelf())));
                        //progressBarOne.setProgressDrawable(getDrawable(Integer.parseInt(response.body().getResult().getSelf())),progressBarOne);

                        //personality-family
                        progressBarOne.setProgress(Integer.parseInt(response.body().getResult().getSelf()));
                        //wealth
                        progressBarTwo.setProgress(Integer.parseInt(response.body().getResult().getWealth()));
                        //family
                        progressBarThree.setProgress(Integer.parseInt(response.body().getResult().getFamily()));
                        //mother
                        progressBarFive.setProgress(Integer.parseInt(response.body().getResult().getMother()));
                        //assets
                        progressBarFour.setProgress(Integer.parseInt(response.body().getResult().getAssets()));
                        //children
                        progressBarSix.setProgress(Integer.parseInt(response.body().getResult().getChildren()));
                        //enimiesdebt
                        progressBarSeven.setProgress(Integer.parseInt(response.body().getResult().getEnemiesDebt()));
                        //marriage
                        progressBarEight.setProgress(Integer.parseInt(response.body().getResult().getMarriage()));
                        //health
                        progressBarNine.setProgress(Integer.parseInt(response.body().getResult().getHealth()));
                        //luck
                        progressBarTen.setProgress(Integer.parseInt(response.body().getResult().getLucky_Father()));
                        //career
                        progressBarEleven.setProgress(Integer.parseInt(response.body().getResult().getCareer()));
                        //savings
                        progressBarTweleve.setProgress(Integer.parseInt(response.body().getResult().getSavings()));

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
