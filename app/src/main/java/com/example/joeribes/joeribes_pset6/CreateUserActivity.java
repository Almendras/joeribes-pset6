package com.example.joeribes.joeribes_pset6;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateUserActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    String email, password;
    EditText emailInput;
    EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

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

    public void createUser(View view) {

        emailInput = (EditText) findViewById(R.id.username);
        passwordInput = (EditText) findViewById(R.id.password);

        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();

        if(password.length() < 6) {
            Toast.makeText(CreateUserActivity.this, "Password is too short, it needs to be at least 6 characters.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("create user", "createUserWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(CreateUserActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(CreateUserActivity.this, "Created user: " + email ,
                                    Toast.LENGTH_SHORT).show();

                            addToDB();
                            Intent overviewIntent = new Intent(getBaseContext(), OverviewActivity.class);
                            startActivity(overviewIntent);
                            finish();
                        }
                    }
                });
    }

    public void addToDB() {
        Customization customItem1 = new Customization("Driver Standings", "2017");
        Customization customItem2 = new Customization("Race Results", "2017");
        Customization customItem3 = new Customization("Race Schedule", "2017");
        Customization customItem4 = new Customization("Driver Information", "2017");

        /*
        CustomizationList customList1 = new CustomizationList("Driver Standings");
        customList1.addCustomization(customItem1);
        CustomizationList customList2 = new CustomizationList("Race Results");
        customList2.addCustomization(customItem2);
        CustomizationList customList3 = new CustomizationList("Race Schedule");
        customList3.addCustomization(customItem3);
        CustomizationList customList4 = new CustomizationList("Driver Information");
        customList4.addCustomization(customItem4);
        */


        HashMap<String, Customization> map = new HashMap<>();
        map.put("Driver Standings 2017", customItem1);
        map.put("Race Results 2017", customItem2);
        map.put("Race Schedule 2017", customItem3);
        map.put("Driver Information 2017", customItem4);

        mDatabase.child(user.getUid()).setValue(map);
    }
}