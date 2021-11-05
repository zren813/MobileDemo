package com.mobiledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CourseListPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_page);
    }
    public void onInfoButton(View view) {
        Intent info = new Intent(CourseListPage.this, InfoPage.class);
        startActivity(info);
    }

    public void onSearchButton(View view) {
        Intent search = new Intent(CourseListPage.this, SearchClassPage.class);
        startActivity(search);
    }

    public void onSettingButton(View view) {
        Intent setting = new Intent(CourseListPage.this, SettingPage.class);
        startActivity(setting);
    }
    public void onCourseListButton(View view) {
        Intent courseList = new Intent(CourseListPage.this, CourseListPage.class);
        startActivity(courseList);
    }
}