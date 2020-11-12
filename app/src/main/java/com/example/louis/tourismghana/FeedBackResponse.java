package com.example.louis.tourismghana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class FeedBackResponse extends AppCompatActivity {

    private CardView failureView, successView;
    private Intent getResponseState;
    private FeedBackActivity feedBackActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_response);

        feedBackActivity = new FeedBackActivity();
        successView = (CardView)findViewById(R.id.cardview1);
        failureView = (CardView)findViewById(R.id.cardview2);

        getResponseState = getIntent();
        boolean status = getResponseState.getBooleanExtra("SUCCESS", true);

        if (!status)
        {
            successView.setVisibility(View.GONE);
            failureView.setVisibility(View.VISIBLE);
        }

    }

    public void CLOSE(View view) {
        this.finish();
    }

    public void RETRY(View view) {
        this.finish();
    }
}
