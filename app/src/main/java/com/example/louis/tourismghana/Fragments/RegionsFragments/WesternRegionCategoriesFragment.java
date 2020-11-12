package com.example.louis.tourismghana.Fragments.RegionsFragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.louis.tourismghana.HotelsActivity;
import com.example.louis.tourismghana.MainActivity;
import com.example.louis.tourismghana.R;
import com.example.louis.tourismghana.SectionsActivity;

/**
 * Created by Louis on 3/14/2018.
 */

public class WesternRegionCategoriesFragment extends Fragment {

    CardView goto_beaches, goto_forts, goto_nature;
    private static String SITE_KEY = "OPTION";

    public WesternRegionCategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.western_region_categories_fragment, container, false);

        //Getting a reference to the Menu Bar's items
        setHasOptionsMenu(true);

        //Defining the font family of the Main Text
        TextView textView = (TextView)view.findViewById(R.id.regionsPageText);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/rustico_regular.otf");
        textView.setTypeface(typeface);

        //Defining the buttons
        goto_beaches = (CardView) view.findViewById(R.id.beaches);
        goto_forts = (CardView) view.findViewById(R.id.forts);
        goto_nature = (CardView) view.findViewById(R.id.nature);

        goto_beaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gotoCategory = new Intent(getActivity(), SectionsActivity.class);
                gotoCategory.putExtra(SITE_KEY, "Western_Region-BEACHES");
                startActivity(gotoCategory);

            }
        });

        goto_forts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gotoCategory = new Intent(getActivity(), SectionsActivity.class);
                gotoCategory.putExtra(SITE_KEY, "Western_Region-FORTS");
                startActivity(gotoCategory);

            }
        });

        goto_nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gotoCategory = new Intent(getActivity(), SectionsActivity.class);
                gotoCategory.putExtra(SITE_KEY, "Western_Region-NATURES");
                startActivity(gotoCategory);

            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_hotels:
                Intent intent = new Intent(getActivity(), HotelsActivity.class);
                intent.putExtra("regionNO", 8);
                startActivity(intent);
                break;

            case R.id.action_home:
                getActivity().finish();
                Intent intent1 = new Intent(getActivity(), MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}