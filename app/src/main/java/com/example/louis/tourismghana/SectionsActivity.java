package com.example.louis.tourismghana;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.louis.tourismghana.Fragments.SitesFragments.BeachesFragment;
import com.example.louis.tourismghana.Fragments.SitesFragments.CastlesFragment;
import com.example.louis.tourismghana.Fragments.SitesFragments.FortsFragment;
import com.example.louis.tourismghana.Fragments.SitesFragments.GamesFragment;
import com.example.louis.tourismghana.Fragments.SitesFragments.MonumentsFragment;
import com.example.louis.tourismghana.Fragments.SitesFragments.MountainsFragment;
import com.example.louis.tourismghana.Fragments.SitesFragments.MuseumsFragment;
import com.example.louis.tourismghana.Fragments.SitesFragments.NatureFragment;
import com.example.louis.tourismghana.Fragments.SitesFragments.ParksFragment;
import com.example.louis.tourismghana.Fragments.SitesFragments.RiversFragment;
import com.example.louis.tourismghana.Fragments.SitesFragments.WaterfallsFragment;
import com.example.louis.tourismghana.Fragments.SitesFragments.WildlifeFragment;
import com.example.louis.tourismghana.Fragments.SitesFragments.ZooFragment;

public class SectionsActivity extends AppCompatActivity {

    public static String SITE_KEY = "OPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent siteCategoryIntent = getIntent();
        String option = siteCategoryIntent.getStringExtra(SITE_KEY);

        //Creating an instance of the bundle to store data
        Bundle siteTypeDeterminer = new Bundle();

