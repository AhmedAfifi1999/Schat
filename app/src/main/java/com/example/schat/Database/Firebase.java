package com.example.schat.Database;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.schat.View.Activites.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase {
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    public Firebase() {
        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
    }

    public boolean login(String Email, String Password) {
        final boolean[] isSuccessful = {false};
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String CurrentUserID = mAuth.getCurrentUser().getUid();
                    RootRef.child("Users").child(CurrentUserID).setValue("");
                    isSuccessful[0] = true;
                }
            }
        });
        return isSuccessful[0];
    }

}