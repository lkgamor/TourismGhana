package com.example.louis.tourismghana;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.example.louis.tourismghana.InfoGhanaActivity;
import com.example.louis.tourismghana.IntroActivity;
import com.example.louis.tourismghana.NearbyHotelsMapsActivity;
import com.example.louis.tourismghana.R;
import com.example.louis.tourismghana.RegionSiteCategories;
import com.example.louis.tourismghana.RegionsActivity;
import com.example.louis.tourismghana.ScanQRCodeActivity;
import com.example.louis.tourismghana.SearchForSiteActivity;
import com.example.louis.tourismghana.VoiceHelpActivity;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final int NOTIFICATION_ID = 3 ;
    private int check;
    private static final int SPEECH_REQUEST_CODE = 0;
    private static boolean SPEECH_CHECK = false;
    private Handler handler = new Handler();
    private TextToSpeech textToSpeech;
    private Button screenButton;
    private ImageView playVideo;
    private ViewFlipper flipperImageView;
    private VideoView videoview;
    boolean already_launched;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Calling the CustomTextToSpeech Class
        textToSpeech = new TextToSpeech(this, this);

        //Defining the Buttons for HISTORY, DELICACIES AND FACTS of GHANA
        playVideo = (ImageView)findViewById(R.id.play_videos);
        CardView factsBtn = (CardView)findViewById(R.id.fab1);
        CardView symbolBtn = (CardView)findViewById(R.id.fab2);
        screenButton = (Button)findViewById(R.id.screen);
        flipperImageView = (ViewFlipper) findViewById(R.id.siteImage);
        videoview = (VideoView)findViewById(R.id.videoView);
        final View shadeView = findViewById(R.id.mainView);

        //Playing introduction Video
        SharedPreferences sharedPreferences = getSharedPreferences("Play Video", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        already_launched = sharedPreferences.getBoolean("launched", true);

        if (already_launched) {

            editor.putBoolean("launched", false);

            showVideo();
            editor.apply();

        }


        //Button for starting Speech Synthesis
        Button voiceBtn = (Button) findViewById(R.id.buttonVoice);
        voiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (!SPEECH_CHECK)
                {
                    shadeView.setVisibility(View.VISIBLE);
                    AlertDialog.Builder alertVoice = new AlertDialog.Builder(MainActivity.this);
                    alertVoice.setTitle(Html.fromHtml("<font color ='#1c2541'><centre>\t\tYour Personal Assistant</centre></font><br><br>"));
                    LayoutInflater inflater = getLayoutInflater();

                    View dialogView= inflater.inflate(R.layout.select_voice_assistant, null);
                    alertVoice.setView(dialogView);
                    alertVoice.setNeutralButton("HELP", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            startActivity(new Intent(MainActivity.this, VoiceHelpActivity.class));
                            SPEECH_CHECK = false;

                        }
                    });
                    alertVoice.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            shadeView.setVisibility(View.GONE);
                            SPEECH_CHECK = false;
                        }
                    });
                    alertVoice.setPositiveButton("START", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            shadeView.setVisibility(View.GONE);
                            speakIntro();

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startSpeechRecognizer();
                                }
                            }, 5000);
                        }
                    });
                    alertVoice.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            shadeView.setVisibility(View.GONE);
                            SPEECH_CHECK = false;
                        }
                    });
                    alertVoice.show();

                    SPEECH_CHECK = true;
                }
                else
                {
                    startSpeechRecognizer();
                }
            }
        });


        //Button for starting Scanner Activity
        Button scannerButton = (Button)findViewById(R.id.buttonScanCode);
        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScanQRCodeActivity.class));
            }
        });

        //Button for starting Search Activity
        Button searchButton = (Button)findViewById(R.id.buttonAbout);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
            }
        });

        //Button for starting NearByMapsActivity
        CardView scheduleButton = (CardView)findViewById(R.id.fab3);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SchedulerActivity.class));
            }
        });

        //Button for starting NearByMapsActivity
        CardView reviewsButton = (CardView)findViewById(R.id.fab4);
        reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReviewsActivity.class));
            }
        });

        //Button for starting NearByMapsActivity
        CardView hotelsButton = (CardView)findViewById(R.id.fab5);
        hotelsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NearbyHotelsMapsActivity.class);
                intent.putExtra("place", "leisure");
                startActivity(intent);
            }
        });

        //Button for starting NearByMapsActivity
        CardView leisureButton = (CardView)findViewById(R.id.fab6);
        leisureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NearbyHotelsMapsActivity.class);
                intent.putExtra("place", "hotel");
                startActivity(intent);
            }
        });

        //Button for going to All REGIONS page
        CardView cardView = (CardView) findViewById(R.id.gotoRegions);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegionsActivity.class));
            }
        });

        factsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = 1;
                Intent openSymbols = new Intent(MainActivity.this, InfoGhanaActivity.class);
                openSymbols.putExtra("checker", check);
                startActivity(openSymbols);
                finish();
            }
        });

        symbolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = 2;
                Intent openFacts = new Intent(MainActivity.this, InfoGhanaActivity.class);
                openFacts.putExtra("checker", check);
                startActivity(openFacts);
                finish();
            }
        });


        //CHECKING IF THERE ARE ANY PLANS IN THE SCHEDULER AND DISPLAYING A NOTIFICATION
        if (SchedulerActivity.count > 0)
        {
            NOTIFICATION_REMINDER();
        }


        playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVideo();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        screenButton.setVisibility(View.VISIBLE);
                        flipperImageView.setVisibility(View.VISIBLE);
                        playVideo.setVisibility(View.VISIBLE);
                        videoview.setVisibility(View.GONE);
                    }
                },30000);

            }
        });

    }

    private void startSpeechRecognizer()
    {
        //Creating an intent to start the Speech Recognition Activity
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What would you like to do?");

        //Starting the Activity
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK)
        {
            ArrayList<String> speechResults = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String userCommand = speechResults.get(0);
            //Toast.makeText(this, userCommand, Toast.LENGTH_LONG).show();
            if(userCommand.contains("regions"))
            {
                startActivity(new Intent(this, RegionsActivity.class));
            }
            else if(userCommand.contains("history"))
            {
                Intent intent = new Intent(this, InfoGhanaActivity.class);
                intent.putExtra("checker",1);
                startActivity(intent);
            }
            else if(userCommand.contains("ashanti region"))
            {
                Intent intent = new Intent(this, RegionSiteCategories.class);
                intent.putExtra("regionNO", 1);
                startActivity(intent);
            }
            else if(userCommand.contains("eastern region"))
            {
                Intent intent = new Intent(this, RegionSiteCategories.class);
                intent.putExtra("regionNO", 2);
                startActivity(intent);
            }
            else if(userCommand.contains("volta region"))
            {
                Intent intent = new Intent(this, RegionSiteCategories.class);
                intent.putExtra("regionNO", 3);
                startActivity(intent);
            }
            else if(userCommand.contains("northern region"))
            {
                Intent intent = new Intent(this, RegionSiteCategories.class);
                intent.putExtra("regionNO", 4);
                startActivity(intent);
            }
            else if(userCommand.contains("accra region"))
            {
                Intent intent = new Intent(this, RegionSiteCategories.class);
                intent.putExtra("regionNO", 5);
                startActivity(intent);
            }
            else if(userCommand.contains("upper west region"))
            {
                Intent intent = new Intent(this, RegionSiteCategories.class);
                intent.putExtra("regionNO", 6);
                startActivity(intent);
            }
            else if(userCommand.contains("upper east region"))
            {
                Intent intent = new Intent(this, RegionSiteCategories.class);
                intent.putExtra("regionNO", 7);
                startActivity(intent);
            }
            else if(userCommand.contains("western region"))
            {
                Intent intent = new Intent(this, RegionSiteCategories.class);
                intent.putExtra("regionNO", 8);
                startActivity(intent);
            }
            else if(userCommand.contains("brong ahafo region"))
            {
                Intent intent = new Intent(this, RegionSiteCategories.class);
                intent.putExtra("regionNO", 9);
                startActivity(intent);
            }
            else if(userCommand.contains("central region"))
            {
                Intent intent = new Intent(this, RegionSiteCategories.class);
                intent.putExtra("regionNO", 10);
                startActivity(intent);
            }
            else if(userCommand.contains("symbols"))
            {
                Intent intent = new Intent(this, InfoGhanaActivity.class);
                intent.putExtra("checker",2);
                startActivity(intent);
            }
            else if(userCommand.contains("facts"))
            {
                Intent intent = new Intent(this, InfoGhanaActivity.class);
                intent.putExtra("checker",1);
                startActivity(intent);
            }
            else if(userCommand.contains("close") || userCommand.contains("exit") || userCommand.contains("terminate"))
            {
                stop();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }
            else if(userCommand.contains("ama")){
                respond();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startSpeechRecognizer();
                    }
                }, 1000);
            }
            else if(userCommand.contains("stop") || userCommand.contains("goodbye")){
                respond1();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shutdown();
                    }
                }, 1000);
            }
            else
            {
                speakError();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startSpeechRecognizer();
                    }
                }, 2000);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onInit(int i) {
        if(i == TextToSpeech.SUCCESS)
        {
            int language = textToSpeech.setLanguage(Locale.UK);
            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    public void speakIntro()
    {
        textToSpeech.speak("Hello! Welcome to the Tourism Ghana Voice Assistance. Please, how may I help you?",
                TextToSpeech.QUEUE_FLUSH,
                null);
    }

    public  void speakError()
    {
        textToSpeech.speak("Sorry, I have no reference to that command", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void respond()
    {
        textToSpeech.speak("Yes please. How may I help you?", TextToSpeech.QUEUE_FLUSH, null);
    }

    public void respond1()
    {
        textToSpeech.speak("Yes please. Good-bye", TextToSpeech.QUEUE_FLUSH, null);
    }

    public  void stop(){
        textToSpeech.speak("As you wish", TextToSpeech.QUEUE_FLUSH, null);
    }

    public  void shutdown(){
        respond1();
        textToSpeech.stop();
        textToSpeech.shutdown();
    }

    public void NOTIFICATION_REMINDER()
    {

        NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        // step -2  Setting notification properties
        Notification note = new NotificationCompat.Builder(this)
                .setContentTitle("Tourism Ghana - Your Schedules")
                .setContentText("You have pending plans to attend to")
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true)
                .build();

        manager.notify(NOTIFICATION_ID, note);

    }

    public void showVideo()
    {
        screenButton.setVisibility(View.GONE);
        flipperImageView.setVisibility(View.GONE);
        playVideo.setVisibility(View.GONE);
        videoview.setVisibility(View.VISIBLE);
        String path = "android.resource://" + this.getPackageName() + "/" + R.raw.demo;

        Uri uri = Uri.parse(path);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoview);
        videoview.setMediaController(mediaController);
        videoview.setVideoURI(uri);
        videoview.requestFocus();
        videoview.start();

    }
}
