package fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anagha.astrology.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.PayPal;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PayPalAccountNonce;
import com.braintreepayments.api.models.PayPalRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import models.MothlyYearlyHoroscope;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofitrelated.APIService;
import retrofitrelated.APIUrl;
import utilitys.NetworkConnectionCheck;
import utilitys.WebCall;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MonthlyFragment extends Fragment{
    View rootView;
    TextView horoscopeMoonTV;
    Button pay;
    Button paywithpaypal;
    static SharedPreferences sPrefs;
    String token;
    private int REQUEST_CODE = 1234;
    BraintreeFragment mBraintreeFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //reference the xml file containing the view with all the code here.
        rootView = inflater.inflate(R.layout.myhoroscope_month_yearly, container, false);
        initUI();
        uiListener();
        return rootView;
    }

    private void initUI() {
        horoscopeMoonTV = (TextView) rootView.findViewById(R.id.moon_TV);
        pay = (Button) rootView.findViewById(R.id.pay);
        paywithpaypal = (Button) rootView.findViewById(R.id.payWithPayPal);
        sPrefs = getContext().getSharedPreferences(WebCall.SharedPreference_Name, 0);
        if (NetworkConnectionCheck.checkInternetConnection(getContext())) {
            new getToken().execute();
            userMonthly_YearlyHoroscopeView();
        } else {
            new WebCall(getContext()).DialogForWifi_Enable_CloseDialog(getContext().getString(R.string.internet_enable), getContext().getString(R.string.internet_enable_message), R.drawable.warning_red);
        }
    }

    private class getToken extends AsyncTask<String, String, String> {
        ProgressDialog mDialog;

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new HttpClient();
            client.get(APIUrl.API_GET_TOKEN, new HttpResponseCallback() {
                @Override
                public void success(final String responseBody) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(responseBody);
                                token = jsonObject.getString("apikey");
                            } catch (JSONException e) {
                                Log.d("JSONException", e.toString());
                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                @Override
                public void failure(Exception exception) {
                    Log.d("EDMT_ERROR", exception.toString());
                }
            });
            return token;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog);
            mDialog.setCancelable(false);
            mDialog.setMessage("Please wait");
            mDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mDialog.dismiss();
        }
    }

    private void userMonthly_YearlyHoroscopeView() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        //building retrofit object
        // .baseUrl(APIUrl.BASE_URL)
        //.baseUrl("http://192.168.2.65/astro-apii/backend/web/")
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);


        //Defining the user object as we need to pass it with the call
        //User user = new User(name, email, password, gender);

        //defining the call
        Call<MothlyYearlyHoroscope> call = service.loadMonthlyYearlyHoroscope(sPrefs.getString("userid", null));

        //calling the api
        call.enqueue(new Callback<MothlyYearlyHoroscope>() {
            @Override
            public void onResponse(Call<MothlyYearlyHoroscope> call, Response<MothlyYearlyHoroscope> response) {
                //hiding progress dialog
                progressDialog.dismiss();
                //displaying the message from the response as toast
                if (response.body().getStatus().equalsIgnoreCase("success")) {
                    try {
                        horoscopeMoonTV.setText(response.body().getMoon());
                    } catch (NullPointerException npe) {
                        Log.i("DashBoard Chakras", npe.getMessage());
                    } catch (NumberFormatException nfe) {
                        Log.i("npe", nfe.getMessage().toString());
                        //Toast.makeText(getContext(),"Unable to load",Toast.LENGTH_LONG).show();

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

    private void uiListener() {
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (token.length() > 0) {
                    DropInRequest dropInRequest = new DropInRequest()
                            .clientToken(token).disablePayPal();
                    dropInRequest.collectDeviceData(true);
                    startActivityForResult(dropInRequest.getIntent(getContext()), REQUEST_CODE);
                } else {
                    Toast.makeText(getActivity(), "Initialization Issue", Toast.LENGTH_LONG).show();
                }
            }
        });

        paywithpaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (token.length() > 0) {
                    try {
                        mBraintreeFragment = BraintreeFragment.newInstance(getActivity(), token);
                        PayPalRequest request = new PayPalRequest("2")
                                .currencyCode("USD").userAction("commit")
                                .intent(PayPalRequest.INTENT_SALE);

                        PayPal.requestOneTimePayment(mBraintreeFragment, request);

                        mBraintreeFragment.addListener(new PaymentMethodNonceCreatedListener() {
                            @Override
                            public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
                                PayPalAccountNonce paypalAccountNonce = (PayPalAccountNonce) paymentMethodNonce;
                                HashMap paramsHash = new HashMap<>();
                                paramsHash.put("amount", "2");
                                paramsHash.put("nonce", paypalAccountNonce.getNonce());
                                sendPayments(paramsHash);
                            }
                        });
                    } catch (InvalidArgumentException ine) {
                        Log.e("IAException", ine.getMessage());
                    }
                } else {
                    Toast.makeText(getActivity(), "Initialization Issue", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                HashMap paramsHash = new HashMap<>();
                paramsHash.put("amount", "1");
                paramsHash.put("nonce", result.getPaymentMethodNonce().getNonce());
                sendPayments(paramsHash);
            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getContext(), "User Cancelled", Toast.LENGTH_LONG).show();
        } else {
            Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            Log.d("EDMT_ERROR", error.toString());
        }
    }

    private void sendPayments(final HashMap<String, String> paramsHashLoc) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIUrl.API_CHECK_OUT,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        if (response.toString().contains("success")) {
                           // pay.setVisibility(View.GONE);
                        } else {
                            Log.e("error response", response.toString());
                        }
                        Log.d("EDMT_Log", response.toString());
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("EDMT_ERROR", error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (paramsHashLoc == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key : params.keySet()) {
                    params.put(key, paramsHashLoc.get(key));
                }
                return paramsHashLoc;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

}
