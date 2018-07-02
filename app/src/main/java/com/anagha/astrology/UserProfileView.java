package com.anagha.astrology;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


import models.Userdetailview;
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

/**
 * Created by harsha on 5/31/2018.
 */

public class UserProfileView extends BaseActivity {
    private Context _ctx = UserProfileView.this;
    private Toolbar mToolbar;
    static SharedPreferences sPrefs;

    private TextView user_profile_view_userNameTV;
    private TextView user_profile_view_userEmailET;
    private TextView user_profile_view_firstNameTV;
    private TextView user_profile_view_lastNameTV;
    private TextView user_profile_view_mobile_no_one;
    private TextView user_profile_view_dateofbirthTV;
    private TextView user_profile_view_timeofbirthTV;
    private TextView user_profile_view_placeofbirthTV;
    private TextView user_profile_view_address_line_oneTV;
    private TextView user_profile_view_cityTV;
    private TextView user_profile_view_stateTV;
    private TextView user_profile_view_countryTV;
    Userdetailview shareresponse;

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
        sPrefs = _ctx.getSharedPreferences(WebCall.SharedPreference_Name, 0);


        user_profile_view_userNameTV = (TextView) findViewById(R.id.user_profile_view_userNameTV);
        user_profile_view_userEmailET = (TextView) findViewById(R.id.user_profile_view_userEmailET);
        user_profile_view_firstNameTV = (TextView) findViewById(R.id.user_profile_view_firstNameTV);
        user_profile_view_lastNameTV = (TextView) findViewById(R.id.user_profile_view_lastNameTV);
        user_profile_view_mobile_no_one = (TextView) findViewById(R.id.user_profile_view_mobile_no_one);
        user_profile_view_dateofbirthTV = (TextView) findViewById(R.id.user_profile_view_dateofbirthTV);
        user_profile_view_timeofbirthTV = (TextView) findViewById(R.id.user_profile_view_timeofbirthTV);
        user_profile_view_placeofbirthTV = (TextView) findViewById(R.id.user_profile_view_placeofbirthTV);
        user_profile_view_address_line_oneTV = (TextView) findViewById(R.id.user_profile_view_address_line_oneTV);
        user_profile_view_cityTV = (TextView) findViewById(R.id.user_profile_view_cityTV);
        user_profile_view_stateTV = (TextView) findViewById(R.id.user_profile_view_stateTV);
        user_profile_view_countryTV = (TextView) findViewById(R.id.user_profile_view_countryTV);
        //user_profile_view_userNameTV = (TextView) findViewById(R.id.user_profile_view_userNameTV);


        if (NetworkConnectionCheck.checkInternetConnection(_ctx)) {
            userProfileView();
        } else {
            new WebCall(_ctx).DialogForWifi_Enable_CloseDialog(_ctx.getString(R.string.internet_enable), _ctx.getString(R.string.internet_enable_message), R.drawable.warning_red);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (NetworkConnectionCheck.checkInternetConnection(_ctx)) {
            Intent i = new Intent(_ctx, SelectedSignDashBoard.class);
            startActivity(i);
            finishAffinity();
            overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
        } else {
            new WebCall(_ctx).DialogForWifi_Enable_CloseDialog("Internet Enable ", "For this Request Please enable Intenet-Wifi,GPS", R.drawable.error_icon);
        }

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
                String profileobj = new Gson().toJson(shareresponse);
                userProfileEdit.putExtra("profileView", profileobj);
                startActivity(userProfileEdit);
                finishAffinity();
                overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void userProfileView() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Profile...");
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

        Call<ProfileViewResultResponse> call = service.loadProfileView(sPrefs.getString("userid", null));

        //calling the api
        call.enqueue(new Callback<ProfileViewResultResponse>() {
            @Override
            public void onResponse(Call<ProfileViewResultResponse> call, Response<ProfileViewResultResponse> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                //displaying the message from the response as toast
                if (response.body().getStatus().toString().equalsIgnoreCase("success")) {
                    //here save success result object values and navigate to dashboard
                    //update sharedpreference3 with login true for everytime dashboard
                   /* Toast mytoast = Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG);
                    mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                    mytoast.show();*/
                    //Toast.makeText(getApplicationContext(),response.body().getUserDetailView().getUsername(), Toast.LENGTH_LONG).show();

                    try {
                        shareresponse = response.body().getUserDetailView();

                        user_profile_view_userNameTV.setText(response.body().getUserDetailView().getUsername());
                        user_profile_view_userEmailET.setText(response.body().getUserDetailView().getEmail());
                        user_profile_view_firstNameTV.setText(response.body().getUserDetailView().getFirst_name());
                        user_profile_view_lastNameTV.setText(response.body().getUserDetailView().getLast_name());
                        user_profile_view_mobile_no_one.setText(response.body().getUserDetailView().getMobile());
                        user_profile_view_dateofbirthTV.setText(response.body().getUserDetailView().getDateofbirth());
                        user_profile_view_timeofbirthTV.setText(response.body().getUserDetailView().getTimeofbirth());
                        user_profile_view_placeofbirthTV.setText(response.body().getUserDetailView().getPlaceofbirth());
                        user_profile_view_address_line_oneTV.setText(response.body().getUserDetailView().getAddress());
                        user_profile_view_cityTV.setText(response.body().getUserDetailView().getCurrent_city());
                        user_profile_view_stateTV.setText(response.body().getUserDetailView().getCurrent_state());
                        user_profile_view_countryTV.setText(response.body().getUserDetailView().getCurrent_country());
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
            public void onFailure(Call<ProfileViewResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "error :" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
