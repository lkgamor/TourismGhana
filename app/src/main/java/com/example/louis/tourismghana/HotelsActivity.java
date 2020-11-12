package com.example.louis.tourismghana;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.louis.tourismghana.Fragments.HotelsFragments.AshantiHotels;
import com.example.louis.tourismghana.Fragments.HotelsFragments.BrongHotels;
import com.example.louis.tourismghana.Fragments.HotelsFragments.CentralHotels;
import com.example.louis.tourismghana.Fragments.HotelsFragments.EasternHotels;
import com.example.louis.tourismghana.Fragments.HotelsFragments.GreaterAccraHotels;
import com.example.louis.tourismghana.Fragments.HotelsFragments.NorthernHotels;
import com.example.louis.tourismghana.Fragments.HotelsFragments.UpperEastHotels;
import com.example.louis.tourismghana.Fragments.HotelsFragments.UpperWestHotels;
import com.example.louis.tourismghana.Fragments.HotelsFragments.VoltaHotels;
import com.example.louis.tourismghana.Fragments.HotelsFragments.WesternHotels;

public class HotelsActivity extends AppCompatActivity {

    int checkRegionType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Checking if Internet Connectivity Exists
        checkNetworkState();


        //Getting a value from Region Page to decide the Site Categories to show for the chosen Region
        Intent checkRegion = getIntent();
        checkRegionType = checkRegion.getIntExtra("regionNO", 0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (checkRegionType == 1)
        {
            fragmentTransaction.replace(R.id.activity_hotels, new AshantiHotels()).commit();
            this.setTitle("Ashanti Region Hotels");
        }
        else if(checkRegionType == 2)
        {
            fragmentTransaction.replace(R.id.activity_hotels, new EasternHotels()).commit();
            this.setTitle("Eastern Region Hotels");
        }
        else if(checkRegionType == 3)
        {
            fragmentTransaction.replace(R.id.activity_hotels, new VoltaHotels()).commit();
            this.setTitle("Volta Region Hotels");
        }
        else if(checkRegionType == 4)
        {
            fragmentTransaction.replace(R.id.activity_hotels, new NorthernHotels()).commit();
            this.setTitle("Northern Region Hotels");
        }
        else if(checkRegionType == 5)
        {
            fragmentTransaction.replace(R.id.activity_hotels, new GreaterAccraHotels()).commit();
            this.setTitle("Greater Accra Hotels");
        }
        else if(checkRegionType == 6)
        {
            fragmentTransaction.replace(R.id.activity_hotels, new UpperWestHotels()).commit();
            this.setTitle("Upper West Hotels");
        }
        else if(checkRegionType == 7)
        {
            fragmentTransaction.replace(R.id.activity_hotels, new UpperEastHotels()).commit();
            this.setTitle("Upper East Hotels");
        }
        else if(checkRegionType == 8)
        {
            fragmentTransaction.replace(R.id.activity_hotels, new WesternHotels()).commit();
            this.setTitle("Western Region Hotels");
        }
        else if(checkRegionType == 9)
        {
            fragmentTransaction.replace(R.id.activity_hotels, new BrongHotels()).commit();
            this.setTitle("Brong Ahafo Hotels");
        }
        else if(checkRegionType == 10)
        {
            fragmentTransaction.replace(R.id.activity_hotels, new CentralHotels()).commit();
            this.setTitle("Central Region Hotels");
        }
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
            Toast.makeText(this, "No Internet Access. Please check your WIFI or Data Connection", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //If there is Internet Connectivity
            network = 1;
        }
        return network;
    }

    public void RETURN_TO_HOME(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
