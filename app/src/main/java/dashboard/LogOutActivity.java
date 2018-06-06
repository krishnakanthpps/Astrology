package dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.anagha.astrology.R;

import utilitys.BaseActivity;
import utilitys.WebCall;


/**
 * Created by support on 9/25/2015.
 */
public class LogOutActivity extends BaseActivity implements  View.OnClickListener {
    private Button signOutRectBtn;
    static SharedPreferences sPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInitUi();
        onUiListener();
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.logout_screen;
    }
    public void onInitUi() {
        signOutRectBtn = (Button) findViewById(R.id.normalsignoutbutton);
        sPrefs = getSharedPreferences(WebCall.SharedPreference_Name, 0);
   }
       public void onUiListener() {
        signOutRectBtn.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.normalsignoutbutton:
                Intent intent = new Intent(LogOutActivity.this, Login.class);
                updatedEditorForLogOut(getResources().getString(R.string.signin_status_key), false);
                startActivity(intent);


                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Successfully Sign Out", Snackbar.LENGTH_LONG);
                View sbView;
                TextView textView;
                sbView = snackbar.getView();
                textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
                overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                LogOutActivity.this.finish();
                finishAffinity();
                break;
            default:
                break;
        }
    }
    private void updatedEditorForLogOut(String loggedinKey, boolean loginStatus) {
        SharedPreferences.Editor e = sPrefs.edit();
        e.clear();
        e.putBoolean(getResources().getString(R.string.app_demo_screens_visibility_boolean_status_key), true);
        e.putBoolean(loggedinKey, loginStatus).commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
    }
}
