package com.example.schat.View.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView userProfileImage;
    private TextView userProfileName, userProfileStatue;
    private Button SendMessageRequestBtn;
    private String reciverUserID, SenderUserID, Current_Status;

    private FirebaseAuth mAuth;
    private DatabaseReference UserRef, ChatRequestRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Current_Status = "new";
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        ChatRequestRef = FirebaseDatabase.getInstance().getReference().child("Chat Requests");
        SenderUserID = mAuth.getCurrentUser().getUid();

        reciverUserID = getIntent().getExtras().get("visit_user_id").toString();


        userProfileImage = findViewById(R.id.visit_profile_image);
        userProfileName = findViewById(R.id.visit_user_name);
        userProfileStatue = findViewById(R.id.visit_profile_status);
        SendMessageRequestBtn=findViewById(R.id.send_message_request_button);
        RetrieveUserInfo();
    }

    private void RetrieveUserInfo() {
        UserRef.child(reciverUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserRef.child(reciverUserID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //get image and name and status for reciver form Firebase
                        if ((dataSnapshot.exists()) && dataSnapshot.hasChild("image")) {
                            String userImage = dataSnapshot.child("image").getValue().toString();
                            String userName = dataSnapshot.child("name").getValue().toString();
                            String userStatus = dataSnapshot.child("status").getValue().toString();
                            //Solve Link Image in Firebase Then remove Comment >
                            //Picasso.get().load(userImage).placeholder(R.drawable.profile_image).into(userProfileImage);
                            userProfileName.setText(userName);
                            userProfileStatue.setText(userStatus);
                            //Temp
                            userProfileImage.setImageResource(R.drawable.profile_image);
                            ManagerChatRequests();
                        } else {

                            String userName = dataSnapshot.child("name").getValue().toString();
                            String userStatus = dataSnapshot.child("status").getValue().toString();


                            userProfileName.setText(userName);
                            userProfileStatue.setText(userStatus);
                            ManagerChatRequests();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void ManagerChatRequests() {

        ChatRequestRef.child(SenderUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(reciverUserID)) {
                    String request_type = dataSnapshot.child(reciverUserID).child("request_type").getValue().toString();
                    if (request_type.equals("send")) {

                        SendMessageRequestBtn.setText("Cancel Chat Request");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (!SenderUserID.equals(reciverUserID)) {

            SendMessageRequestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SendMessageRequestBtn.setEnabled(false);

                    if (Current_Status.equals("new")) {

                        SendChatRequest();

                    }

                }
            });

        } else {
            SendMessageRequestBtn.setVisibility(View.INVISIBLE);

        }

    }

    public void SendChatRequest() {
        ChatRequestRef.child(SenderUserID).child(reciverUserID).child("request_type").setValue("send").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ChatRequestRef.child(reciverUserID).child(SenderUserID).child("request_type").setValue("received").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                SendMessageRequestBtn.setEnabled(true);
                                Current_Status = "request_send";
                                SendMessageRequestBtn.setText("Cancel Chat Request");

                            } else {


                            }
                        }
                    });


                }
            }
        });


    }


}
