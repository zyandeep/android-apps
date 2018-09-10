package com.example.zyandeep.tabexperiments;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.mytabLayout);
        viewPager = findViewById(R.id.viewPager);

      /*  tabLayout.addTab(tabLayout.newTab().setText(R.string.top_stories));         // position: 0
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tech_news));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.coocking));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.politics_news));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.sports_news));*/


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

         return true;
        //return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_share:
                // Display a snackbar
                Snackbar snackbar =
                        Snackbar.make(findViewById(R.id.relative_layout), "Not impletented yet", Snackbar.LENGTH_LONG);

                snackbar.setAction("Exit app", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.finish();
                    }
                });
                snackbar.show();

                break;

            case R.id.item_setting:
                // Display a toast
                Toast.makeText(getApplicationContext(), "You selected setting", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
        //return super.onOptionsItemSelected(item);
    }




    class MyPagerAdapter extends FragmentStatePagerAdapter {

        private int numberOfTabs;

        public MyPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            numberOfTabs = tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TabFragment1();

                case 1:
                    return new TabFragment2();

                case 2:
                    return new TabFragment3();

                case 3:
                    return new TabFragment4();

                case 4:
                    return new TabFragment5();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numberOfTabs;
        }
    }
}