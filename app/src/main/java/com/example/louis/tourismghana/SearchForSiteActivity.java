package com.example.louis.tourismghana;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.tourismghana.Adapters.SearchSite;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class SearchForSiteActivity extends AppCompatActivity {

    private EditText mSearchField;
    private ImageView mSearchImage;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_site);
        mUserDatabase = FirebaseDatabase.getInstance().getReference("Tourist_Sites/Central_Region/Parks/2");


        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchImage = (ImageView) findViewById(R.id.search_Image);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mSearchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();

                firebaseUserSearch(searchText);

            }
        });

        //Button for starting Scanner Activity
        Button scannerButton = (Button)findViewById(R.id.buttonScanCode);
        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchForSiteActivity.this, ScanQRCodeActivity.class));
                finish();
            }
        });

    }

    private void firebaseUserSearch(String searchText) {

        Toast.makeText(SearchForSiteActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<SearchSite, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<SearchSite, UsersViewHolder>(

                SearchSite.class,
                R.layout.search_list_layout,
                UsersViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, SearchSite model, int position) {


                viewHolder.setDetails(getApplicationContext(), model.getName(), model.getImage());

            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

    }


    // View Holder Class

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(Context ctx, String siteName, String siteImage){

            TextView site_name = (TextView) mView.findViewById(R.id.site_name);
            ImageView site_image = (ImageView) mView.findViewById(R.id.site_image);


            site_name.setText(siteName);

            Picasso.with(ctx).load(siteImage).into(site_image);


        }

    }

}
