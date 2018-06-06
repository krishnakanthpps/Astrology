package dashboard;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anagha.astrology.R;

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
                        //userSignIn();
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
        return false;
    }
}
