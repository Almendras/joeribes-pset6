package com.example.joeribes.joeribes_pset6;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String email, password;
    EditText emailInput, passwordInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        setListener();
    }

    // This will handle the textViews
    public void textViewsHandler() {
        emailInput = (EditText) findViewById(R.id.username);
        passwordInput = (EditText) findViewById(R.id.password);

        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
    }


    private void setListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("signed in", "onAuthStateChanged:signed_in:" + user.getUid());
                    overviewIntent();

                } else {
                    // User is signed out
                    Log.d("signed out", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    // This will invoke the overview activity
    private void overviewIntent() {
        Intent overviewIntent = new Intent(getBaseContext(), OverviewActivity.class);
        startActivity(overviewIntent);
        finish();
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

    // This will start a new activity CreateUser
    public void createUser(View view) {
        Intent createUserIntent = new Intent(getBaseContext(), CreateUserActivity.class);
        startActivity(createUserIntent);
        finish();
    }

    // Login the appropriate user
    public void logInUser(View view) {
        textViewsHandler();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("sign in", "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w("email", "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Valid authentication credentials were not provided.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Logged in user: " + email ,
                                    Toast.LENGTH_SHORT).show();

                            overviewIntent();
                        }
                    }
                });
    }


}
