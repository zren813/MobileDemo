package com.mobiledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends AppCompatActivity {
    private String uid;
    private FirebaseUser user;
    private DatabaseReference reference;

    public static final String EXTRA_Name = "com.example.MobileDemo.Name";
    public static final String EXTRA_Email = "com.example.MobileDemo.Email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        uid = user.getUid();

        final TextView nickNameField = (TextView) findViewById(R.id.nickNameField);
        final TextView emailField = (TextView) findViewById(R.id.emailField);

        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String nickName = userProfile.nickName;
                    String email = userProfile.email;
                    nickNameField.setText(nickName);
                    emailField.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfilePage.this, "Wrong", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.searchNow:
                searchName();
                break;
        }

    }

    /** Called when the user taps the Search button */
    public void searchName() {
        // Do something in response to button
        Intent intent = new Intent(this, DisplaySearchResult.class);
        EditText editText = (EditText) findViewById(R.id.searchName);
        String targetName = editText.getText().toString();
        intent.putExtra(EXTRA_Name, targetName);

        String uid;
        FirebaseUser user;
        DatabaseReference reference;
        final String[] targetEmail = new String[1];
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        uid = user.getUid();
        Query query = reference.child("Users").orderByChild("nickName").equalTo(targetName);
        query.addValueEventListener(new ValueEventListener() {
            private static final String TAG = "No such Nickname";

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User target = data.getValue(User.class);
                    targetEmail[0] = target.email;
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        intent.putExtra(EXTRA_Email, targetEmail[0]);
        startActivity(intent);
    }


}