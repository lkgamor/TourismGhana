package com.example.louis.tourismghana.Fragments.HotelsFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.louis.tourismghana.Adapters.Hotels;
import com.example.louis.tourismghana.Adapters.HotelsAdapter;
import com.example.louis.tourismghana.FinalHotelActivity;
import com.example.louis.tourismghana.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WesternHotels extends Fragment {

    private List<Hotels> hotelsList;
    private ProgressBar progressbar;
    private HotelsAdapter hotelsAdapter;
    private RecyclerView recyclerView;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private static String HOTEL = "HOTEL";
    private static String HOTEL_NAME = "HOTEL_NAME";
    private static String HOTEL_LOCATION = "HOTEL_LOCATION";
    private static String HOTEL_NUMBER = "HOTEL_NUMBER";
    private static String HOTEL_RATING = "HOTEL_RATING";
    private static String HOTEL_PRICE = "HOTEL_PRICE";

    public WesternHotels() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_western_hotels, container, false);

        //Getting a reference to the Menu Bar's items
        setHasOptionsMenu(true);

        //Starting progressBar
        progressbar = (ProgressBar)view.findViewById(R.id.actionProgress);
        progressbar.setVisibility(View.VISIBLE);

        //Initializing our Hotel List Object
        hotelsList = new ArrayList<>();

        //Initialing our Adapter Object
        hotelsAdapter = new HotelsAdapter(getActivity(), hotelsList);

        recyclerView = (RecyclerView)view.findViewById(R.id.hotelsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Initialing our Adapter Object
        hotelsAdapter = new HotelsAdapter(getActivity(), hotelsList);

        //Handling an OnClick Event in RecyclerView Adapter
        hotelsAdapter.setOnItemClickListener(new HotelsAdapter.OnItemClickListener() {
            @Override
            public void OnClickListener(View view, int position) {

                //Sending data of selected Item from recyclerView List to FinalGhanaInfo
                Intent intent = new Intent(getActivity(), FinalHotelActivity.class);
                intent.putExtra(HOTEL, hotelsList.get(position).getImage());
                intent.putExtra(HOTEL_NAME, hotelsList.get(position).getName());
                intent.putExtra(HOTEL_LOCATION, hotelsList.get(position).getLocation());
                intent.putExtra(HOTEL_RATING, hotelsList.get(position).getRating());
                intent.putExtra(HOTEL_NUMBER, hotelsList.get(position).getNumber());
                intent.putExtra(HOTEL_PRICE, hotelsList.get(position).getPrice());

                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Getting a reference to ut Firebase Database Store
        hotelsList.clear();
        firestore.collection("Hotels/Western_Region/Western_Hotels/").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e != null)
                {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    for (DocumentChange document: documentSnapshots.getDocumentChanges()) {

                        if (document.getType() == DocumentChange.Type.ADDED);
                        {
                            Hotels hotels = document.getDocument().toObject(Hotels.class);
                            hotelsList.add(hotels);
                            progressbar.setVisibility(View.GONE);
                            hotelsAdapter.notifyDataSetChanged();
                        }

                    }
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
}
