package com.mobiledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class SearchClassPage extends AppCompatActivity implements View.OnClickListener, ItemClickListener {
    public Button searchCourse_btn, confirm_Button;
    private EditText edit_search_course;
    String[] sem={"Camera", "Laptop", "Watch","Smartphone",
            "Television"};
    ListPopupWindow listPopupWindow;
    Spinner spinnerYear, spinnerSemester;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String uid;
    private FirebaseFirestore db;
    private List<String> courseNo;
    private List<String> courseName;
    private List<String> professorName;
    private RecyclerView recyclerView;
    private List<String> regNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        uid = user.getUid();

        db = FirebaseFirestore.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_class_page);
        recyclerView = findViewById(R.id.searchedRecyclerView);

        spinnerYear=findViewById(R.id.yearSpinner);
        spinnerSemester = findViewById(R.id.seasonSpinner);
        ArrayAdapter<CharSequence> yearAdapter=ArrayAdapter.createFromResource(this, R.array.years, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> semesterAdapter=ArrayAdapter.createFromResource(this, R.array.semesters, android.R.layout.simple_spinner_item);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerYear.setAdapter(yearAdapter);
        spinnerSemester.setAdapter(semesterAdapter);





        edit_search_course = (EditText) findViewById(R.id.edit_search_course);
        searchCourse_btn = (Button) findViewById(R.id.searchCourse_btn);
        confirm_Button = (Button) findViewById(R.id.confirmButton);
        confirm_Button.setOnClickListener(this);
        searchCourse_btn.setOnClickListener(this);




        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String interestSemester = userProfile.interestSemester;
                    String interestYear = userProfile.interestYear;

                    if (interestSemester != null && interestYear != null) {
                        spinnerYear.setSelection(getIndex(spinnerYear, interestYear));
                        spinnerSemester.setSelection(getIndex(spinnerSemester, interestSemester));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchClassPage.this, "Wrong", Toast.LENGTH_LONG).show();
            }
        });



    }
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.searchCourse_btn:
                searchCourse();
                break;
            case R.id.confirmButton:
                updateYearAndSemester();
        }
    }
    public void updateYearAndSemester() {
        reference.child(uid).child("interestSemester").setValue(spinnerSemester.getSelectedItem().toString());
        reference.child(uid).child("interestYear").setValue(spinnerYear.getSelectedItem().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SearchClassPage.this, "Update Successful", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SearchClassPage.this, "Update Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void searchCourse() {
        // Do something in response to button

        String targetName = edit_search_course.getText().toString();
        courseNo = new ArrayList<>();
        courseName = new ArrayList<>();
        professorName = new ArrayList<>();
        regNo = new ArrayList<>();
        CollectionReference docRef = db.collection("Spring-2022");
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot query = task.getResult();
                    for (DocumentSnapshot docus : query) {
                        String this_course_number = (String) docus.getData().get("number");
                        if (this_course_number.contains(edit_search_course.getText())) {
                            courseNo.add((String) docus.getData().get("number"));
                            courseName.add((String) docus.getData().get("name"));
                            professorName.add((String) docus.getData().get("professor"));
                            regNo.add((String) docus.getData().get("regnumber"));
                        }
                    }
                    getAdapter();
                }
            }
        });

//        DocumentReference docRef = db.collection("Spring-2022").document("03NOA7Xyf6EpDPWtJOWM");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    System.out.println(document);
//                }
//            }
//        });

        //final TextView course_result = (TextView) findViewById(R.id.course_result);

        //System.out.println(course_result.getText());
        /*
        DatabaseReference reference;
        final String[] targetEmail = new String[1];
        final String[] targetMoment = new String[1];
        reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("Users").orderByChild("nickName").equalTo(targetName);
        query.addValueEventListener(new ValueEventListener() {
            private static final String TAG = "No such Nickname";

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User target = data.getValue(User.class);
                    targetEmail[0] = target.email;
                    targetMoment[0] = target.moment; // moment null check?
                }
                // display the result to the user
                if (targetEmail[0] == null) {
                    searchedUserInfo.setText("No such user");
                } else {
                    searchedUserInfo.setText("Nick Name: " + targetName);
                    searchedEmail.setText("Email:  " + targetEmail[0]);
                    if (targetMoment[0] == null) {
                        searchedMoment.setText("No moment currently");
                    } else {
                        searchedMoment.setText("Moment: " + targetMoment[0]);
                    }
                }*/
            }
    public void getAdapter(){
        CourseListAdapter courseListAdapter = new CourseListAdapter(this, courseNo, courseName, professorName, regNo);
        courseListAdapter.setClickListener(this);
        recyclerView.setAdapter(courseListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    public void onInfoButton(View view) {
        Intent info = new Intent(SearchClassPage.this, InfoPage.class);
        startActivity(info);
    }

    public void onSearchButton(View view) {
        Intent search = new Intent(SearchClassPage.this, SearchClassPage.class);
        startActivity(search);
    }

    public void onSettingButton(View view) {
        Intent setting = new Intent(SearchClassPage.this, SettingPage.class);
        startActivity(setting);
    }
    public void onCourseListButton(View view) {
        Intent courseList = new Intent(SearchClassPage.this, CourseListPage.class);
        startActivity(courseList);
    }
    public void onAddClass(View view) {
        Toast.makeText(SearchClassPage.this, "Class Added", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onItemClick(View view, int position) {
        String this_course_reg_no = regNo.get(position);
        Intent courseIntent = new Intent(SearchClassPage.this, CourseDetailPage.class);
        courseIntent.putExtra("regNo", this_course_reg_no);
        startActivity(courseIntent);
    }
}