package com.example.joeribes.joeribes_pset6;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mAuth = FirebaseAuth.getInstance();
        setListener();

        // Initialize database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    private void setListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("signed in", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("signed out", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void addToDB(View view) {
        Customization customItem1 = new Customization("Driver Standings", "2017");
        Customization customItem2 = new Customization("Race Results", "2017");
        Customization customItem3 = new Customization("Race Schedule", "2017");
        Customization customItem4 = new Customization("Driver Information", "2017");


        HashMap<String, Customization> map = new HashMap<>();
        map.put("Driver Standings", customItem1);
        map.put("Race Results", customItem2);
        map.put("Race Schedule", customItem3);
        map.put("Driver Information", customItem4);

        mDatabase.child(user.getUid()).setValue(map);
    }

    public void getFromDB(View view) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get out object out od the database

                Formula1 aFormula1 = dataSnapshot.child(user.getUid()).getValue(Formula1.class);

                Toast.makeText(SecondActivity.this, aFormula1.name,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting the data failed, log a message
                Log.w("Cancel", "Something went wrong: ", databaseError.toException());
            }
        };

        mDatabase.addValueEventListener(postListener);
    }


}
