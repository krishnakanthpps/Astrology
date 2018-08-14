package dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anagha.astrology.R;
import com.anagha.astrology.SelectedSignDashBoard;

import utilitys.BaseActivity;

/**
 * Created by harsha on 5/25/2018.
 */

public class Thankyou extends BaseActivity {
    //FloatingActionButton fab;
    Context _ctx = Thankyou.this;
    TextView thankyou;

    @Override
    protected int getLayoutResource() {
        return R.layout.thankyou_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        uiListener();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();

    }

    private void uiListener() {
    }

    private void initUI() {
        thankyou = (TextView) findViewById(R.id.successmessage);
        thankyou.setText(Html.fromHtml(_ctx.getResources().getString(R.string.thankyoureg)));

    }


}
