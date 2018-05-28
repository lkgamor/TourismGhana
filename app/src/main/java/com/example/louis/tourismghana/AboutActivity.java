package com.example.louis.tourismghana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Button for starting Scanner Activity
        Button scannerButton = (Button)findViewById(R.id.buttonScanCode);
        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, ScanQRCodeActivity.class));
                finish();
            }
        });

        /*Button for starting Main Activity
        Button mainButton = (Button)findViewById(R.id.buttonScanCode);
        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, MainActivity.class));
                finish();
            }
        });*/
    }
}
