package dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anagha.astrology.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofitrelated.APIService;
import retrofitrelated.APIUrl;
import retrofitrelated.RegisterResponse;
import utilitys.BaseActivity;
import utilitys.NetworkConnectionCheck;
import utilitys.WebCall;

public class ForgotPasswordDialog extends BaseActivity implements View.OnClickListener {
    private EditText userNameEmailET;
    private Button submitBT;
    Context _context = ForgotPasswordDialog.this;
    InputMethodManager inputManager;

    @Override
    protected int getLayoutResource() {
        return R.layout.forgotpassword_dialog;
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
        submitBT.setOnClickListener(this);


    }

    private void initUI() {
        userNameEmailET = (EditText) findViewById(R.id.usernameET);
        submitBT = (Button) findViewById(R.id.submitBT);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitBT:
                if (!validations(userNameEmailET.getText().toString())) {
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

    private boolean validations(String emailUserId) {
        if (TextUtils.isEmpty(emailUserId)) {
            new WebCall(_context).EmptyDialog("Required", "Enter User name/Email", R.drawable.warning_red);
            return true;
        }
        if (!WebCall.isEmailValid(emailUserId)) {
            new WebCall(_context).EmptyDialog("Required", "Enter Valid email address", R.drawable.warning_red);
            return true;
        }
        return false;
    }

    private void userForgotPassword() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Forgot Password...");
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
        Call<RegisterResponse> call = service.forgotPassword(
                userNameEmailET.getText().toString()
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
                    Toast mytoast = Toast.makeText(getApplicationContext(), "Welcome :" + response.body().getMessage(), Toast.LENGTH_SHORT);
                    mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                    mytoast.show();

                    Intent intent = new Intent(ForgotPasswordDialog.this, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                    ForgotPasswordDialog.this.finish();
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
