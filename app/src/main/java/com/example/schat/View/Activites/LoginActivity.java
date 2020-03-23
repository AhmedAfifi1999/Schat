package com.example.schat.View.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private EditText UserEmail, UserPassword;
    private Button Login_btn;
    private TextView ForgetPasswordLink, DontHaveAccountLink;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        InitializeFields();

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Login");
        actionBar.setDisplayHomeAsUpEnabled(true);


        DontHaveAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });

        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AllowUserToLogin();
            }
        });

    }

    private void SendUserToMainActivity() {

        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }


    private void AllowUserToLogin() {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            UserEmail.setError("please Enter Email");
//            Toast.makeText(this, "please Enter Email", Toast.LENGTH_SHORT).show();
            if (TextUtils.isEmpty(password)) {
                UserPassword.setError("please Enter password");
//                Toast.makeText(this, "please Enter password", Toast.LENGTH_SHORT).show();
            }
        } else {
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please Wait , while we are login ");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        SendUserToMainActivity();
                        Toast.makeText(LoginActivity.this, "Logged In Successful ...", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                    } else {


                        String Message = task.getException().toString();
                        Toast.makeText(LoginActivity.this, "Error Message :" + Message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                    }

                }
            });
        }


    }


    private void InitializeFields() {
        Login_btn = findViewById(R.id.Login_btn);
        UserEmail = findViewById(R.id.LEmail);
        UserPassword = findViewById(R.id.Lpass);
        DontHaveAccountLink = findViewById(R.id.LAlready_have_Account);
        ForgetPasswordLink = findViewById(R.id.LForget_password);
        loadingBar = new ProgressDialog(this);
    }
}
