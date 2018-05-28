package com.example.louis.tourismghana;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.louis.tourismghana.Fragments.SendRecommendationFragment;
import com.example.louis.tourismghana.Fragments.SendReviewsFragment;

public class FeedbackHolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_holder);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent getActionType = getIntent();
        int action = getActionType.getIntExtra("CHOOSER", 0);

        if (action == 1)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.feedback_layout, new SendReviewsFragment()).commit();
        }
        else if (action == 2)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.feedback_layout, new SendRecommendationFragment()).commit();
        }
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

