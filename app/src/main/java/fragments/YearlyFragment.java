package fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anagha.astrology.R;

import models.DailyPlanetResponse;
import models.MothlyYearlyHoroscope;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofitrelated.APIService;
import retrofitrelated.APIUrl;
import utilitys.NetworkConnectionCheck;
import utilitys.WebCall;

public class YearlyFragment extends Fragment {
    View rootView;
    TextView horoscopeMoonTV;
    /*TextView dailyHoroscopeJupiterTV;
    TextView dailyHoroscopeMercuryTV;
    TextView dailyHoroscopeSunTV;
    TextView dailyHoroscopeMarsTV;
    TextView dailyHoroscopeVenusTV;
    TextView dailyHoroscopeSaturnTV;*/
    //TextView dailyHoroscopeTV;
    static SharedPreferences sPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //reference the xml file containing the view with all the code here.
        rootView = inflater.inflate(R.layout.myhoroscope_month_yearly, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        horoscopeMoonTV = (TextView) rootView.findViewById(R.id.moon_TV);
      /*  dailyHoroscopeJupiterTV = (TextView) rootView.findViewById(R.id.jupiter_dailyTV);
        dailyHoroscopeMercuryTV = (TextView) rootView.findViewById(R.id.mercury_dailyTV);
        dailyHoroscopeSunTV = (TextView) rootView.findViewById(R.id.sun_dailyTV);
        dailyHoroscopeMarsTV = (TextView) rootView.findViewById(R.id.mars_dailyTV);
        dailyHoroscopeVenusTV = (TextView) rootView.findViewById(R.id.venus_dailyTV);
        dailyHoroscopeSaturnTV = (TextView) rootView.findViewById(R.id.saturn_dailyTV);*/
        sPrefs = getContext().getSharedPreferences(WebCall.SharedPreference_Name, 0);
        if (NetworkConnectionCheck.checkInternetConnection(getContext())) {
            userMonthly_YearlyHoroscopeView();
        } else {
            new WebCall(getContext()).DialogForWifi_Enable_CloseDialog(getContext().getString(R.string.internet_enable), getContext().getString(R.string.internet_enable_message), R.drawable.warning_red);
        }
    }

    private void userMonthly_YearlyHoroscopeView() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
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
        Call<MothlyYearlyHoroscope> call = service.loadYearlyHoroscope(sPrefs.getString("userid", null));

        //calling the api
        call.enqueue(new Callback<MothlyYearlyHoroscope>() {
            @Override
            public void onResponse(Call<MothlyYearlyHoroscope> call, Response<MothlyYearlyHoroscope> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                //displaying the message from the response as toast
                if (response.body().getStatus().equalsIgnoreCase("success")) {
                    //starTV.setText(response.body().getRasi());
                    try {
                        horoscopeMoonTV.setText(response.body().getMoon());
                        /*dailyHoroscopeJupiterTV.setText(response.body().getResult().getDaily_Jupiter());
                        dailyHoroscopeMercuryTV.setText(response.body().getResult().getDaily_Mercury());
                        dailyHoroscopeSunTV.setText(response.body().getResult().getDaily_Sun());
                        dailyHoroscopeMarsTV.setText(response.body().getResult().getDaily_Mars());
                        dailyHoroscopeVenusTV.setText(response.body().getResult().getDaily_Venus());
                        dailyHoroscopeSaturnTV.setText(response.body().getResult().getDaily_Saturn());*/
                    } catch (NullPointerException npe) {
                        Log.i("DashBoard Chakras", npe.getMessage());
                    }
                } else {
                    Toast mytoast = Toast.makeText(getContext().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG);
                    mytoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  // for center vertical
                    mytoast.show();
                }
            }

            @Override
            public void onFailure(Call<MothlyYearlyHoroscope> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext().getApplicationContext(), "error :" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
