package com.example.louis.tourismghana.Fragments.SitesFragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.louis.tourismghana.Adapters.Site;
import com.example.louis.tourismghana.Adapters.SitesAdapter;
import com.example.louis.tourismghana.FinalSitePageActivity;
import com.example.louis.tourismghana.MainActivity;
import com.example.louis.tourismghana.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RiversFragment extends Fragment {

    private List<Site> siteList;
    private RecyclerView recyclerView;
    private SitesAdapter sitesAdapter;
    private ProgressBar progressBar;
    private RelativeLayout success;
    private CardView gotoHome;
    private LinearLayout failure;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private String REGION;
    private String getIntentData;
    private Intent getRegionType;
    private static String SITE = "SITE";
    private static String SITE_NAME = "SITE_NAME";
    private static String SITE_LOCATION = "SITE_LOCATION";
    private static String SITE_NUMBER = "SITE_NUMBER";
    private static String SITE_DESCRIPTION = "SITE_DESCRIPTION";
    private static String SITE_MAP_LAT = "SITE_MAP_LAT";
    private static String SITE_MAP_LONG = "SITE_MAP_LONG";

    public static String SITE_KEY = "OPTION";

    public RiversFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rivers, container, false);

        getActivity().setTitle("Available Rivers");
        //Receiving region_site_type data from SectionsActivity's fragment
        getRegionType = getActivity().getIntent();
        getIntentData = getRegionType.getStringExtra(SITE_KEY);

        /*Splitting the data received from getIntent() to extract the name of the Region
          This will be used to determine which directory in our Firebase Firestore Database
          to be opened
          */

        String directory[] = getIntentData.split("-");
        REGION = directory[0];

        //Starting progressBar
        progressBar = (ProgressBar)view.findViewById(R.id.actionProgress);
        progressBar.setVisibility(View.VISIBLE);

        success = (RelativeLayout)view.findViewById(R.id.layoutSuccess);
        failure = (LinearLayout)view.findViewById(R.id.layoutFail);
        gotoHome = (CardView)view.findViewById(R.id.goto_home);
        recyclerView = (RecyclerView)view.findViewById(R.id.sitesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);

        //Initializing our Sites List Object
        siteList = new ArrayList<>();

        //Initialing our Adapter Object
        sitesAdapter = new SitesAdapter(getActivity(), siteList);

        //Handling an OnClick Event in RecyclerView Adapter
        sitesAdapter.setOnItemClickListener(new SitesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {

                //Sending data of selected Item on recyclerView List to FinalSitePageActivity
                Intent intent = new Intent(getActivity(), FinalSitePageActivity.class);
                intent.putExtra(SITE, siteList.get(position).getImage());
                intent.putExtra(SITE_NAME, siteList.get(position).getName());
                intent.putExtra(SITE_DESCRIPTION, siteList.get(position).getDescription());
                intent.putExtra(SITE_LOCATION, siteList.get(position).getLocation());
                intent.putExtra(SITE_NUMBER, siteList.get(position).getNumber());
                intent.putExtra(SITE_MAP_LAT, siteList.get(position).getLatitude());
                intent.putExtra(SITE_MAP_LONG, siteList.get(position).getLongitude());

                startActivity(intent);
            }
        });

        //Returning to HomePage after error
        gotoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });


        //Checking Internet Connectivity
        checkNetworkState();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Loading data from the Firebase Firestore Database
        loadSite();
    }

    public void loadSite()
    {
        //Getting a reference to our FireBase FireStore database
        siteList.clear();
        firestore.collection("/Tourist_Sites/" + REGION + "/Rivers/").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e != null)
                {
                    AlertDialog.Builder alertSymbol = new AlertDialog.Builder(getActivity());
                    alertSymbol.setIcon(R.drawable.ic_error_black_24dp);
                    alertSymbol.setTitle("Error");
                    alertSymbol.setMessage(e.getMessage());
                    alertSymbol.setNegativeButton("Reload", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            progressBar.setVisibility(View.VISIBLE);
                            loadSite();
                        }
                    });
                    alertSymbol.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            success.setVisibility(View.GONE);
                            failure.setVisibility(View.VISIBLE);
                        }
                    });
                    alertSymbol.show();
                }
                else
                {
                    for (DocumentChange documentChange: documentSnapshots.getDocumentChanges()) {

                        if (documentChange.getType() == DocumentChange.Type.ADDED)
                        {
                            Site sites = documentChange.getDocument().toObject(Site.class);
                            siteList.add(sites);
                            progressBar.setVisibility(View.GONE);
                            sitesAdapter.notifyDataSetChanged();
                        }
                    }

                    recyclerView.setAdapter(sitesAdapter);
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                getActivity().finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private int checkNetworkState() {

        int network;

        //Initializing Connectivity Manager
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        //Initializing Network Manager to get information from Connectivity Manager Object;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Checking if Internet Connection does NOT exist;
        if (networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected())
        {
            network = 0;
            Toast.makeText(getActivity(), "No Internet Access. Check WIFI or Data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //If Internet Connectivity Exits
            network = 1;
        }
        return network;
    }
}
