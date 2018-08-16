package dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anagha.astrology.R;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.concurrent.TimeUnit;

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
 * Created by harsha on 4/12/2018.
 */

public class Register extends BaseActivity implements View.OnClickListener {
    /* DatePickerDialog.OnDateSetListener {*/
    /* , TimePickerDialog.OnTimeSetListener {*/
    Context _context = Register.this;
    InputMethodManager inputManager;
    static SharedPreferences sPrefs;
    private EditText userNameET;
    private EditText userEmailET;
    private EditText userPhoneET;
    private EditText passwordET;
    private EditText reenterpasswordET;
    private EditText placeofbirthET;
    private TextView timeofbirthET;
    private TextView dobET;
    private Button registerBT;
    private TextView registerLoginTV;
    //DatePickerDialog datePickerDialog;
    //int Year, Month, Day;
    //Calendar calendar;
    //TimePickerDialog timePickerDialog;
    //int CalendarHour, CalendarMinute;
    String refreshedToken;

    @Override
    protected int getLayoutResource() {
        return R.layout.register_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        uiListener();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
    }

    private void uiListener() {
        registerBT.setOnClickListener(this);
        dobET.setOnClickListener(this);
        timeofbirthET.setOnClickListener(this);
        registerLoginTV.setOnClickListener(this);
    }

