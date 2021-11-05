package com.mobiledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DetailedSettingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_setting);
    }

    public void onInfoButton(View view) {
        Intent info = new Intent(DetailedSettingPage.this, InfoPage.class);
        startActivity(info);
    }

    public void onSearchButton(View view) {
        Intent search = new Intent(DetailedSettingPage.this, SearchClassPage.class);
        startActivity(search);
    }

    public void onSettingButton(View view) {
        Intent setting = new Intent(DetailedSettingPage.this, SettingPage.class);
        startActivity(setting);
    }
    public void onCourseListButton(View view) {
        Intent courseList = new Intent(DetailedSettingPage.this, CourseListPage.class);
        startActivity(courseList);
    }

    public void onFontButton(View view) {
        Intent font = new Intent(DetailedSettingPage.this, FontPage.class);
        startActivity(font);
    }

    public void onThemeButton(View view) {
        Intent theme = new Intent(DetailedSettingPage.this, ThemePage.class);
        startActivity(theme);
    }
}