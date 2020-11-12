package com.example.louis.tourismghana.Fragments.InformationFragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.tourismghana.Adapters.Facts;
import com.example.louis.tourismghana.Adapters.FactsAdapter;
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
public class FactsFragment extends Fragment {

    FirebaseFirestore firestore;
    FactsAdapter factsadapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    AlertDialog.Builder alertSymbol;
    List<Facts> factsAboutGhanaList;

    public FactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facts, container, false);

        //Starting progressBar
        progressBar = (ProgressBar) view.findViewById(R.id.actionProgress);
        progressBar.setVisibility(View.VISIBLE);

        //Initializing our fasts list object
        factsAboutGhanaList = new ArrayList<>();

        //Connecting to the Firebase Server
        firestore = FirebaseFirestore.getInstance();

        //Defining the font family of the Main Text
        TextView textView = (TextView) view.findViewById(R.id.regionsPageText);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/rustico_regular.otf");
        textView.setTypeface(typeface);

        factsadapter = new FactsAdapter(factsAboutGhanaList);

        //Defining our recyclerview
        recyclerView = (RecyclerView) view.findViewById(R.id.factsRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(factsadapter);

        //Handling an OnClick Event in RecyclerView Adapter
        factsadapter.setItemClickListener(new FactsAdapter.CustomOnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {

                alertSymbol = new AlertDialog.Builder(getActivity());
                alertSymbol.setIcon(R.drawable.ic_info_black_24dp);
                alertSymbol.setTitle(factsAboutGhanaList.get(position).getTitle());
                alertSymbol.setMessage(factsAboutGhanaList.get(position).getDesc());
                alertSymbol.show();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        factsAboutGhanaList.clear();
        firestore.collection("Facts").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {

                            Facts facts = documentChange.getDocument().toObject(Facts.class);
                            factsAboutGhanaList.add(facts);
                            progressBar.setVisibility(View.GONE);
                            factsadapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
}
