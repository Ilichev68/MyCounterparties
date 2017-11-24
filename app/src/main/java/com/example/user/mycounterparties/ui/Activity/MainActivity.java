package com.example.user.mycounterparties.ui.Activity;

import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.ui.Fragments.AbstractFragment;
import com.example.user.mycounterparties.ui.Fragments.LastCounterpartiesFragment;
import com.example.user.mycounterparties.ui.Fragments.SearchFragment;
import com.example.user.mycounterparties.ui.Adapters.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {

    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((AbstractFragment)adapter.getItem(position)).update();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SearchFragment(), "Search");
        adapter.addFragment(new LastCounterpartiesFragment(), "Last");
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}
