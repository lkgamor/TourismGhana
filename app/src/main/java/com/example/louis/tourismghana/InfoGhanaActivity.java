package com.example.louis.tourismghana;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.tourismghana.Fragments.InformationFragments.FactsFragment;
import com.example.louis.tourismghana.Fragments.InformationFragments.SymbolsFragment;

public class InfoGhanaActivity extends AppCompatActivity {

    int check;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_ghana);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setTitle("ABOUT GHANA");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //Checking for which Fragment to Start
        Intent FragmentChecker = getIntent();
        check = FragmentChecker.getIntExtra("checker", 0);

        if (check==1)
        {
            mViewPager.setCurrentItem(0);
        }
        if (check==2)
        {
            mViewPager.setCurrentItem(1);
        }

        //Checking if User has NETWORK CONNECTIVITY
        checkNetworkState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_ghana, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, FeedBackActivity.class));
        }
        else if(id == android.R.id.home)
        {
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_info_ghana, container, false);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            if (position == 0)
            {
                fragment = new FactsFragment();
            }
            if (position == 1)
            {
                fragment = new SymbolsFragment();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "FACTS";
                case 1:
                    return "SYMBOLS";
            }
            return null;
        }
    }

    public void RETURN_TO_HOME(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        super.onBackPressed();
    }

    private int checkNetworkState() {

        int network;

        //Initializing Connectivity Manager
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        //Initializing Network Manager to get information from Connectivity Manager Object;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Checking if Internet Connection does NOT exist;
        if (networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected())
        {
            network = 0;
            Toast.makeText(this, "No Internet Access. Check WIFI or Data", Toast.LENGTH_LONG).show();
        }
        else
        {
            //If Internet Connectivity Exits
            network = 1;
        }
        return network;
    }
}
