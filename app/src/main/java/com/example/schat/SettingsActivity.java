package com.example.schat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private Button UpdateAccountSettings;
    private EditText userName, userStatus;
    private CircleImageView userProfileImage;
    private String CurrentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private static final int GallaryPick = 1;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mAuth = FirebaseAuth.getInstance();

        RootRef = FirebaseDatabase.getInstance().getReference();
        CurrentUserID = mAuth.getCurrentUser().getUid();
        InitializeField();
        userName.setVisibility(View.INVISIBLE);

        UpdateAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSetting();
            }
        });

        RetrieveUserInfo();

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GallaryPick);
            }
        });


    }

    private void RetrieveUserInfo() {
        RootRef.child("Users").child(CurrentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChild("name") && dataSnapshot.hasChild("image")) {

                    String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                    String retrieveStatus = dataSnapshot.child("status").getValue().toString();

                    String retrieveprofileImage = dataSnapshot.child("image").getValue().toString();
                    userName.setText(retrieveUserName);
                    userStatus.setText(retrieveStatus);

                } else if (dataSnapshot.exists() && dataSnapshot.hasChild("name")) {

                    String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                    String retrieveStatus = dataSnapshot.child("status").getValue().toString();
                    userName.setText(retrieveUserName);
                    userStatus.setText(retrieveStatus);

                } else {
                    userName.setVisibility(View.VISIBLE);
                    Toast.makeText(SettingsActivity.this, "Pleas set & Update Your Profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void UpdateSetting() {
        String setUserName = userName.getText().toString();
        String setStatus = userStatus.getText().toString();
        if (TextUtils.isEmpty(setUserName)) {
            Toast.makeText(this, "Please Enter UserName", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(setStatus)) {
            Toast.makeText(this, "Please Enter Your Status", Toast.LENGTH_SHORT).show();


        } else {
            HashMap<String, String> profileMap = new HashMap<>();
            profileMap.put("uid", CurrentUserID);
            profileMap.put("name", setUserName);
            profileMap.put("status", setStatus);

            RootRef.child("Users").child(CurrentUserID).setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        SendUserToMainActivity();
                        Toast.makeText(SettingsActivity.this, "profile  Updated Successfully ... ", Toast.LENGTH_SHORT).show();

                    } else {
                        String Message = task.getException().toString();
                        Toast.makeText(SettingsActivity.this, "Error : " + Message, Toast.LENGTH_SHORT).show();


                    }

                }
            });
        }

    }

    private void InitializeField() {
        UpdateAccountSettings = findViewById(R.id.update_settings_btn);
        userName = findViewById(R.id.set_user_name);
        userStatus = findViewById(R.id.set_profile_status);
        userProfileImage = findViewById(R.id.set_profile_image);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void SendUserToMainActivity() {

        Intent mainIntent = new Intent(SettingsActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
