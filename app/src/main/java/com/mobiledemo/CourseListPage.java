package com.mobiledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CourseListPage extends AppCompatActivity implements ItemClickListener{
    private String uid;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Map<String, String> courses;
    private RecyclerView recyclerView;
    private List<String> courseNo;
    private List<String> courseName;
    private List<String> professorName;
    private List<String> regNo;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_page);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        uid = user.getUid();
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.courseListRecyclerView);
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    courses = userProfile.courses;
                    if (courses != null) {
                        Iterator coursesIterator = courses.entrySet().iterator();
                        courseNo = new ArrayList<>();
                        courseName = new ArrayList<>();
                        professorName = new ArrayList<>();
                        regNo = new ArrayList<>();

                        while (coursesIterator.hasNext()) {
                            Map.Entry mapElement = (Map.Entry)coursesIterator.next();
                            DocumentReference this_doc_Ref = db.collection("Spring-2022").document((String)mapElement.getKey());
                            this_doc_Ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot this_doc = task.getResult();
                                        courseNo.add((String) this_doc.getData().get("number"));
                                        courseName.add((String) this_doc.getData().get("name"));
                                        professorName.add((String) this_doc.getData().get("professor"));
                                        regNo.add((String) this_doc.getData().get("regnumber"));
                                        getAdapter();
                                    }
                                }
                            });

                        }
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
        CourseListAdapter courseListAdapter = new CourseListAdapter(this, courseNo, courseName, professorName, regNo);
        courseListAdapter.setClickListener(this);
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

    @Override
    public void onItemClick(View view, int position) {
        String this_course_reg_no = regNo.get(position);
        Intent courseIntent = new Intent(CourseListPage.this, CourseDetailPage.class);
        courseIntent.putExtra("regNo", this_course_reg_no);
        startActivity(courseIntent);
    }
}