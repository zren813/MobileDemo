package com.mobiledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);
    }

    public void onInfoButton(View view) {
        Intent info = new Intent(SettingPage.this, InfoPage.class);
        startActivity(info);
    }

    public void onSearchButton(View view) {
        Intent search = new Intent(SettingPage.this, SearchClassPage.class);
        startActivity(search);
    }

    public void onSettingButton(View view) {
        Intent setting = new Intent(SettingPage.this, SettingPage.class);
        startActivity(setting);
    }
    public void onCourseListButton(View view) {
        Intent courseList = new Intent(SettingPage.this, CourseListPage.class);
        startActivity(courseList);
    }

    public void onSmallSettingButton(View view) {
        Intent smallSetting = new Intent(SettingPage.this, DetailedSettingPage.class);
        startActivity(smallSetting);
    }
}