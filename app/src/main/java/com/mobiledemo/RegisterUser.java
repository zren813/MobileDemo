package com.mobiledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private TextView login, registerUser;
    private EditText editTextNickName, editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();

        login = (TextView) findViewById(R.id.editReturnLogIn);
        login.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.editRegisterButton);
        registerUser.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.editRegisterEmail);
        editTextNickName = (EditText) findViewById(R.id.editNickName);
        editTextPassword = (EditText) findViewById(R.id.editRegisterPassword);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.editReturnLogIn:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.editRegisterButton:
                registerUser();
                break;
        }
    }
    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String nickName = editTextNickName.getText().toString().trim();

        if (nickName.isEmpty()) {
            editTextNickName.setError("Nick name is required");
            editTextNickName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email address is required");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Invalid Email Address");
            editTextEmail.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(nickName, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(RegisterUser.this, SearchClassPage.class));
                                    } else {
                                        Toast.makeText(RegisterUser.this, "Registered failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(RegisterUser.this, "Registered failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }

}