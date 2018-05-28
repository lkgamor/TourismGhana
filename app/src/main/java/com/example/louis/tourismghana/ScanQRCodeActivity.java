package com.example.louis.tourismghana;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Locale;

public class ScanQRCodeActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static String REGION;
    private static String SITE_NAME;
    private static String SITE_NUMBER;
    private static String SITE_CATEGORY;
    private static String SITE_DESCRIPTION;
    private static String SITE_DESCRIPTION_KEY = "description";
    private TextToSpeech textToSpeech;
    private FloatingActionButton fabStop;

    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initializing the TEXT-TO-SPEECH Object
        textToSpeech = new TextToSpeech(this, this);

        CardView scanCode = (CardView) findViewById(R.id.buttonScanCode);

        fabStop = (FloatingActionButton) findViewById(R.id.buttonStop);
        fabStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSpeaking();
                finish();
            }
        });


        scanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (textToSpeech.isSpeaking()) {
                    stopSpeaking();
                }

                IntentIntegrator integrator = new IntentIntegrator(ScanQRCodeActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan Code");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scanning has been cancelled", Toast.LENGTH_LONG).show();
            } else {

                fabStop.setVisibility(View.VISIBLE);
                String directory[] = result.getContents().split("-");
                REGION = directory[0];
                SITE_CATEGORY = directory[1];
                SITE_NUMBER = directory[2];
                SITE_NAME = directory[3];
                loadSite();
                speakOut();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    //Getting data from Firebase Database
    public void loadSite() {
        //Getting a reference to our FireBase FireStore database
        documentReference = FirebaseFirestore.getInstance().document("/Tourist_Sites/" + REGION + "/" + SITE_CATEGORY + "/" + SITE_NUMBER);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                if (documentSnapshot.exists()) {
                    SITE_DESCRIPTION = documentSnapshot.getString(SITE_DESCRIPTION_KEY);

                } else {
                    Toast.makeText(ScanQRCodeActivity.this, "No data received. Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                stopSpeaking();
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int language = textToSpeech.setLanguage(Locale.UK);
            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("Language Data Error");
                builder.setMessage("No English TTS language data. Please download a Language Pack");
                builder.setNegativeButton("Ignore", null);

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

        if (SITE_DESCRIPTION == null) {
            Toast.makeText(ScanQRCodeActivity.this, "Scanner Initialized. Please Scan Again", Toast.LENGTH_LONG).show();
        } else {
            textToSpeech.speak("Welcome, to the " + SITE_NAME + "!" + SITE_DESCRIPTION + "! Thank you for visiting us. Enjoy your tour!", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void stopSpeaking() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    public void onBackPressed() {
        stopSpeaking();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        stopSpeaking();
        super.onDestroy();
    }
}
