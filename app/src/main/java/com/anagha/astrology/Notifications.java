package com.anagha.astrology;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapters.NotificationsListAdapter;
import models.NotificationListModel;
import retrofitrelated.APIUrl;
import utilitys.BaseActivity;
import utilitys.NetworkConnectionCheck;
import utilitys.WebCall;

public class Notifications extends BaseActivity implements NotificationsListAdapter.OnCardClickListner {
    private Context _ctx = Notifications.this;
    private Toolbar mToolbar;
    RecyclerView notificationsList;
    static SharedPreferences sPrefs;
    LinearLayoutManager linearLayoutManager;
    NotificationsListAdapter notificationsListadapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Notifications");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initUI();
        uiListener();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.notificationslist;
    }

    private void uiListener() {
    }

    private void initUI() {
        sPrefs = _ctx.getSharedPreferences(WebCall.SharedPreference_Name, 0);
        notificationsList = (RecyclerView) findViewById(R.id.notificationsListView);
        notificationsList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //noRecordsFoundTv.setVisibility(View.VISIBLE);
        notificationsList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        notificationsList.setLayoutManager(linearLayoutManager);
        if (NetworkConnectionCheck.checkInternetConnection(_ctx)) {
            try {
                getNotificationsList();
            } catch (JSONException jse) {
                Log.e("jse", jse.getMessage());
            }

        } else {
            new WebCall(_ctx).DialogForWifi_Enable_CloseDialog(_ctx.getString(R.string.internet_enable), _ctx.getString(R.string.internet_enable_message), R.drawable.warning_red);
        }

    }

    JSONObject notificationList;
    ProgressDialog progressDialog;

    private void getNotificationsList() throws JSONException {
        progressDialog = new ProgressDialog(_ctx);
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, APIUrl.NOTIFICATIONS_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            notificationList = new JSONObject(response);
                            if (notificationList.getString("status").equalsIgnoreCase("fail")) {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(_ctx);
                                dialog.setCancelable(false);
                                dialog.setTitle("Error");
                                dialog.setMessage(notificationList.getString("errors"));
                                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                                final AlertDialog alert = dialog.create();
                                alert.show();
                            } else {
                                if (notificationList.has("notificationlist")) {
                                    // noRecordsFoundTv.setVisibility(View.INVISIBLE);
                                    updateUI(notificationList);
                                }
                            }
                        } catch (JSONException jse) {
                            Log.e("JSONException", jse.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("fail errorresponse", error.toString());

                    }
                }) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void updateUI(JSONObject person) throws JSONException {
        List<NotificationListModel> notificationListresponse = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) person.get("notificationlist");
        if (jsonArray.length() != 0) {
            Gson gson = new Gson();
            notificationListresponse.clear();
            List<NotificationListModel> posts = Arrays.asList(gson.fromJson(jsonArray.toString(), NotificationListModel[].class));
            for (int i = 0; i < posts.size(); i++) {
                NotificationListModel ptmodel = new NotificationListModel(
                        posts.get(i).getNot_id(),
                        posts.get(i).getNotification_type(),
                        posts.get(i).getNotification_title(),
                        posts.get(i).getNotification_description(),
                        posts.get(i).getSchedule_time()
                );
                notificationListresponse.add(ptmodel);
            }
            notificationsListadapter = new NotificationsListAdapter(Notifications.this, notificationListresponse);
            notificationsList.setAdapter(notificationsListadapter);
            notificationsListadapter.setOnCardClickListner(this);
        }
    }

    @Override
    public void OnCardClicked(View view, int position) {
        NotificationListModel model = notificationsListadapter.getItem(position);
        Intent intent = new Intent(_ctx, NotificationDetails.class);
        intent.putExtra("notificationTitle", model.getNotification_title());
        intent.putExtra("notificationDescription", model.getNotification_description());
        intent.putExtra("notificationDated", model.getSchedule_time().toString());
        intent.putExtra("notificationId", model.getNot_id().toString());
        _ctx.startActivity(intent);
        overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
    }
}
