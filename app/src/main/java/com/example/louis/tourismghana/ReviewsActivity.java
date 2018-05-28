package com.example.louis.tourismghana;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.tourismghana.Adapters.Reviews;
import com.example.louis.tourismghana.Adapters.ReviewsAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView noDownload;
    ImageView imageView;
    List<Reviews> reviewsList;
    ReviewsAdapter reviewsAdapter;
    Switch aSwitch;
    private ProgressDialog progressDialog;

    //Getting Refrence to the Firebase Server
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Starting progressBar
        progressDialog = new ProgressDialog(this);
        noDownload = (TextView) findViewById(R.id.noDownload);
        imageView = (ImageView) findViewById(R.id.noDownLoadImage);

        //Initializing our fasts list object
        reviewsList = new ArrayList<>();

        reviewsAdapter = new ReviewsAdapter(reviewsList);

        //Connecting to the Firebase Server
        firestore = FirebaseFirestore.getInstance();

        //Defining our recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.reviewsRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(reviewsAdapter);

        //Defining the Switch object
        aSwitch = (Switch) findViewById(R.id.loadReviews);
        aSwitch.setChecked(false);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (aSwitch.isChecked()) {

                    //Checking for Internt Connectivity
                    checkNetworkState();


                    recyclerView.setVisibility(View.VISIBLE);
                    noDownload.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    aSwitch.setText("Turn OFF to stop loading reviews");

                    startDialog();

                    //Loading Reviews from FireBase
                    reviewsList.clear();

                    loadReviews();

                } else {

                    reviewsList.clear();
                    recyclerView.setVisibility(View.GONE);
                    stopDialog();
                    noDownload.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    aSwitch.setText("Turn ON to load Reviews");

                }
            }
        });
    }

    public void loadReviews()
    {
        reviewsList.clear();
        firestore.collection("User_Responses").addSnapshotListener(ReviewsActivity.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(ReviewsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    stopDialog();
                    noDownload.setText("Oh snap! \n We could not download reviews at this time");
                } else {
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {

                            Reviews reviews = documentChange.getDocument().toObject(Reviews.class);
                            reviewsList.add(reviews);
                            reviewsAdapter.notifyDataSetChanged();
                        }
                    }
                    stopDialog();
                }
            }
        });
    }


    public void startDialog()
    {
        progressDialog.setMessage("Loading reviews...");
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                reviewsList.clear();
                aSwitch.setChecked(false);
            }
        });
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }

    public void stopDialog()
    {
        progressDialog.dismiss();
    }


    private int checkNetworkState() {

        int network;

        //Initializing Connectivity Manager
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        //Initializing Network Manager to get information from Connectivity Manager Object;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Checking if Internet Connection does NOT exist;
        if (networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected())
        {
            network = 0;
            Toast.makeText(this, "No Internet Access. Check WIFI or Data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //If Internet Connectivity Exits
            network = 1;
        }
        return network;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
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

}
