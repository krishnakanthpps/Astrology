package dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anagha.astrology.R;
import com.anagha.astrology.SelectedSignDashBoard;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofitrelated.APIService;
import retrofitrelated.APIUrl;
import retrofitrelated.LoginResult;
import retrofitrelated.RegisterResponse;
import utilitys.BaseActivity;
import utilitys.NetworkConnectionCheck;
import utilitys.WebCall;

public class ChangePassword extends BaseActivity implements View.OnClickListener {
    private EditText oldPasswordET;
    private EditText newPasswordET;
    private EditText confirmnewPasswordET;
    private Button submitBT;
    Context _context = ChangePassword.this;
    InputMethodManager inputManager;
    static SharedPreferences sPrefs;

    @Override
    protected int getLayoutResource() {
        return R.layout.change_password;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        uiListener();

    }

    private void uiListener() {
        submitBT.setOnClickListener(this);


    }

    private void initUI() {
        sPrefs = getSharedPreferences(WebCall.SharedPreference_Name, 0);
        oldPasswordET = (EditText) findViewById(R.id.oldPasswordET);
        newPasswordET = (EditText) findViewById(R.id.newpasswordET);
        confirmnewPasswordET = (EditText) findViewById(R.id.confirm_passwordET);
        submitBT = (Button) findViewById(R.id.resetBT);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitBT:
                if (!validations(oldPasswordET.getText().toString(), newPasswordET.getText().toString(), confirmnewPasswordET.getText().toString())) {
                    if (NetworkConnectionCheck.checkInternetConnection(_context)) {
                        userForgotPassword();
                    } else {
                        new WebCall(_context).DialogForWifi_Enable_CloseDialog(_context.getString(R.string.internet_enable), _context.getString(R.string.internet_enable_message), R.drawable.warning_red);
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
    }

    private boolean validations(String oldpassword, String newpassword, String confirmnewpassword) {
        if (TextUtils.isEmpty(oldpassword)) {
            new WebCall(_context).EmptyDialog("Required", "Enter Password", R.drawable.warning_red);
            return true;
        }
        if (!(oldpassword.length() >= 6)) {
            new WebCall(_context).EmptyDialog("Required", "Password should 6 digit", R.drawable.warning_red);
            //inputLayoutPassword.setError("Password should be atleast more than 6 digits and shold have one numeric and once alphabet");
            return true;
        }
        if (!WebCall.isPasswordValid(oldpassword)) {
            new WebCall(_context).EmptyDialog("Required", "Password Pattern", R.drawable.warning_red);
            //inputLayoutPassword.setError("Password should be atleast more than 6 digits and shold have one numeric and once alphabet");
            return true;
        }
        if (TextUtils.isEmpty(newpassword)) {
            new WebCall(_context).EmptyDialog("Required", "Enter NewPassword", R.drawable.warning_red);
            return true;
        }
        if (!(newpassword.length() >= 6)) {
            new WebCall(_context).EmptyDialog("Required", "Password should 6 digit", R.drawable.warning_red);
            //inputLayoutPassword.setError("Password should be atleast more than 6 digits and shold have one numeric and once alphabet");
            return true;
        }
        if (!WebCall.isPasswordValid(newpassword)) {
            new WebCall(_context).EmptyDialog("Required", "Password Pattern", R.drawable.warning_red);
            //inputLayoutPassword.setError("Password should be atleast more than 6 digits and shold have one numeric and once alphabet");
            return true;
        }
        if (TextUtils.isEmpty(confirmnewpassword)) {
            new WebCall(_context).EmptyDialog("Required", "Enter NewPassword", R.drawable.warning_red);
            return true;
        }
        if (!(confirmnewpassword.length() >= 6)) {
            new WebCall(_context).EmptyDialog("Required", "Password should 6 digit", R.drawable.warning_red);
            //inputLayoutPassword.setError("Password should be atleast more than 6 digits and shold have one numeric and once alphabet");
            return true;
        }
        if (!WebCall.isPasswordValid(confirmnewpassword)) {
            new WebCall(_context).EmptyDialog("Required", "Password Pattern", R.drawable.warning_red);
            //inputLayoutPassword.setError("Password should be atleast more than 6 digits and shold have one numeric and once alphabet");
            return true;
        }
        if (!checkPassword_ConfirmPassword(newpassword, confirmnewpassword)) {
            new WebCall(_context).EmptyDialog("Required", "Password and confirm password didn't match", R.drawable.warning_red);
            return true;
        }
        return false;
    }

    private boolean checkPassword_ConfirmPassword(String newpassword, String confirmnewpassword) {
        boolean pstatus = false;
        if (newpassword != null && newpassword != null) {
            if (newpassword.equals(confirmnewpassword)) {
                pstatus = true;
            }
        }
        return pstatus;
    }

    private void userForgotPassword() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Reset Password...");
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
        Call<RegisterResponse> call = service.changePassword(
                "",
                oldPasswordET.getText().toString(),
                newPasswordET.getText().toString()
        );

        //calling the api
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                //displaying the message from the response as toast
                if (response.body().getStatus().toString().equalsIgnoreCase("success")) {
                    //here save success result object values and navigate to dashboard
                    //update sharedpreference3 with login true for everytime dashboard

                   /* Toast.makeText(getApplicationContext(), response.body().getResult().getEmail()+","
                            +response.body().getResult().getUserid()+","
                            +response.body().getResult().getToken(), Toast.LENGTH_LONG).show();*/


                    /* saveLoginStatus(getResources().getString(R.string.signin_status_key), true);
                     */

                    Toast mytoast = Toast.makeText(getApplicationContext(), "Success :" + response.body().getMessage(), Toast.LENGTH_SHORT);
                    mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                    mytoast.show();

                    Intent intent = new Intent(ChangePassword.this, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                    ChangePassword.this.finish();
                } else {
                    Toast mytoast = Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG);
                    mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                    mytoast.show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "error :" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
