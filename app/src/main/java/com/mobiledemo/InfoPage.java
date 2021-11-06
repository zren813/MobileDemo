package com.mobiledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class InfoPage extends AppCompatActivity implements View.OnClickListener {
    private String uid;
    private FirebaseUser user;
    private DatabaseReference reference;
    private EditText editTextFirstName, editTextLastName, editTextMajor;
    private Button updateInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        uid = user.getUid();
        final TextView emailTextView = (TextView) findViewById(R.id.emailTextView);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextMajor = (EditText) findViewById(R.id.editTextMajor);
        updateInfoButton = (Button) findViewById(R.id.updateInfoButton);
        updateInfoButton.setOnClickListener(this);

        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String firstName = userProfile.firstName;
                    String lastName = userProfile.lastName;
                    editTextFirstName.setText(firstName);
                    editTextLastName.setText(lastName);
                    String major = userProfile.major;
                    editTextMajor.setText(major);
                    emailTextView.setText(userProfile.email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InfoPage.this, "Wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.updateInfoButton:
                updateInfo();
        }
    }
    public void updateInfo() {
        reference.child(uid).child("firstName").setValue(editTextFirstName.getText().toString());
        reference.child(uid).child("lastName").setValue(editTextLastName.getText().toString());
        reference.child(uid).child("major").setValue(editTextMajor.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(InfoPage.this, "Update Successful", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(InfoPage.this, "Update Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void onInfoButton(View view) {
        Intent info = new Intent(InfoPage.this, InfoPage.class);
        startActivity(info);
    }

    public void onSearchButton(View view) {
        Intent search = new Intent(InfoPage.this, SearchClassPage.class);
        startActivity(search);
    }

    public void onSettingButton(View view) {
        Intent setting = new Intent(InfoPage.this, SettingPage.class);
        startActivity(setting);
    }
    public void onCourseListButton(View view) {
        Intent courseList = new Intent(InfoPage.this, CourseListPage.class);
        startActivity(courseList);
    }
}