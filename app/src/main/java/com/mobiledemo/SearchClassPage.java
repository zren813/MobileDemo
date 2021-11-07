package com.mobiledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SearchClassPage extends AppCompatActivity implements View.OnClickListener {
    public TextView semester_pick;
    public Button popout_semester_btn, searchCourse_btn,listCourse_btn;
    private EditText edit_search_course;
    String[] sem={"Camera", "Laptop", "Watch","Smartphone",
            "Television"};
    ListPopupWindow listPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_class_page);
        String semester = "Fall Semester 2022";
        semester_pick = (TextView) findViewById(R.id.semester_pick);
        popout_semester_btn = (Button) findViewById(R.id.popout_semester_btn);
        popout_semester_btn.setOnClickListener(this);
        edit_search_course = (EditText) findViewById(R.id.edit_search_course);
        searchCourse_btn = (Button) findViewById(R.id.searchCourse_btn);
        searchCourse_btn.setOnClickListener(this);
        listCourse_btn = (Button) findViewById(R.id.listCourse_btn);
        listCourse_btn.setOnClickListener(this);
        semester_pick.setText(semester);



    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.popout_semester_btn:
                System.out.println("good");
                break;
            case R.id.searchCourse_btn:
                System.out.println("search");
                searchCourse();
                break;
            case R.id.listCourse_btn:
                System.out.println("list");
                Intent listinfo = new Intent(SearchClassPage.this, InfoPage.class);
                startActivity(listinfo);
                break;
        }
    }

    public void searchCourse() {
        // Do something in response to button

        String targetName = edit_search_course.getText().toString();
        //final TextView course_result = (TextView) findViewById(R.id.course_result);
        listCourse_btn.setText(targetName + "    Database Technology");
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


}