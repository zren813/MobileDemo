package com.mobiledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplaySearchResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search_result);
        Intent intent = getIntent();
        String name = intent.getStringExtra(ProfilePage.EXTRA_Name);
        String email = intent.getStringExtra(ProfilePage.EXTRA_Email);

        // Capture the layout's TextView and set the string as its text
        TextView textView1 = findViewById(R.id.target_name);
        textView1.setText(name);
        TextView textView2 = findViewById(R.id.target_email);
        textView2.setText(email);
    }
}