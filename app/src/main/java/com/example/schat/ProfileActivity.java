package com.example.schat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private TextView userProfileName, userProfileStatus;
    private CircleImageView userProfileImage;
    private Button SendMessageRequestBtn;
    private String reciverUserID, Sender_UserID, Current_state;

    private DatabaseReference UserRef, ChatRequestRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        ChatRequestRef = FirebaseDatabase.getInstance().getReference().child("Chat Requests");
        mAuth = FirebaseAuth.getInstance();

        reciverUserID = getIntent().getExtras().get("visit_user_id").toString();
        Sender_UserID = mAuth.getCurrentUser().getUid();

        userProfileName = findViewById(R.id.visit_user_name);
        userProfileStatus = findViewById(R.id.visit_profile_status);
        SendMessageRequestBtn = findViewById(R.id.send_message_request_button);
        userProfileImage = findViewById(R.id.visit_profile_image);
        SendMessageRequestBtn = findViewById(R.id.send_message_request_button);
        Current_state = "new";


        RetrieveUserInfo();


    }

    private void RetrieveUserInfo() {

        UserRef.child(reciverUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if ((dataSnapshot.exists() && dataSnapshot.hasChild("image"))) {

                    String image = dataSnapshot.child("image").getValue().toString();
                    String userName = dataSnapshot.child("name").getValue().toString();
                    String userStatus = dataSnapshot.child("status").getValue().toString();
                    // First  solve Link Image In Firebase Then Delete this commint
                    Picasso.get().load(image).placeholder(R.drawable.profile_image).into(userProfileImage);
                    userProfileName.setText(userName);
                    userProfileStatus.setText(userStatus);

                    ManageChatRequest();
                } else {
                    String userName = dataSnapshot.child("name").getValue().toString();
                    String userStatus = dataSnapshot.child("status").getValue().toString();

                    userProfileName.setText(userName);
                    userProfileStatus.setText(userStatus);

                    ManageChatRequest();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ManageChatRequest() {

        ChatRequestRef.child(Sender_UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(reciverUserID)) {

                    String request_type = dataSnapshot.child(reciverUserID).child("request_type").getValue().toString();

                    if (request_type.equals("send")) {

                        Current_state = "request_send";
                        SendMessageRequestBtn.setText("Cancel Chat Request");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (!Sender_UserID.equals(reciverUserID)) {
            SendMessageRequestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SendMessageRequestBtn.setEnabled(false);

                    if (Current_state.equals("new")) {

                        SendChatRequest();

                    }
                }
            });

        } else {

            SendMessageRequestBtn.setVisibility(View.INVISIBLE);

        }

    }

    private void SendChatRequest() {


        ChatRequestRef.child(Sender_UserID).child(reciverUserID).child("request_type").setValue("send").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    ChatRequestRef.child(reciverUserID).child(Sender_UserID).child("request_type").setValue("received").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                SendMessageRequestBtn.setEnabled(true);
                                Current_state = "request_send";
                                SendMessageRequestBtn.setText("Cancel Chat Request");

                            }


                        }
                    });

                }
            }
        });
    }


}
