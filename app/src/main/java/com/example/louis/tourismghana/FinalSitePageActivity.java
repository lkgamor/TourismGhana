package com.example.louis.tourismghana;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Locale;

import uk.co.senab.photoview.PhotoViewAttacher;

public class FinalSitePageActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private Intent getSiteIntent;
    private ImageView siteImage;
    private Button read, stop;
    private TextView siteLocation, siteDescription, siteContact;

    private static String SITE = "SITE";
    private static String SITE_NAME = "SITE_NAME";
    private static String SITE_LOCATION = "SITE_LOCATION";
    private static String SITE_NUMBER = "SITE_NUMBER";
    private static String SITE_DESCRIPTION = "SITE_DESCRIPTION";
    private static String SITE_MAP_LAT = "SITE_MAP_LAT";
    private static String SITE_MAP_LONG = "SITE_MAP_LONG";
    private TextToSpeech textToSpeech;
    private ProgressDialog progressDialog;


    private String IMAGE, NAME, DESCRIPTION, LOCATION, CONTACT, LATITUDE, LONGITUDE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_site_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initializing the TEXT-TO-SPEECH Object
        textToSpeech = new TextToSpeech(this, this);

        //Defining the Views
        read = (Button) findViewById(R.id.btn_Read);
        stop = (Button) findViewById(R.id.btn_Stop);
        siteImage = (ImageView) findViewById(R.id.siteImage);
        siteLocation = (TextView) findViewById(R.id.siteLocation);
        siteDescription = (TextView) findViewById(R.id.siteDescription);
        siteContact = (TextView) findViewById(R.id.siteContact);
        progressDialog = new ProgressDialog(this);

        //Receiving site data from Selected List in Site Categories Fragment
        getSiteIntent = getIntent();
        IMAGE = getSiteIntent.getStringExtra(SITE);
        NAME = getSiteIntent.getStringExtra(SITE_NAME);
        DESCRIPTION = getSiteIntent.getStringExtra(SITE_DESCRIPTION);
        LOCATION = getSiteIntent.getStringExtra(SITE_LOCATION);
        CONTACT = getSiteIntent.getStringExtra(SITE_NUMBER);
        LATITUDE = getSiteIntent.getStringExtra(SITE_MAP_LAT);
        LONGITUDE = getSiteIntent.getStringExtra(SITE_MAP_LONG);

        //Setting the textViews on the screen to the data received
        siteDescription.setText(DESCRIPTION);
        siteLocation.setText(LOCATION);
        siteContact.setText(CONTACT);
        this.setTitle(NAME);

        Picasso.with(this)
                .load(IMAGE)
                .placeholder(R.drawable.image_not_found)
                .fit()
                .centerCrop()
                .into(siteImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Toast.makeText(FinalSitePageActivity.this, "Could not load Site image at this time", Toast.LENGTH_LONG).show();
                    }
                });

        //Enabling ZOOM EFFECT on the Site's image when clicked on
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(siteImage);
        photoViewAttacher.setZoomable(true);
        photoViewAttacher.setZoomTransitionDuration(500);
        photoViewAttacher.update();
    }

    public void READ_SITE_INFO(View view) {

        read.setVisibility(View.GONE);
        stop.setVisibility(View.VISIBLE);
        speakOut();
    }

    public void STOP_READING(View view) {

        read.setVisibility(View.VISIBLE);
        stop.setVisibility(View.GONE);
        stopSpeaking();
    }

    public void CALL_SITE(View view) {

        if (CONTACT.contains("Not"))
        {
            Snackbar.make(view, "Oops...Contact number not available for this site!", Snackbar.LENGTH_LONG).show();
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_phone_black_24dp);
            builder.setTitle("CONTACT THIS SITE");
            builder.setMessage("You are about to call this Tourist Site");
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    textToSpeech.shutdown();
                    Intent call = new Intent(Intent.ACTION_DIAL);
                    call.setData(Uri.parse("tel:" + '"' + CONTACT + '"'));
                    startActivity(call);
                }
            });
            builder.show();
        }
    }

    public void VIEW_MAP(View view) {

        if (LATITUDE.equals("") || LONGITUDE.equals(""))
        {
            Snackbar.make(view, "Oops...Map data not yet available for this site!", Snackbar.LENGTH_LONG).show();
        }
        else
        {
            Intent intent = new Intent(this, SiteMapActivity.class);
            intent.putExtra(SITE_NAME, NAME);
            intent.putExtra(SITE_MAP_LAT, LATITUDE);
            intent.putExtra(SITE_MAP_LONG, LONGITUDE);

            textToSpeech.shutdown();
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                textToSpeech.stop();
                textToSpeech.shutdown();
                this.finish();
                break;
            default:
                textToSpeech.stop();
                textToSpeech.shutdown();
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void shareInfo(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_share_black_24dp);
        builder.setTitle("Share File");
        builder.setNegativeButton("CANCEL",null);
        builder.setPositiveButton("TEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                startDialog();

                String INFO_TO_SHARE = "\nDESCRIPTION:\n" + DESCRIPTION +
                        "\n\nLOCATION:\n" + LOCATION +
                        "\n\nCONTACT INFO:\n" + CONTACT +
                        "\n\nSent from TOURISM GHANA";

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, NAME.toUpperCase());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, INFO_TO_SHARE);
                if (sharingIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(sharingIntent, "Share Site Info"));
                } else {
                    Toast.makeText(FinalSitePageActivity.this, "No app found on your phone which can perform this action", Toast.LENGTH_LONG).show();
                }
                stopDialog();

            }
        });
        builder.show();
    }

    @Override
    public void onInit(int i) {

        if (i == TextToSpeech.SUCCESS) {
            int language = textToSpeech.setLanguage(Locale.UK);
            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("Language Data Error");
                builder.setMessage("No English TTS language data. Please download a Language Pack");
                builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent openSetting = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                        startActivity(openSetting);
                    }
                });

                builder.show();
            }
        }
    }

    public void speakOut() {
        textToSpeech.speak("The " + NAME + "!" +
                DESCRIPTION + "." +
                "It is located in " + LOCATION + "!" +
                "Contact, " + CONTACT, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void stopSpeaking() {
        textToSpeech.stop();
    }

    @Override
    public void onBackPressed() {
        textToSpeech.stop();
        textToSpeech.shutdown();
        this.finish();
    }

    public void startDialog()
    {
        progressDialog.setMessage("Generating audio file...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void stopDialog()
    {
        progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        textToSpeech.stop();
        textToSpeech.shutdown();
        super.onDestroy();
    }
}
