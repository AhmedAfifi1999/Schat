package com.example.schat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private TextView userProfileName, userProfileStatus;
    private CircleImageView userProfileImage;
    private Button SendMessageRequestBtn;
    private String reciverUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

/*
        reciverUserID = getIntent().getExtras().get("visit_user_id").toString();
        Toast.makeText(this, "User ID: " + reciverUserID, Toast.LENGTH_SHORT).show();
        */

        userProfileName = findViewById(R.id.visit_user_name);
        userProfileStatus = findViewById(R.id.visit_profile_status);
        SendMessageRequestBtn = findViewById(R.id.send_message_request_button);
        userProfileImage = findViewById(R.id.visit_profile_image);


    }
}
