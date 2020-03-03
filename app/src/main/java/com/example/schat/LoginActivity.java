package com.example.schat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private EditText UserEmail,UserPassword;
    private Button Login_btn;
    private TextView ForgetPasswordLink,AlreadyHaveAccountLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitialaizeFields();

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void InitialaizeFields() {
        Login_btn = findViewById(R.id.Login_btn);
        UserEmail = findViewById(R.id.LEmail);
        UserPassword = findViewById(R.id.Lpass);
        AlreadyHaveAccountLink = findViewById(R.id.LAlready_have_Account);
        ForgetPasswordLink = findViewById(R.id.LForget_password);

    }
}
