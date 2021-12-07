package com.mobiledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import java.util.Iterator;
import java.util.Map;

public class CourseGridPage extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String uid;
    private Map<String, String> courses;
    private View mLayout, tuLayout, wLayout, thLayout, fLayout;
    private FirebaseFirestore db;
    float pixels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_grid_page);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        db = FirebaseFirestore.getInstance();
        uid = user.getUid();
        mLayout = findViewById(R.id.mLayout);
        tuLayout = findViewById(R.id.tuLayout);
        wLayout = findViewById(R.id.wLayout);
        thLayout = findViewById(R.id.thLayout);
        fLayout = findViewById(R.id.fLayout);

        pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());


        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    courses = userProfile.courses;
                    Iterator coursesIterator = courses.entrySet().iterator();

                    while (coursesIterator.hasNext()) {
                        Map.Entry mapElement = (Map.Entry)coursesIterator.next();
                        DocumentReference this_doc_Ref = db.collection("Spring-2022").document((String)mapElement.getKey());
                        this_doc_Ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot this_doc = task.getResult();

                                    String courseNo = (String) this_doc.getData().get("number");
                                    String courseName = (String) this_doc.getData().get("name");
                                    String days = (String) this_doc.getData().get("days");
                                    String time = (String) this_doc.getData().get("time");
                                    addCourse(courseNo, courseName, days, time);
                                }
                            }
                        });

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CourseGridPage.this, "Wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void changeToList(View view) {
        Intent courseList = new Intent(CourseGridPage.this, CourseListPage.class);
        startActivity(courseList);
    }

    public void addCourse(String courseNo, String courseName, String days, String time) {
        if (days.equals("") || days.equals(" ") || time.equals("TBA")) {
            return;
        }
//45
        String[] separated_days = time.split(" ");
        String firstTime = separated_days[0];
        String secondTime = separated_days[3];
        int firstTimeHour = Integer.valueOf(firstTime.split(":")[0]);
        int firstTimeMin = Integer.valueOf(firstTime.split(":")[1]);
        int secondTimeHour = Integer.valueOf(secondTime.split(":")[0]);
        int secondTimeMin = Integer.valueOf(secondTime.split(":")[1]);
        if (separated_days[1].equals("pm")) {
            firstTimeHour = firstTimeHour + 12;
        }
        if (separated_days[4].equals("pm")) {
            secondTimeHour = secondTimeHour + 12;
        }

        int startOffSet = firstTimeHour * 60 + firstTimeMin - 480;

        int endOffSet = secondTimeHour * 60 + secondTimeMin - firstTimeHour * 60 - firstTimeMin;

        for (int i = 0; i < days.length(); i++) {
            View thisLayout = null;
            int id = 0;
            if (days.charAt(i) == 'M') {
                thisLayout = mLayout;
                id = R.id.mLayout;
            } else if (days.charAt(i) == 'T') {
                thisLayout = tuLayout;
                id = R.id.tuLayout;
            } else if (days.charAt(i) == 'W') {
                thisLayout = wLayout;
                id = R.id.wLayout;
            } else if (days.charAt(i) == 'R') {
                thisLayout = thLayout;
                id = R.id.thLayout;
            } else if (days.charAt(i) == 'F') {
                thisLayout = fLayout;
                id = R.id.fLayout;
            }

            TextView dayView = new TextView(this);
            dayView.setText(courseNo + "\n" + courseName);
            dayView.setId(View.generateViewId());

            dayView.setBackgroundColor(Color.parseColor("#99ffff"));
            ConstraintLayout.LayoutParams  p = new ConstraintLayout.LayoutParams((int)(83 * pixels), (int) (100 * pixels));

            dayView.setLayoutParams(p);
            ((ConstraintLayout) thisLayout).addView(dayView);
            ConstraintSet set = new ConstraintSet();
            set.clone((ConstraintLayout) thisLayout);
            set.connect(dayView.getId(), ConstraintSet.TOP, id, ConstraintSet.TOP, (int) ((startOffSet / 60 * 45) * pixels));
            set.applyTo((ConstraintLayout) thisLayout);








        }
    }
}