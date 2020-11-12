package com.example.louis.tourismghana.Fragments.InformationFragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.tourismghana.Adapters.Symbols;
import com.example.louis.tourismghana.FinalGhanaInfoActivity;
import com.example.louis.tourismghana.R;
import com.example.louis.tourismghana.Adapters.SymbolsAdapter;
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
public class SymbolsFragment extends Fragment {

    List<Symbols> symbolsList;
    RecyclerView recyclerView;
    SymbolsAdapter symbolsAdapter;
    ProgressBar progressBar;

    private static String SYMBOL = "SYMBOL";
    private static String SYMBOL_NAME = "SYMBOL_NAME";
    private static String SYMBOL_LITERAL = "SYMBOL_LITERAL";
    private static String SYMBOL_MEANING = "SYMBOL_MEANING";

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public SymbolsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_symbols, container, false);

        //Starting progressBar
        progressBar = (ProgressBar) view.findViewById(R.id.actionProgress);
        progressBar.setVisibility(View.VISIBLE);

        //Defining the font family of the Main Text
        TextView textView = (TextView) view.findViewById(R.id.regionsPageText);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/rustico_regular.otf");
        textView.setTypeface(typeface);

        //Initializing our symbols list object
        symbolsList = new ArrayList<>();

        //filling the Symbols Adapter with data from the sumbols list
        symbolsAdapter = new SymbolsAdapter(getActivity(), symbolsList);

        //Defining the recyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.symbolsRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(symbolsAdapter);

        //Handling an OnClick Event in RecyclerView Adapter
        symbolsAdapter.setOnClickListener(new SymbolsAdapter.CustomOnItemClickLister() {
            @Override
            public void OnClickListener(View view, int position) {

                //Sending data of selected Item from recyclerView List to FinalGhanaInfo
                Intent intent = new Intent(getActivity(), FinalGhanaInfoActivity.class);
                intent.putExtra(SYMBOL, String.valueOf(symbolsList.get(position).getImage()));
                intent.putExtra(SYMBOL_NAME, symbolsList.get(position).getName());
                intent.putExtra(SYMBOL_LITERAL, symbolsList.get(position).getLiteral());
                intent.putExtra(SYMBOL_MEANING, symbolsList.get(position).getMeaning());

                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        symbolsList.clear();
        firestore.collection("Symbols").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {

                            Symbols symbols = documentChange.getDocument().toObject(Symbols.class);
                            symbolsList.add(symbols);
                            progressBar.setVisibility(View.GONE);
                            symbolsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
}
