package com.mobiledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CourseListPage extends AppCompatActivity {
    private String uid;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Map<String, String> courses;
    private RecyclerView recyclerView;
    private List<String> courseNo;
    private List<String> courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_page);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        uid = user.getUid();
        recyclerView = findViewById(R.id.courseListRecyclerView);
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    courses = userProfile.courses;
                    Iterator coursesIterator = courses.entrySet().iterator();
                    courseNo = new ArrayList<>();
                    courseName = new ArrayList<>();
                    while (coursesIterator.hasNext()) {
                        Map.Entry mapElement = (Map.Entry)coursesIterator.next();
                        courseNo.add((String) mapElement.getKey());
                        courseName.add((String) mapElement.getValue());
                        getAdapter();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CourseListPage.this, "Wrong", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void getAdapter() {
        System.out.println(courseName);
        CourseListAdapter courseListAdapter = new CourseListAdapter(this, courseNo, courseName);
        recyclerView.setAdapter(courseListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    public void changeToGrid(View view) {
        Intent courseListGrid = new Intent(CourseListPage.this, CourseGridPage.class);
        startActivity(courseListGrid);
    }
}