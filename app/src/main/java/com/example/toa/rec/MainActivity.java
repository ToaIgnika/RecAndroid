package com.example.toa.rec;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.toa.rec.Dialogs.LogInDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);

        TabLayout tabLayout =
                (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Browse"));
        tabLayout.addTab(tabLayout.newTab().setText("My Schedule"));
        tabLayout.addTab(tabLayout.newTab().setText("Survey"));

        final Button loginLogoutButton = findViewById(R.id.loginLogoutButton);
        loginLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginHandler lh = new LoginHandler();
                if (!lh.isLoggedIn(getApplicationContext(), MainActivity.this)) {
                    LogInDialog d = new LogInDialog(MainActivity.this, v.getId());
                    d.show();
                } else {

                    lh.logout(getApplicationContext(), MainActivity.this);
                    Toast.makeText(getApplicationContext(), "LOGGING YOU OUT BOI", Toast.LENGTH_SHORT).show();
                }


            }
        });


        final ViewPager viewPager =
                (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new TabPagerAdapter
                (getSupportFragmentManager(),
                        tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }
}
