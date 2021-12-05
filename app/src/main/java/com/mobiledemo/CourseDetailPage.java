package com.mobiledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class CourseDetailPage extends AppCompatActivity implements View.OnClickListener{

    private TextView courseNoView, courseTitleView, professorView, regNoView, associatedTermView, dateRangeView, gradeBasisView, levelsView, campusView, daysView;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String uid;
    private FirebaseFirestore db;
    String regNo;
    private Button add_remove_button, professorButton;
    Map<String, String> courses;
    boolean appeared = false;
    String professor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        uid = user.getUid();

        db = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail_page);
        Intent courseIntent = getIntent();
        regNo = courseIntent.getStringExtra("regNo");


        courseNoView = findViewById(R.id.courseDetailCourseNoView);
        courseTitleView = findViewById(R.id.courseDetailCourseTitleView);
        professorView = findViewById(R.id.courseDetailProfessorView);
        regNoView = findViewById(R.id.courseDetailRegNoView);
        associatedTermView = findViewById(R.id.courseDetailAssociatedTermView);
        dateRangeView = findViewById(R.id.courseDetailDateRangeView);
        gradeBasisView = findViewById(R.id.courseDetailGradeBasisView);
        levelsView = findViewById(R.id.courseDetailLevelsView);
        campusView = findViewById(R.id.courseDetailCampusView);
        daysView = findViewById(R.id.courseDetailDaysView);
        add_remove_button = findViewById(R.id.add_remove_class_button);
        professorButton = findViewById(R.id.professorPageButton);

        DocumentReference this_doc_Ref = db.collection("Spring-2022").document(regNo);
        this_doc_Ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot this_doc = task.getResult();

                    courseNoView.setText((String)this_doc.getData().get("number"));
                    courseTitleView.setText((String)this_doc.getData().get("name"));
                    professorView.setText((String)this_doc.getData().get("professor"));
                    regNoView.setText((String)this_doc.getData().get("regnumber"));
                    associatedTermView.setText((String)this_doc.getData().get("Associated Term"));
                    dateRangeView.setText((String)this_doc.getData().get("Date Range"));
                    gradeBasisView.setText((String)this_doc.getData().get("Grade Basis"));
                    levelsView.setText((String)this_doc.getData().get("Levels"));
                    campusView.setText((String)this_doc.getData().get("campus"));
                    daysView.setText((String)this_doc.getData().get("days"));
                    professor = (String) this_doc.getData().get("professor");
                }
            }
        });

        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    courses = userProfile.courses;
                    if (courses.containsKey(regNo)) {
                        appeared = true;
                        add_remove_button.setText("Remove Class");
                    } else {
                        appeared = false;
                        add_remove_button.setText("Add Class");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CourseDetailPage.this, "Wrong", Toast.LENGTH_LONG).show();
            }
        });
        add_remove_button.setOnClickListener(this);
        professorButton.setOnClickListener(this);

    }


    public void onInfoButton(View view) {
        Intent info = new Intent(CourseDetailPage.this, InfoPage.class);
        startActivity(info);
    }

    public void onSearchButton(View view) {
        Intent search = new Intent(CourseDetailPage.this, SearchClassPage.class);
        startActivity(search);
    }

    public void onSettingButton(View view) {
        Intent setting = new Intent(CourseDetailPage.this, SettingPage.class);
        startActivity(setting);
    }
    public void onCourseListButton(View view) {
        Intent courseList = new Intent(CourseDetailPage.this, CourseListPage.class);
        startActivity(courseList);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_remove_class_button:
                add_remove_class();
                break;
            case R.id.professorPageButton:
                professorPage();
                break;
        }
    }

    public void add_remove_class(){
        if (appeared) {
            courses.remove(regNo);
            reference.child(uid).child("courses").setValue(courses);
            appeared = false;
        } else {
            courses.put(regNo,regNo);
            reference.child(uid).child("courses").setValue(courses);
        }
    }
    public void professorPage() {
        Intent professorPage = new Intent(CourseDetailPage.this, ProfessorPage.class);
        professorPage.putExtra("regNo", regNo);
        professorPage.putExtra("professor", professor);
        startActivity(professorPage);
    }
}