        //Starting out FragmentManager and transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (option.contains("ZOO"))
        {
            //setting the title of the activity
            this.setTitle("ZOO");

            //Putting the data from previous fragment into bundle
            siteTypeDeterminer.putString(SITE_KEY, option);

            //Creating an instance of the Zoo Fragment
            ZooFragment zooFragment = new ZooFragment();

            //Pssing the bundle to the fragment
            zooFragment.setArguments(siteTypeDeterminer);

            //Starting the fragment
            fragmentTransaction.replace(R.id.siteTypeContainer, zooFragment).commit();
        }
        else if (option.contains("BEACH"))
        {
            //setting the title of the activity
            this.setTitle("BEACHES");

            //Putting the data from previous fragment into bundle
            siteTypeDeterminer.putString(SITE_KEY, option);

            //Creating an instance of the Beach Fragment
            BeachesFragment beachesFragment = new BeachesFragment();

            //Pssing the bundle to the fragment
            beachesFragment.setArguments(siteTypeDeterminer);

            //Starting the fragment
            fragmentTransaction.replace(R.id.siteTypeContainer, beachesFragment).commit();
        }
        else if (option.contains("FORT"))
        {

            //setting the title of the activity
            this.setTitle("FORTS");

            //Putting the data from previous fragment into bundle
            siteTypeDeterminer.putString(SITE_KEY, option);

            //Creating an instance of the Fort Fragment
            FortsFragment fortsFragment = new FortsFragment();

            //Pssing the bundle to the fragment
            fortsFragment.setArguments(siteTypeDeterminer);

            //Starting the fragment
            fragmentTransaction.replace(R.id.siteTypeContainer, fortsFragment).commit();
        }
        else if (option.contains("CASTLE"))
        {
            //setting the title of the activity
            this.setTitle("CASTLES");

            //Putting the data from previous fragment into bundle
            siteTypeDeterminer.putString(SITE_KEY, option);

            //Creating an instance of the Castle Fragment
            CastlesFragment castlesFragment = new CastlesFragment();

            //Pssing the bundle to the fragment
            castlesFragment.setArguments(siteTypeDeterminer);

            //Starting the fragment
            fragmentTransaction.replace(R.id.siteTypeContainer, castlesFragment).commit();
        }
        else if (option.contains("MUSEUM"))
        {
            //setting the title of the activity
            this.setTitle("MUSEUMS");

            //Putting the data from previous fragment into bundle
            siteTypeDeterminer.putString(SITE_KEY, option);

            //Creating an instance of the Museum Fragment
            MuseumsFragment museumsFragment = new MuseumsFragment();

            //Pssing the bundle to the fragment
            museumsFragment.setArguments(siteTypeDeterminer);

            //Starting the fragment
            fragmentTransaction.replace(R.id.siteTypeContainer, museumsFragment).commit();
        }
        else if (option.contains("MOUNTAIN"))
        {
            //setting the title of the activity
            this.setTitle("MOUNTAINS");

            //Putting the data from previous fragment into bundle
            siteTypeDeterminer.putString(SITE_KEY, option);

            //Creating an instance of the Mountain Fragment
            MountainsFragment mountainsFragment = new MountainsFragment();

            //Pssing the bundle to the fragment
            mountainsFragment.setArguments(siteTypeDeterminer);

            //Starting the fragment
            fragmentTransaction.replace(R.id.siteTypeContainer, mountainsFragment).commit();
        }
        else if (option.contains("NATURE"))
        {
            //setting the title of the activity
            this.setTitle("NATURE RESERVES");

            //Putting the data from previous fragment into bundle
            siteTypeDeterminer.putString(SITE_KEY, option);

            //Creating an instance of the Nature Fragment
            NatureFragment natureFragment = new NatureFragment();

            //Pssing the bundle to the fragment
            natureFragment.setArguments(siteTypeDeterminer);

            //Starting the fragment
            fragmentTransaction.replace(R.id.siteTypeContainer, natureFragment).commit();
        }
        else if (option.contains("PARK"))
        {
            //setting the title of the activity
            this.setTitle("NATIONAL PARKS");

            //Putting the data from previous fragment into bundle
            siteTypeDeterminer.putString(SITE_KEY, option);

            //Creating an instance of the Nature Fragment
            ParksFragment parksFragment = new ParksFragment();

            //Pssing the bundle to the fragment
            parksFragment.setArguments(siteTypeDeterminer);

            //Starting the fragment
            fragmentTransaction.replace(R.id.siteTypeContainer, parksFragment).commit();
        }
        else if (option.contains("GAME"))
        {

            //setting the title of the activity
            this.setTitle("GAME RESERVES");

            //Putting the data from previous fragment into bundle
            siteTypeDeterminer.putString(SITE_KEY, option);

            //Creating an instance of the Game Fragment
            GamesFragment gamesFragment = new GamesFragment();

            //Pssing the bundle to the fragment
            gamesFragment.setArguments(siteTypeDeterminer);

            //Starting the fragment
            fragmentTransaction.replace(R.id.siteTypeContainer, gamesFragment).commit();
        }
        else if (option.contains("WATERFALLS"))
        {
            //setting the title of the activity
            this.setTitle("WATERFALLS");

            //Putting the data from previous fragment into bundle
            siteTypeDeterminer.putString(SITE_KEY, option);

            //Creating an instance of the Waterfall Fragment
            WaterfallsFragment waterfallsFragment = new WaterfallsFragment();

            //Pssing the bundle to the fragment
            waterfallsFragment.setArguments(siteTypeDeterminer);

            //Starting the fragment
            fragmentTransaction.replace(R.id.siteTypeContainer, waterfallsFragment).commit();
        }
        else if (option.contains("WILDLIFE"))
        {
            //setting the title of the activity
            this.setTitle("WILDLIFE");

            //Putting the data from previous fragment into bundle
            siteTypeDeterminer.putString(SITE_KEY, option);

            //Creating an instance of the Wildlife Fragment
            WildlifeFragment wildlifeFragment = new WildlifeFragment();

            //Pssing the bundle to the fragment
            wildlifeFragment.setArguments(siteTypeDeterminer);

            //Starting the fragment
            fragmentTransaction.replace(R.id.siteTypeContainer, wildlifeFragment).commit();
        }
        else if (option.contains("RIVER"))
        {
            //setting the title of the activity
            this.setTitle("RIVERS");

            //Putting the data from previous fragment into bundle
            siteTypeDeterminer.putString(SITE_KEY, option);

            //Creating an instance of the River Fragment
            RiversFragment riversFragment = new RiversFragment();

            //Pssing the bundle to the fragment
            riversFragment.setArguments(siteTypeDeterminer);

            //Starting the fragment
            fragmentTransaction.replace(R.id.siteTypeContainer, riversFragment).commit();
        }
        else if (option.contains("MONUMENT"))
        {
            //setting the title of the activity
            this.setTitle("MONUMENTS");

            //Putting the data from previous fragment into bundle
            siteTypeDeterminer.putString(SITE_KEY, option);

            //Creating an instance of the Monument Fragment
            MonumentsFragment monumentsFragment = new MonumentsFragment();

            //Pssing the bundle to the fragment
            monumentsFragment.setArguments(siteTypeDeterminer);

            //Starting the fragment
            fragmentTransaction.replace(R.id.siteTypeContainer, monumentsFragment).commit();
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_error_black_24dp);
            builder.setTitle("Page Load Error");
            builder.setMessage("Sorry! Please return to previous page and reload this page");
            builder.setPositiveButton("OK", null);
            builder.show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void RETURN_TO_HOME(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
