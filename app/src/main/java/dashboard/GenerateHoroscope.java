package dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anagha.astrology.R;

import utilitys.BaseActivity;

/**
 * Created by harsha on 5/25/2018.
 */

public class GenerateHoroscope extends BaseActivity implements View.OnClickListener {
    // private Toolbar mToolbar;
    private Button continueBT;


    @Override
    protected int getLayoutResource() {
        return R.layout.generate_horoscope_layout;
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

    }

    private void uiListener() {
        continueBT.setOnClickListener(this);
    }

    private void initUI() {
        continueBT = (Button) findViewById(R.id.continueBT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continueBT:
                Intent thankyou = new Intent(GenerateHoroscope.this, Thankyou.class);
                startActivity(thankyou);
                overridePendingTransition(R.anim.activity_animation_right_to_left, R.anim.right_to_left);
                break;
            default:
                break;


        }
    }
}
