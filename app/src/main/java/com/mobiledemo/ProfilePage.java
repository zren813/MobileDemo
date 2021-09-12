package com.mobiledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfilePage extends AppCompatActivity implements View.OnClickListener {
    private String uid;
    private FirebaseUser user;
    private DatabaseReference reference;
    private EditText editMoment, editSearchUser;
    private Button editTextButton, searchUserButton;
    private TextView searchedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        uid = user.getUid();

        final TextView nickNameField = (TextView) findViewById(R.id.nickNameField);
        final TextView emailField = (TextView) findViewById(R.id.emailField);
        editMoment = (EditText) findViewById(R.id.editTextMoment);
        editTextButton = (Button) findViewById(R.id.editMomentButton);
        editTextButton.setOnClickListener(this);

        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String nickName = userProfile.nickName;
                    String email = userProfile.email;
                    nickNameField.setText(nickName);
                    emailField.setText(email);
                    String moment = userProfile.moment;
                    editMoment.setText(moment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfilePage.this, "Wrong", Toast.LENGTH_LONG).show();
            }
        });

        editSearchUser = (EditText) findViewById(R.id.textEditSearchUser);
        searchedUser = (TextView) findViewById(R.id.searchedUserInfo);
        searchUserButton = (Button) findViewById(R.id.searchUserButton);
        searchUserButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.editMomentButton:
                updateMoment();
            case R.id.searchUserButton:
                searchUser();
        }
    }

    public void updateMoment() {
        System.out.println(editMoment.getText());
        reference.child(uid).child("moment").setValue(editMoment.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ProfilePage.this, "Update Successful", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ProfilePage.this, "Update Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void searchUser() {
        // Do something in response to button
        EditText editText = (EditText) findViewById(R.id.textEditSearchUser);
        String targetName = editText.getText().toString();
        final TextView searchedUserInfo = (TextView) findViewById(R.id.searchedUserInfo);
        final TextView searchedEmail = (TextView) findViewById(R.id.searchedEmail);
        final TextView searchedMoment = (TextView) findViewById(R.id.searchedMoment);

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
                    searchedUserInfo.setText(targetName);
                    searchedEmail.setText(targetEmail[0]);
                    if (targetMoment[0] == null) {
                        searchedMoment.setText("No moment currently");
                    } else {
                        searchedMoment.setText(targetMoment[0]);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(ProfilePage.this, "No such user", Toast.LENGTH_LONG).show();
                // ...
            }
        });
    }
}