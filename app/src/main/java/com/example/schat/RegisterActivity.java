package com.example.schat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private Button Register_btn;
    private EditText UserEmail, UserPassword;
    private TextView AlreadyHaveAccountLink,ForgetPasswordLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mtoolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(mtoolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Register");
        actionBar.setDisplayHomeAsUpEnabled(true);




    }


    private void InitialaizeFields() {
        Register_btn = findViewById(R.id.Register_btn);
        UserEmail = findViewById(R.id.Email);
        UserPassword = findViewById(R.id.pass);
        AlreadyHaveAccountLink = findViewById(R.id.RAlready_have_Account);
        ForgetPasswordLink = findViewById(R.id.RForget_password);

    }

}
