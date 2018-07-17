package dashboard;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/*import android.support.v7.widget.Toolbar;*/


import com.anagha.astrology.R;
import com.anagha.astrology.UserProfileView;

import java.util.ArrayList;
import java.util.List;

import adapters.NavigationDrawerAdapter;
import models.NavDrawerItem;
import utilitys.NetworkConnectionCheck;
import utilitys.WebCall;

/**
 * Created by support on 04/10/2018.
 */

public class FragmentDrawer extends Fragment {
    //private static String TAG = FragmentDrawer.class.getSimpleName();
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private static TypedArray titlesIMR = null;
    private FragmentDrawerListener drawerListener;
    public TextView profileName;
    public TextView signin_signupTV;
    public TextView profileSignOut;
    View layout;
    //private TextView androidVersionNameTV;
    static SharedPreferences sPrefs;

    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
        titlesIMR = getResources().obtainTypedArray(R.array.nav_drawer_user_images_white);
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(titles[i]);
            navItem.setImageResource(titlesIMR.getResourceId(i, -1));
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
        titlesIMR = getResources().obtainTypedArray(R.array.nav_drawer_user_images_white);
    }
    private boolean getBooleanForLoginStatus(String loggedin) {
        SharedPreferences.Editor e = sPrefs.edit();
        return sPrefs.getBoolean("signinStatus", false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.selectedsign_nav_header_gd_logo_signinup, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        //androidVersionNameTV = (TextView)layout.findViewById(R.id.appversion);
        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        profileName = (TextView) layout.findViewById(R.id.profileName);
        signin_signupTV = (TextView) layout.findViewById(R.id.signin_outTV);
        profileSignOut = (TextView) layout.findViewById(R.id.signoutTV);
        sPrefs = getActivity().getSharedPreferences(WebCall.SharedPreference_Name, 0);
       /* try {
            androidVersionNameTV.setText("App Version: " +  getActivity().getPackageManager().getPackageInfo( getActivity().getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException pnfe) {
            Log.e("package version", pnfe.toString());
        }*/
        if (getBooleanForLoginStatus("loggedin")) {
            profileName.setVisibility(View.VISIBLE);
            profileName.setText(sPrefs.getString("email", null));
            //profileName.setText("TestUser");
            profileName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.profile, 0, 0, 0);
            signin_signupTV.setVisibility(View.GONE);
            profileSignOut.setVisibility(View.GONE);
        } else {
           /* signin_signupTV.setVisibility(View.VISIBLE);
            profileName.setVisibility(View.INVISIBLE);
            profileSignOut.setVisibility(View.INVISIBLE);*/
            profileName.setVisibility(View.VISIBLE);
            //profileName.setText(sPrefs.getString("userName", null));
            profileName.setText("TestUser");
            profileName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.profile, 0, 0, 0);
            signin_signupTV.setVisibility(View.GONE);
            profileSignOut.setVisibility(View.GONE);
        }
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);



        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }
        }));
        /*signin_signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent optionLoginFromNormal = new Intent(getActivity(), Login.class);
                //optionLoginFromNormal.putExtra("login_for_vendor", "optinsMenu for normal login");
                startActivity(optionLoginFromNormal);
                getActivity().overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
            }
        });*/
        profileSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent i = new Intent(getActivity(), LogOutActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                mDrawerLayout.closeDrawer(containerView);*/
            }
        });
        profileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkConnectionCheck.checkInternetConnection(getActivity())) {
                    mDrawerLayout.closeDrawer(containerView);
                    Intent i = new Intent(getActivity(), UserProfileView.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                }else{
                    new WebCall(getActivity()).DialogForWifi_Enable_CloseDialog("Internet Enable ", "For this Request Please enable Intenet-Wifi,GPS", R.drawable.error_icon);
                }
            }
        });


        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        //mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public NavDrawerItem getSelectedItem(int position) {
        return adapter.getPosition(position);
    }

    interface ClickListener {
        void onClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, RecyclerView recyclerView, ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);
    }
}
