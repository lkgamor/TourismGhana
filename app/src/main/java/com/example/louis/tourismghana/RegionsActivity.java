package com.example.louis.tourismghana;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class RegionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regions);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Defining the font family of the Main Text
        TextView textView = (TextView)findViewById(R.id.regionsPageText);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/rustico_regular.otf");
        textView.setTypeface(typeface);
    }

    public void GOTO_ASHANTI(View view) {
        Intent intent = new Intent(this, RegionSiteCategories.class);
        intent.putExtra("regionNO", 1);
        startActivity(intent);
    }

    public void GOTO_EASTERN(View view) {
        Intent intent = new Intent(this, RegionSiteCategories.class);
        intent.putExtra("regionNO", 2);
        startActivity(intent);
    }

    public void GOTO_VOLTA(View view) {
        Intent intent = new Intent(this, RegionSiteCategories.class);
        intent.putExtra("regionNO", 3);
        startActivity(intent);
    }

    public void GOTO_NORTHERN(View view) {
        Intent intent = new Intent(this, RegionSiteCategories.class);
        intent.putExtra("regionNO", 4);
        startActivity(intent);
    }

    public void GOTO_ACCRA(View view) {
        Intent intent = new Intent(this, RegionSiteCategories.class);
        intent.putExtra("regionNO", 5);
        startActivity(intent);
    }

    public void GOTO_WEST(View view) {
        Intent intent = new Intent(this, RegionSiteCategories.class);
        intent.putExtra("regionNO", 6);
        startActivity(intent);
    }

    public void GOTO_EAST(View view) {
        Intent intent = new Intent(this, RegionSiteCategories.class);
        intent.putExtra("regionNO", 7);
        startActivity(intent);
    }

    public void GOTO_WESTERN(View view) {
        Intent intent = new Intent(this, RegionSiteCategories.class);
        intent.putExtra("regionNO", 8);
        startActivity(intent);
    }

    public void GOTO_BRONG(View view) {
        Intent intent = new Intent(this, RegionSiteCategories.class);
        intent.putExtra("regionNO", 9);
        startActivity(intent);
    }

    public void GOTO_CENTRAL(View view) {
        Intent intent = new Intent(this, RegionSiteCategories.class);
        intent.putExtra("regionNO", 10);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
