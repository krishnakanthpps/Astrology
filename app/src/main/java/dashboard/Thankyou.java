package dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anagha.astrology.R;
import com.anagha.astrology.SelectedSignDashBoard;

import utilitys.BaseActivity;

/**
 * Created by harsha on 5/25/2018.
 */

public class Thankyou extends BaseActivity{
    //FloatingActionButton fab;
    Context _ctx = Thankyou.this;
    @Override
    protected int getLayoutResource() {
        return R.layout.thankyou_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* mToolbar = (Toolbar) findViewById(R.id.toolbar);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();

    }

    private void uiListener() {
       // fab.setOnClickListener(this);
    }

    private void initUI() {

        //fab = (FloatingActionButton) findViewById(R.id.fab);
    }


}
