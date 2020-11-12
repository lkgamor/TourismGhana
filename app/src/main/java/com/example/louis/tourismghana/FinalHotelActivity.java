package com.example.louis.tourismghana;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class FinalHotelActivity extends AppCompatActivity {

    private Intent getHotelInfo;
    private ImageView hotelImage;
    private ProgressBar progressBar;
    private TextView name, rating, price, location, number;

    private static String HOTEL = "HOTEL";
    private static String HOTEL_NAME = "HOTEL_NAME";
    private static String HOTEL_LOCATION = "HOTEL_LOCATION";
    private static String HOTEL_NUMBER = "HOTEL_NUMBER";
    private static String HOTEL_RATING = "HOTEL_RATING";
    private static String HOTEL_PRICE = "HOTEL_PRICE";

    private String hotel, hotelName, hotelRating, hotelLocation, hotelPrice, hotelNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_hotel);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setting the custom title for this activity
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(getLayoutInflater().inflate(R.layout.custom_hotel_title, null), new ActionBar.LayoutParams(Gravity.CENTER));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        //Getting data from the previous (InfoGhanaActivity --> SymbolFragment) activity
        getHotelInfo = getIntent();
        hotel = getHotelInfo.getStringExtra(HOTEL);
        hotelName = getHotelInfo.getStringExtra(HOTEL_NAME);
        hotelLocation = getHotelInfo.getStringExtra(HOTEL_LOCATION);
        hotelNumber = getHotelInfo.getStringExtra(HOTEL_NUMBER);
        hotelRating = getHotelInfo.getStringExtra(HOTEL_RATING);
        hotelPrice = getHotelInfo.getStringExtra(HOTEL_PRICE);

        //Defining Objects Of The Views of the layout
        progressBar = (ProgressBar) findViewById(R.id.actionProgress);
        hotelImage = (ImageView) findViewById(R.id.hotelImage);
        name = (TextView) findViewById(R.id.hotel_Name);
        rating = (TextView) findViewById(R.id.hotel_rating);
        price = (TextView) findViewById(R.id.hotel_price);
        location = (TextView) findViewById(R.id.hotel_location);
        number = (TextView) findViewById(R.id.hotel_number);

        //Setting the data on the layout
        name.setText(hotelName);
        rating.setText(hotelRating);
        price.setText(hotelPrice);
        location.setText(hotelLocation);
        number.setText(hotelNumber);

        //Loading image of selected Hotel
        Picasso.with(this)
                .load(hotel)
                .placeholder(R.drawable.image_not_found)
                .fit()
                .centerCrop()
                .into(hotelImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(FinalHotelActivity.this, "Couldn't load hotel picture at this time", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void SHARE_INFO(View view) {

        String INFO_TO_SHARE = String.valueOf("\nRATING:\n" +
                hotelRating + "\n\n" +
                "LOCATION:\n" +
                hotelLocation + "\n\n" +
                "CONTACT INFO:\n" +
                hotelNumber + "\n\n" +
                "Sent from TOURISM GHANA\n");

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, hotelName.toUpperCase());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, INFO_TO_SHARE);
        if (sharingIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sharingIntent, "Share Site Info"));
        } else {
            Toast.makeText(this, "No app found on your phone which can perform this action", Toast.LENGTH_LONG).show();
        }

    }

    public void CALL_HOTEL(View view) {

        if (hotelNumber.contains("no"))
        {
            Snackbar.make(view, "Oops...Contact number not available at this time!", Snackbar.LENGTH_LONG).show();
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_phone_black_24dp);
            builder.setTitle("CONTACT HOTEL");
            builder.setMessage("You are about to call this Hotel");
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + '"' + hotelNumber + '"'));
                    startActivity(callIntent);
                }
            });
            builder.show();
        }
    }
}