    private void initUI() {
        sPrefs = getSharedPreferences(WebCall.SharedPreference_Name, 0);
        registerBT = (Button) findViewById(R.id.registerBT);
        userNameET = (EditText) findViewById(R.id.usernameET);
        userEmailET = (EditText) findViewById(R.id.useremailET);
        userPhoneET = (EditText) findViewById(R.id.mobilenoET);
        passwordET = (EditText) findViewById(R.id.passwordEt);
        reenterpasswordET = (EditText) findViewById(R.id.repasswordEt);
        placeofbirthET = (EditText) findViewById(R.id.placeofbirthEt);

        timeofbirthET = (TextView) findViewById(R.id.timeofbirthEt);
        timeofbirthET.setText("TOB");
        timeofbirthET.setTextColor(getResources().getColor(R.color.greyhint));
        dobET = (TextView) findViewById(R.id.dateofbirthEt);
        dobET.setText("DOB");
        dobET.setTextColor(getResources().getColor(R.color.greyhint));
        registerLoginTV = (TextView) findViewById(R.id.register_logintv);
        refreshedToken = FirebaseInstanceId.getInstance().getToken();

       /* calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerBT:
                if (!validations(userNameET.getText().toString(),
                        userEmailET.getText().toString(),
                        userPhoneET.getText().toString(),
                        passwordET.getText().toString(),
                        reenterpasswordET.getText().toString(), dobET.getText().toString(), timeofbirthET.getText().toString())) {
                    if (NetworkConnectionCheck.checkInternetConnection(_context)) {
                        userSignRegister();
                        /*Intent personaldetails = new Intent(Register.this, Thankyou.class);
                        startActivity(personaldetails);
                        overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);*/
                    } else {
                        new WebCall(_context).DialogForWifi_Enable_CloseDialog(_context.getString(R.string.internet_enable), _context.getString(R.string.internet_enable_message), R.drawable.warning_red);
                    }
                }
                inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.dateofbirthEt:
               /* datePickerDialog = DatePickerDialog.newInstance(Register.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#009688"));
                datePickerDialog.setTitle("Select DateOfBirth From DatePicker");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
*/

                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getSupportFragmentManager(), "datePicker");


                break;
            case R.id.timeofbirthEt:
              /*  CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);
                timePickerDialog = TimePickerDialog.newInstance(Register.this, CalendarHour, CalendarMinute, true);
                timePickerDialog.setThemeDark(false);
                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Toast.makeText(Register.this, "Time Not Selected", Toast.LENGTH_SHORT).show();
                    }
                });
                timePickerDialog.show(getFragmentManager(), "Material Design TimePicker Dialog");
                */
                DialogFragment timeFragment = new TimePickerFragment();
                timeFragment.show(getSupportFragmentManager(), "timePicker");


                break;
            case R.id.register_logintv:
                onBackPressed();
                break;
            default:
                break;
        }
    }

   /* @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {
        int UpdatedMonth = Month + 1;
        String date = "Selected Date : " + Day + "-" + UpdatedMonth + "-" + Year;
        Toast.makeText(Register.this, date, Toast.LENGTH_LONG).show();
        dobET.setText(Day + "-" + UpdatedMonth + "-" + Year);
    }*/

    private boolean validations(String name, String emailUserId, String phoneno, String password, String confirmpassword, String dob, String tob) {
        if (TextUtils.isEmpty(name)) {
            new WebCall(_context).EmptyDialog("Required", "Enter Name", R.drawable.warning_red);
            return true;
        }
        if (TextUtils.isEmpty(emailUserId)) {
            new WebCall(_context).EmptyDialog("Required", "Enter Email", R.drawable.warning_red);
            return true;
        }
        if (!WebCall.isEmailValid(emailUserId)) {
            new WebCall(_context).EmptyDialog("Required", "Enter Valid email address", R.drawable.warning_red);
            return true;
        }
        if (TextUtils.isEmpty(phoneno)) {
            new WebCall(_context).EmptyDialog("Required", "Enter MobileNo", R.drawable.warning_red);
            return true;
        }
        if (TextUtils.isEmpty(password)) {
            new WebCall(_context).EmptyDialog("Required", "Enter Password", R.drawable.warning_red);
            return true;
        }
        if (!(password.length() >= 6)) {
            new WebCall(_context).EmptyDialog("Required", "Password should 6 digit", R.drawable.warning_red);
            //inputLayoutPassword.setError("Password should be atleast more than 6 digits and shold have one numeric and once alphabet");
            return true;
        }
        if (!WebCall.isPasswordValid(password)) {
            new WebCall(_context).EmptyDialog("Required", "Password Pattern", R.drawable.warning_red);
            //inputLayoutPassword.setError("Password should be atleast more than 6 digits and shold have one numeric and once alphabet");
            return true;
        }
        if (TextUtils.isEmpty(confirmpassword)) {
            new WebCall(_context).EmptyDialog("Required", "ReEnter Password", R.drawable.warning_red);
            return true;
        }
        if (!(confirmpassword.length() >= 6)) {
            new WebCall(_context).EmptyDialog("Required", "ReEnterPassword should 6 digit", R.drawable.warning_red);
            //inputLayoutPassword.setError("Password should be atleast more than 6 digits and shold have one numeric and once alphabet");
            return true;
        }
        if (!WebCall.isPasswordValid(confirmpassword)) {
            new WebCall(_context).EmptyDialog("Required", "ReEnterPassword Pattern", R.drawable.warning_red);
            //inputLayoutPassword.setError("Password should be atleast more than 6 digits and shold have one numeric and once alphabet");
            return true;
        }
        if (!checkPassword_ConfirmPassword(password, confirmpassword)) {
            new WebCall(_context).EmptyDialog("Required", "Password and confirm password didn't match", R.drawable.warning_red);
            return true;
        }
        if (dobET.getText().toString().equalsIgnoreCase("DOB")) {
            new WebCall(_context).EmptyDialog("Required", "Date Of Birth", R.drawable.warning_red);
            return true;
        }
        if (timeofbirthET.getText().toString().equalsIgnoreCase("TOB")) {
            new WebCall(_context).EmptyDialog("Required", "Time Of Birth", R.drawable.warning_red);
            return true;
        }


        return false;
    }

    private boolean checkPassword_ConfirmPassword(String password, String confirm_password) {
        boolean pstatus = false;
        if (password != null && password != null) {
            if (password.equals(confirm_password)) {
                pstatus = true;
            }
        }
        return pstatus;
    }

    /*@Override
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
        Toast.makeText(Register.this, SelectedTime, Toast.LENGTH_LONG).show();


        timeofbirthET.setText(Hour + ":" + minute + " : " + ampm);
    }*/

    private void userSignRegister() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
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

        //defining the call
        Call<retrofitrelated.RegisterResponse> call = service.createUser(
                userNameET.getText().toString(),
                userEmailET.getText().toString(),
                userPhoneET.getText().toString(),
                passwordET.getText().toString(),
                dobET.getText().toString(),
                timeofbirthET.getText().toString(),
                placeofbirthET.getText().toString(),
                FirebaseInstanceId.getInstance().getToken(),
                Build.SERIAL,
                "Android"
        );
        //calling the api
        call.enqueue(new Callback<retrofitrelated.RegisterResponse>() {

            @Override
            public void onResponse(Call<retrofitrelated.RegisterResponse> call, Response<retrofitrelated.RegisterResponse> registerresponse) {
                //hiding progress dialog
                Log.i("response", registerresponse.toString());
                progressDialog.dismiss();
                //displaying the message from the response as toast
                if (registerresponse.body().getStatus().equalsIgnoreCase("success")) {
                    Log.i("responsetrue", registerresponse.toString());
                    //here save success result object values and navigate to dashboard
                    //update sharedpreference3 with login true for everytime dashboard
                    //saveLoginStatus(getResources().getString(R.string.signin_status_key), true);

                    Toast mytoast = Toast.makeText(getApplicationContext(), registerresponse.body().getMessage(), Toast.LENGTH_SHORT);
                    mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                    mytoast.show();

                    Intent intent = new Intent(Register.this, Thankyou.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                    Register.this.finish();
                } else {
                    Log.i("responseelse", registerresponse.toString());
                    Toast mytoast = Toast.makeText(getApplicationContext(), registerresponse.body().getMessage(), Toast.LENGTH_SHORT);
                    mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                    mytoast.show();
                }
            }

            @Override
            public void onFailure(Call<retrofitrelated.RegisterResponse> call, Throwable t) {
                Log.e("response", t.toString());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "error :" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
