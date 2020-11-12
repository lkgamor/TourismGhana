package com.example.louis.tourismghana;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.tourismghana.Fragments.RegionsFragments.AshantiRegionCategoriesFragment;
import com.example.louis.tourismghana.Fragments.RegionsFragments.BrongAhafoCategoriesFragment;
import com.example.louis.tourismghana.Fragments.RegionsFragments.CentralRegionCategoriesFragment;
import com.example.louis.tourismghana.Fragments.RegionsFragments.EasternRegionCategoriesFragment;
import com.example.louis.tourismghana.Fragments.RegionsFragments.GreaterAccraCategoriesFragment;
import com.example.louis.tourismghana.Fragments.RegionsFragments.NorthernRegionCategoriesFragment;
import com.example.louis.tourismghana.Fragments.RegionsFragments.UpperEastCategoriesFragment;
import com.example.louis.tourismghana.Fragments.RegionsFragments.UpperWestCategoriesFragment;
import com.example.louis.tourismghana.Fragments.RegionsFragments.VoltaRegionCategoriesFragment;
import com.example.louis.tourismghana.Fragments.RegionsFragments.WesternRegionCategoriesFragment;

public class RegionSiteCategories extends AppCompatActivity {

    int checkRegionType;
    private boolean opened;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_site_categories);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Declaring Widgets
        relativeLayout = (RelativeLayout) findViewById(R.id.helpLayout);

        SharedPreferences sharedPreferences = getSharedPreferences("SHOW HELP", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        opened = sharedPreferences.getBoolean("launched", true);

        if (opened) {
            editor.putBoolean("launched", false);
            //Starting the bouncing effect on TextView
            relativeLayout.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(RegionSiteCategories.this, R.anim.blink_effect);
            relativeLayout.startAnimation(animation);

            editor.apply();
        }

        //Getting a value from Region Page to decide the Site Categories to show for the chosen Region
        Intent checkRegion = getIntent();
        checkRegionType = checkRegion.getIntExtra("regionNO", 0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (checkRegionType == 1) {
            fragmentTransaction.replace(R.id.activity_region_site_categories, new AshantiRegionCategoriesFragment()).commit();
            this.setTitle("Ashanti Region");
        } else if (checkRegionType == 2) {
            fragmentTransaction.replace(R.id.activity_region_site_categories, new EasternRegionCategoriesFragment()).commit();
            this.setTitle("Eastern Region");
        } else if (checkRegionType == 3) {
            fragmentTransaction.replace(R.id.activity_region_site_categories, new VoltaRegionCategoriesFragment()).commit();
            this.setTitle("Volta Region");
        } else if (checkRegionType == 4) {
            fragmentTransaction.replace(R.id.activity_region_site_categories, new NorthernRegionCategoriesFragment()).commit();
            this.setTitle("Northern Region");
        } else if (checkRegionType == 5) {
            fragmentTransaction.replace(R.id.activity_region_site_categories, new GreaterAccraCategoriesFragment()).commit();
            this.setTitle("Greater Accra");
        } else if (checkRegionType == 6) {
            fragmentTransaction.replace(R.id.activity_region_site_categories, new UpperWestCategoriesFragment()).commit();
            this.setTitle("Upper West");
        } else if (checkRegionType == 7) {
            fragmentTransaction.replace(R.id.activity_region_site_categories, new UpperEastCategoriesFragment()).commit();
            this.setTitle("Upper East");
        } else if (checkRegionType == 8) {
            fragmentTransaction.replace(R.id.activity_region_site_categories, new WesternRegionCategoriesFragment()).commit();
            this.setTitle("Western Region");
        } else if (checkRegionType == 9) {
            fragmentTransaction.replace(R.id.activity_region_site_categories, new BrongAhafoCategoriesFragment()).commit();
            this.setTitle("Brong Ahafo");
        } else if (checkRegionType == 10) {
            fragmentTransaction.replace(R.id.activity_region_site_categories, new CentralRegionCategoriesFragment()).commit();
            this.setTitle("Central Region");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            case R.id.action_hotels:
                return false;
            case R.id.action_review:
                this.finish();
                startActivity(new Intent(this, FeedBackActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.region_site_categories_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
