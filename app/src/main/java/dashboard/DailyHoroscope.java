package dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.anagha.astrology.R;

import java.util.ArrayList;
import java.util.List;

import fragments.DailyFragment;
import fragments.MonthlyFragment;
import fragments.YearlyFragment;
import utilitys.BaseActivity;
import utilitys.WebCall;

public class DailyHoroscope extends BaseActivity {
    Context _context = DailyHoroscope.this;
    static SharedPreferences sPrefs;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar mToolbar;
    @Override
    protected int getLayoutResource() {
        return R.layout.navigationoptionone;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(getIntent().getStringExtra("bhaavaam"));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initUI();
        uiListener();
    }

    private void uiListener() {
    }

    private void initUI() {
        sPrefs = getSharedPreferences(WebCall.SharedPreference_Name, 0);
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DailyFragment(), "Daily");
        adapter.addFragment(new MonthlyFragment(), "Monthly");
        adapter.addFragment(new YearlyFragment(), "Yearly");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.activity_animation_right_to_left,
                R.anim.right_to_left);
    }
}


