package com.example.schat.View.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.schat.R;

public class ProfileActivity extends AppCompatActivity {


    private String reciverUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        reciverUserID = getIntent().getExtras().get("visit_user_id").toString();
        Toast.makeText(this, "User ID: " + reciverUserID, Toast.LENGTH_SHORT).show();

    }
}
