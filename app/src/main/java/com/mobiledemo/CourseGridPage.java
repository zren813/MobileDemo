package com.mobiledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CourseGridPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_grid_page);
    }

    public void changeToList(View view) {
        Intent courseList = new Intent(CourseGridPage.this, CourseListPage.class);
        startActivity(courseList);
    }
}