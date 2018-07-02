package com.anagha.astrology;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.lang.reflect.Type;
import java.util.Calendar;

import dashboard.FragmentDrawer;
import dashboard.Register;
import models.Userdetailview;
import retrofit2.Response;
import retrofitrelated.ProfileViewResultResponse;
import utilitys.BaseActivity;
import utilitys.NetworkConnectionCheck;
import utilitys.WebCall;

/**
 * Created by harsha on 6/5/2018.
 */

public class UserProfileUpdateView extends BaseActivity implements
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {
    private Context _ctx = UserProfileUpdateView.this;
    private Toolbar mToolbar;
    static SharedPreferences sPrefs;
    InputMethodManager inputManager;

    private TextView userprofile_update_userNameTV;
    private TextView userprofile_update_userEmailTV;
    private TextView userprofile_update_userMobileTV;

    private TextView userprofile_update_dateofbirthTV;
    private TextView userprofile_update_timeofbirthTV;

    private EditText userprofile_update_userFNET;
    private EditText userprofile_update_userLET;

    private EditText userprofile_update_address_placeofbirthET;
    private EditText userprofile_update_address_line_oneET;
    private EditText userprofile_update_cityET;
    private EditText userprofile_update_stateET;
    private EditText userprofile_update_countryET;
    Userdetailview shareresponse;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day;
    Calendar calendar;
    TimePickerDialog timePickerDialog;
    int CalendarHour, CalendarMinute;

    @Override
    protected int getLayoutResource() {
        return R.layout.profile_update;
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
        //userprofile_update_dateofbirthTV.setOnClickListener(this);
        //userprofile_update_timeofbirthTV.setOnClickListener(this);
    }

    private void initUI() {
        sPrefs = _ctx.getSharedPreferences(WebCall.SharedPreference_Name, 0);

        userprofile_update_userNameTV = (TextView) findViewById(R.id.userprofile_update_userNameTV);
        userprofile_update_userEmailTV = (TextView) findViewById(R.id.userprofile_update_userEmailTV);
        userprofile_update_userMobileTV = (TextView) findViewById(R.id.userprofile_update_userMobileTV);

        userprofile_update_dateofbirthTV = (TextView) findViewById(R.id.userprofile_update_dateofbirthTV);
        userprofile_update_timeofbirthTV = (TextView) findViewById(R.id.userprofile_update_timeofbirthTV);

        userprofile_update_userFNET = (EditText) findViewById(R.id.userprofile_update_userFNET);
        userprofile_update_userLET = (EditText) findViewById(R.id.userprofile_update_userLET);
        userprofile_update_address_placeofbirthET = (EditText) findViewById(R.id.userprofile_update_placeofbirthET);
        userprofile_update_address_line_oneET = (EditText) findViewById(R.id.userprofile_update_address_line_oneET);
        userprofile_update_cityET = (EditText) findViewById(R.id.userprofile_update_cityET);
        userprofile_update_stateET = (EditText) findViewById(R.id.userprofile_update_stateET);
        userprofile_update_countryET = (EditText) findViewById(R.id.userprofile_update_countryET);
        //user_profile_view_countryTV = (EditText) findViewById(R.id.user_profile_view_countryTV);

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);


        String res = getIntent().getStringExtra("profileView");

        Gson gson = new Gson();
        Type type = new TypeToken<Userdetailview>() {
        }.getType();
        shareresponse = gson.fromJson(res, type);

        userprofile_update_userNameTV.setText(shareresponse.getUsername());
        userprofile_update_userEmailTV.setText(shareresponse.getEmail());
        userprofile_update_userMobileTV.setText(shareresponse.getMobile());
        userprofile_update_dateofbirthTV.setText(shareresponse.getDateofbirth());
        userprofile_update_timeofbirthTV.setText(shareresponse.getTimeofbirth());

        userprofile_update_userFNET.setText(shareresponse.getFirst_name());
        userprofile_update_userLET.setText(shareresponse.getLast_name());
        userprofile_update_address_placeofbirthET.setText(shareresponse.getPlaceofbirth());
        userprofile_update_address_line_oneET.setText(shareresponse.getAddress());
        userprofile_update_cityET.setText(shareresponse.getCurrent_city());
        userprofile_update_stateET.setText(shareresponse.getCurrent_state());
        userprofile_update_countryET.setText(shareresponse.getCurrent_country());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit_feed_back_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_menu_submit:
                if (!validations(userprofile_update_userFNET.getText().toString(),
                        userprofile_update_userLET.getText().toString(),
                        userprofile_update_address_placeofbirthET.getText().toString(),
                        userprofile_update_address_line_oneET.getText().toString(),
                        userprofile_update_cityET.getText().toString(),
                        userprofile_update_stateET.getText().toString(),
                        userprofile_update_countryET.getText().toString()
                )) {
                    if (NetworkConnectionCheck.checkInternetConnection(_ctx)) {

                        //userProfileUpdate();

                    } else {
                        new WebCall(_ctx).DialogForWifi_Enable_CloseDialog(_ctx.getString(R.string.internet_enable), _ctx.getString(R.string.internet_enable_message), R.drawable.warning_red);
                    }
                }
                inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validations(String firstname, String lastname,String placeofbirth, String addressone, String city, String state, String country) {
        if (TextUtils.isEmpty(firstname)) {
            new WebCall(_ctx).EmptyDialog("Required", "Enter First Name", R.drawable.warning_red);
            return true;
        }
        if (TextUtils.isEmpty(lastname)) {
            new WebCall(_ctx).EmptyDialog("Required", "Enter Last Name", R.drawable.warning_red);
            return true;
        }
        if (!WebCall.isEmailValid(placeofbirth)) {
            new WebCall(_ctx).EmptyDialog("Required", "Enter Place Of Birth", R.drawable.warning_red);
            return true;
        }
        if (!WebCall.isEmailValid(addressone)) {
            new WebCall(_ctx).EmptyDialog("Required", "Enter Address One", R.drawable.warning_red);
            return true;
        }
        if (TextUtils.isEmpty(city)) {
            new WebCall(_ctx).EmptyDialog("Required", "Enter City", R.drawable.warning_red);
            return true;
        }
        if (TextUtils.isEmpty(state)) {
            new WebCall(_ctx).EmptyDialog("Required", "Enter State", R.drawable.warning_red);
            return true;
        }
        if (TextUtils.isEmpty(country)) {
            new WebCall(_ctx).EmptyDialog("Required", "Enter Country", R.drawable.warning_red);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userprofile_update_dateofbirthTV:
                datePickerDialog = DatePickerDialog.newInstance(UserProfileUpdateView.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#009688"));
                datePickerDialog.setTitle("Select DateOfBirth From DatePicker");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                break;
            case R.id.userprofile_update_timeofbirthTV:
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);
                timePickerDialog = TimePickerDialog.newInstance(UserProfileUpdateView.this, CalendarHour, CalendarMinute, true);
                timePickerDialog.setThemeDark(false);
                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Toast.makeText(UserProfileUpdateView.this, "Time Not Selected", Toast.LENGTH_SHORT).show();
                    }
                });
                timePickerDialog.show(getFragmentManager(), "Material Design TimePicker Dialog");
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int UpdatedMonth = Month + 1;
        String date = "Selected Date : " + Day + "-" + UpdatedMonth + "-" + Year;
        Toast.makeText(UserProfileUpdateView.this, date, Toast.LENGTH_LONG).show();
        userprofile_update_dateofbirthTV.setText(Day + "-" + UpdatedMonth + "-" + Year);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        int Hour = 0;
        String ampm = "AM";

        if (hourOfDay > 12) {
            switch (hourOfDay) {
                case 13:
                    Hour = 1;
                    ampm = "PM";
                    break;

                case 14:
                    Hour = 2;
                    ampm = "PM";
                    break;

                case 15:
                    Hour = 3;
                    ampm = "PM";
                    break;

                case 16:
                    Hour = 4;
                    ampm = "PM";
                    break;

                case 17:
                    Hour = 5;
                    ampm = "PM";
                    break;

                case 18:
                    Hour = 6;
                    ampm = "PM";
                    break;

                case 19:
                    Hour = 7;
                    ampm = "PM";
                    break;

                case 20:
                    Hour = 8;
                    ampm = "PM";
                    break;

                case 21:
                    Hour = 9;
                    ampm = "PM";
                    break;

                case 22:
                    Hour = 10;
                    ampm = "PM";
                    break;

                case 23:
                    Hour = 11;
                    ampm = "PM";
                    break;

                case 24:
                    Hour = 12;
                    ampm = "PM";
                    break;
            }

        } else {
            Hour = hourOfDay;
            ampm = "AM";
        }
        String SelectedTime = "Selected Time is " + Hour + " : " + minute + " : " + ampm;
        Toast.makeText(UserProfileUpdateView.this, SelectedTime, Toast.LENGTH_LONG).show();


        userprofile_update_timeofbirthTV.setText(Hour + ":" + minute + " : " + ampm);
    }
}
