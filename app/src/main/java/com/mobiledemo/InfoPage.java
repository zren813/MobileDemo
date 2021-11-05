package com.mobiledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InfoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
    }
    public void onInfoButton(View view) {
        Intent info = new Intent(InfoPage.this, InfoPage.class);
        startActivity(info);
    }

    public void onSearchButton(View view) {
        Intent search = new Intent(InfoPage.this, SearchClassPage.class);
        startActivity(search);
    }

    public void onSettingButton(View view) {
        Intent setting = new Intent(InfoPage.this, SettingPage.class);
        startActivity(setting);
    }
    public void onCourseListButton(View view) {
        Intent courseList = new Intent(InfoPage.this, CourseListPage.class);
        startActivity(courseList);
    }
}