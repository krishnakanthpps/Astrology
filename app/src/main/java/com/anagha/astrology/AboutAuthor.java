package com.anagha.astrology;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import dashboard.Help_AppDetails;
import utilitys.BaseActivity;

public class AboutAuthor extends BaseActivity {
    private Context _context = AboutAuthor.this;
    private Toolbar mToolbar;
    private TextView aboutAuthorTV;

    @Override
    protected int getLayoutResource() {
        return R.layout.about_author;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle(_context.getString(R.string.about_author_title));
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

    private void onInitUi() {
        aboutAuthorTV = (TextView) findViewById(R.id.about_author);
        aboutAuthorTV.setText(Html.fromHtml(getResources().getString(R.string.about_author)));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
    }
}
