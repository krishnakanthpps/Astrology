package dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

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
import com.anagha.astrology.SelectedSignDashBoard;
import com.google.firebase.iid.FirebaseInstanceId;


import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofitrelated.APIService;
import retrofitrelated.APIUrl;
import retrofitrelated.LoginResult;
import retrofitrelated.LoginResult;
import utilitys.BaseActivity;
import utilitys.NetworkConnectionCheck;
import utilitys.WebCall;

/**
 * Created by harsha on 4/12/2018.
 */

public class Login extends BaseActivity implements View.OnClickListener {
    private TextView signupTV;
    private TextView forgotPassTV;
    private EditText userNameET;
    private EditText passwordET;
    private Button signinBT;
    Context _context = Login.this;
    InputMethodManager inputManager;
    static SharedPreferences sPrefs;
    String refreshedToken;

    @Override
    protected int getLayoutResource() {
        return R.layout.login_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
        initUI();
        uiListener();

    }

    private void uiListener() {
        signupTV.setOnClickListener(this);
        forgotPassTV.setOnClickListener(this);
        signinBT.setOnClickListener(this);

    }

    private void initUI() {
        sPrefs = getSharedPreferences(WebCall.SharedPreference_Name, 0);
        userNameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordEt);
        signinBT = (Button) findViewById(R.id.singinBT);
        signupTV = (TextView) findViewById(R.id.signuptv);
        forgotPassTV = (TextView) findViewById(R.id.forgotpasswordtv);

        refreshedToken = FirebaseInstanceId.getInstance().getToken();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.singinBT:
                if (!validations(userNameET.getText().toString(), passwordET.getText().toString())) {
                    if (NetworkConnectionCheck.checkInternetConnection(_context)) {
                        userSignIn();
                    } else {
                        new WebCall(_context).DialogForWifi_Enable_CloseDialog(_context.getString(R.string.internet_enable), _context.getString(R.string.internet_enable_message), R.drawable.warning_red);
                    }
                }
                inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.signuptv:
                Intent register = new Intent(Login.this, Register.class);
                startActivity(register);
                overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                break;
            case R.id.forgotpasswordtv:
                Intent forgot = new Intent(Login.this, ForgotPasswordDialog.class);
                startActivity(forgot);
                overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                break;
        }
    }

    private boolean validations(String emailUserId, String password) {
        if (TextUtils.isEmpty(emailUserId)) {
            new WebCall(_context).EmptyDialog("Required", "Enter Email", R.drawable.warning_red);
            return true;
        }
        if (!WebCall.isEmailValid(emailUserId)) {
            new WebCall(_context).EmptyDialog("Required", "Enter Valid Email", R.drawable.warning_red);
            return true;
        } else if (TextUtils.isEmpty(password)) {
            new WebCall(_context).EmptyDialog("Required", "Enter Password", R.drawable.warning_red);
            return true;
        }
        return false;
    }

    private void userSignIn() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing In...");
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
        Call<retrofitrelated.LoginResult> call = service.loginUser(
                userNameET.getText().toString(),
                passwordET.getText().toString(),
                FirebaseInstanceId.getInstance().getToken(),
                Build.SERIAL,
                "Android"
        );

        //calling the api
        call.enqueue(new Callback<retrofitrelated.LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                //displaying the message from the response as toast
                if (response.body().getStatus().toString().equalsIgnoreCase("success")) {
                    //here save success result object values and navigate to dashboard
                    //update sharedpreference3 with login true for everytime dashboard

                   /* Toast.makeText(getApplicationContext(), response.body().getResult().getEmail()+","
                            +response.body().getResult().getUserid()+","
                            +response.body().getResult().getToken(), Toast.LENGTH_LONG).show();*/


                    saveLoginStatus(getResources().getString(R.string.signin_status_key), true);
                    updateUserBasicDetails(response.body().getResult().getUserid().toString(),
                            response.body().getResult().getUsername(),
                            response.body().getResult().getEmail(),
                            response.body().getResult().getFirst_name(),
                            response.body().getResult().getLast_name(),
                            response.body().getResult().getDateofbirth(),
                            response.body().getResult().getTimeofbirth(),
                            response.body().getResult().getPlaceofbirth(),
                            response.body().getResult().getCurrent_city(),
                            response.body().getResult().getCurrent_state(),
                            response.body().getResult().getCurrent_country(),
                            response.body().getResult().getMobile(),
                            response.body().getResult().getDasa_at_birth(),
                            response.body().getResult().getDasa_end_date(),
                            response.body().getResult().getToken());

                    Toast mytoast = Toast.makeText(getApplicationContext(), "Welcome :" + response.body().getResult().getEmail(), Toast.LENGTH_SHORT);
                    mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                    mytoast.show();

                    Intent intent = new Intent(Login.this, SelectedSignDashBoard.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                    Login.this.finish();
                } else {
                    Toast mytoast = Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG);
                    mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                    mytoast.show();
                    // Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "error :" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUserBasicDetails(String userid, String username,
                                        String email, String first_name, String last_name,
                                        String dateofbirth, String timeofbirth, String placeofbirth, String current_city,
                                        String current_state, String current_country, String mobile,
                                        String dasa_at_birth, String dasa_end_date, String token) {
        SharedPreferences.Editor e = sPrefs.edit();
        e.putString("userid", userid).commit();
        e.putString("userName", username).commit();
        e.putString("email", email).commit();
        e.putString("first_name", first_name).commit();
        e.putString("last_name", last_name).commit();
        e.putString("dateofbirth", dateofbirth).commit();
        e.putString("timeofbirth", timeofbirth).commit();
        e.putString("placeofbirth", placeofbirth).commit();
        e.putString("current_city", current_city).commit();
        e.putString("current_state", current_state).commit();
        e.putString("current_country", current_country).commit();
        e.putString("mobile", mobile).commit();
        e.putString("dasa_at_birth", dasa_at_birth).commit();
        e.putString("dasa_end_date", dasa_end_date).commit();
        e.putString("token", token).commit();
    }

    private void saveLoginStatus(String signin_status_key, boolean loginStatus) {
        SharedPreferences.Editor e = sPrefs.edit();
        e.putBoolean(signin_status_key, loginStatus).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
    }
}
