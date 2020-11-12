package com.example.louis.tourismghana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FinalGhanaInfoActivity extends AppCompatActivity {

    private Intent getSymbolInfo;
    private ImageView symbolImage, symbolImage_big;
    private TextView name, literal, meaning;

    private static String SYMBOL = "SYMBOL";
    private static String SYMBOL_NAME = "SYMBOL_NAME";
    private static String SYMBOL_LITERAL = "SYMBOL_LITERAL";
    private static String SYMBOL_MEANING = "SYMBOL_MEANING";

    private String symbol, symbolName, symbolLiteralMeaning, symbolMeaning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_ghana_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Getting data from the previous (InfoGhanaActivity --> SymbolFragment) activity
        getSymbolInfo = getIntent();
        symbol = getSymbolInfo.getStringExtra(SYMBOL);
        symbolName = getSymbolInfo.getStringExtra(SYMBOL_NAME);
        symbolLiteralMeaning = getSymbolInfo.getStringExtra(SYMBOL_LITERAL);
        symbolMeaning = getSymbolInfo.getStringExtra(SYMBOL_MEANING);

        //Defining Objects Of The Views of the layout
        symbolImage = (ImageView)findViewById(R.id.symbolImage);
        symbolImage_big = (ImageView)findViewById(R.id.symbolImage_big);
        name = (TextView)findViewById(R.id.symbolName);
        literal = (TextView)findViewById(R.id.literal_meaning);
        meaning = (TextView)findViewById(R.id.meaning);

        //Setting the data on the layout
        name.setText(symbolName);
        literal.setText(symbolLiteralMeaning);
        meaning.setText(symbolMeaning);

        //Calling AnimationUtils on the symbolImage Layout
        //Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink_effect);
        //symbolLayout.startAnimation(animation);

        //Loading small image of selected Symbol
        Picasso.with(this)
                .load(symbol)
                .placeholder(R.drawable.image_not_found)
                .fit()
                .centerInside()
                .into(symbolImage);

        //Loading large image of selected Symbol
        Picasso.with(this)
                .load(symbol)
                .placeholder(R.drawable.image_not_found)
                .fit()
                .centerInside()
                .into(symbolImage_big);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
