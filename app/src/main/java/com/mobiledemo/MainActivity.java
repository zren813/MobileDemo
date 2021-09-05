package com.mobiledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register, signIn;
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = (TextView) findViewById(R.id.registerText);
        register.setOnClickListener(this);
        editTextEmail = (EditText) findViewById(R.id.editEmail);
        editTextPassword = (EditText) findViewById(R.id.editPassword);
        signIn = (Button) findViewById(R.id.submitButton);
        signIn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.registerText:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.submitButton:
                logIn();
                break;
        }
    }
    public void logIn() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (email.isEmpty()) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email invalid");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this, ProfilePage.class));
                } else {
                   Toast.makeText(MainActivity.this, "Please recheck your email or password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}