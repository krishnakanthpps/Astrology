package dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anagha.astrology.R;
import com.anagha.astrology.SelectedSignDashBoard;

import java.util.concurrent.TimeUnit;

import models.MothlyYearlyHoroscope;
import models.RemediesModel;
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

public class Remidies extends BaseActivity {
    private Context _context = Remidies.this;
    private Toolbar mToolbar;
    static SharedPreferences sPrefs;
    TextView remediesTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("Remedies");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        onInitUi();
        onUiListener();
    }

    private void onUiListener() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getIntent().getStringExtra("call_from").equalsIgnoreCase("main")){
            overridePendingTransition(
                    R.anim.activity_animation_right_to_left,
                    R.anim.right_to_left);
        }else{
            Intent intent = new Intent(_context, SelectedSignDashBoard.class);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
            this.finish();
        }
    }

    private void onInitUi() {
        sPrefs = getSharedPreferences(WebCall.SharedPreference_Name, 0);
        remediesTV = (TextView) findViewById(R.id.moon_TV);
        if (NetworkConnectionCheck.checkInternetConnection(_context)) {
            userRemediesView();
        } else {
            new WebCall(_context).DialogForWifi_Enable_CloseDialog(_context.getString(R.string.internet_enable), _context.getString(R.string.internet_enable_message), R.drawable.warning_red);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.remedies;
    }


    private void userRemediesView() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(_context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);
        //Defining the user object as we need to pass it with the call
        //defining the call
        Call<RemediesModel> call = service.loadRemedies(sPrefs.getString("userid", null));

        //calling the api
        call.enqueue(new Callback<RemediesModel>() {
            @Override
            public void onResponse(Call<RemediesModel> call, Response<RemediesModel> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                //displaying the message from the response as toast
                if (response.body().getStatus().equalsIgnoreCase("success")) {
                    try {
                        remediesTV.setText(response.body().getRemedies());
                    } catch (NullPointerException npe) {
                        Log.i("DashBoard Chakras", npe.getMessage());
                    }
                } else {
                    Toast mytoast = Toast.makeText(_context.getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG);
                    mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                    mytoast.show();
                }
            }

            @Override
            public void onFailure(Call<RemediesModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(_context.getApplicationContext(), "error :" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
