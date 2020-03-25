package com.example.schat.View.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schat.Database.Firebase;
import com.example.schat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private Button Register_btn;
    private EditText UserEmail, UserPassword;
    private TextView AlreadyHaveAccountLink, ForgetPasswordLink;
    //    private FirebaseAuth mAuth;
//    private DatabaseReference RootRef;
    private ProgressBar loadingBar;
    private ActionBar actionBar;
    private Firebase firebase;
    private boolean isClicked = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        mAuth = FirebaseAuth.getInstance();
//        RootRef = FirebaseDatabase.getInstance().getReference();
        InitializeFields();
        mtoolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(mtoolbar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Login");
        actionBar.setDisplayHomeAsUpEnabled(true);

        AlreadyHaveAccountLink.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (!isClicked) {
                    actionBar.setTitle("Register");
                    AlreadyHaveAccountLink.setText("Already have an Account ?");
                    Register_btn.setText("Sign up");
                    isClicked = true;
                } else {
                    actionBar.setTitle("Login");
                    AlreadyHaveAccountLink.setText("Don't have an Account ?");
                    Register_btn.setText("Sign In");
                    isClicked = false;
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }
        });


        Register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register_btn.setVisibility(View.INVISIBLE);
                loadingBar.setVisibility(View.VISIBLE);
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "please Enter Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "please Enter password", Toast.LENGTH_SHORT).show();
        } else {
//            loadingBar.setTitle("Creating New Account");
//            loadingBar.setMessage("Please Wait , while we are Creating An Account  ");
//            loadingBar.setCanceledOnTouchOutside(true);
//            loadingBar.show();
//            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//
//                    if (task.isSuccessful()) {
//                        String CurrentUserID = mAuth.getCurrentUser().getUid();
//                        RootRef.child("Users").child(CurrentUserID).setValue("");
            boolean isSuccessful = firebase.login(email, password);
            if (isSuccessful) {
                SendUserToMainActivity();
                if (Register_btn.getText().toString().toLowerCase().equals("sign up"))
                    Toast.makeText(RegisterActivity.this, "Account Created Successful", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(RegisterActivity.this, "Logged In Successful ...", Toast.LENGTH_SHORT).show();
            } else {
//                        String Message = task.getException().toString();
                Toast.makeText(RegisterActivity.this, "Filed " + actionBar.getTitle(), Toast.LENGTH_SHORT).show();

            }


        }
//            });

        loadingBar.setVisibility(View.INVISIBLE);
        Register_btn.setVisibility(View.VISIBLE);
    }


    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }


    private void InitializeFields() {
        Register_btn = findViewById(R.id.register_login);
        UserEmail = findViewById(R.id.Email);
        UserPassword = findViewById(R.id.pass);
        AlreadyHaveAccountLink = findViewById(R.id.RAlready_have_Account);
        ForgetPasswordLink = findViewById(R.id.RForget_password);
        loadingBar = findViewById(R.id.loadingBar);
        firebase = new Firebase();
    }


}
