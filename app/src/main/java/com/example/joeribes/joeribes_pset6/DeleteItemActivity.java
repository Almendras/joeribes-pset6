package com.example.joeribes.joeribes_pset6;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DeleteItemActivity extends AppCompatActivity {

    ArrayList<Customization> customItems;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    String itemSelected;
    ArrayList<String> items;
    Toolbar toolbarDelete;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        // Database information
        mAuth = FirebaseAuth.getInstance();
        setListener();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        customItems = (ArrayList<Customization>) this.getIntent().getSerializableExtra("deleteCustomItem");
        createItemsArray(customItems);
        createSpinner(items);

        toolBar();
    }

    public void toolBar() {
        toolbarDelete = (Toolbar) findViewById(R.id.toolbar_custom);
        setSupportActionBar(toolbarDelete);
        getSupportActionBar().setTitle("Delete item");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_custom, menu);
        setTitle("Delete item");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent = new Intent(getBaseContext(), OverviewActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    // Create an Arraylist<String> for the spinner
    public void createItemsArray(ArrayList<Customization> customItems) {
        items = new ArrayList<>();
        for(Customization item : customItems) {
            items.add(item.getItem() + " " + item.getSeason());
        }
    }

    // Creates a spinner with the customization items of the user
    public void createSpinner(ArrayList<String> items) {
        final Spinner spinItem = (Spinner) findViewById(R.id.items_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, items);
        spinItem.setAdapter(adapter);

        spinItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                itemSelected = spinItem.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }


    // Deletes the appropriate item from the database
    public void deleteFromDatabase(View view) {
        if (itemSelected != null) {
            mDatabase.child(mAuth.getCurrentUser().getUid()).child(itemSelected).removeValue();

            Toast.makeText(DeleteItemActivity.this, "Deleted " + itemSelected,
                    Toast.LENGTH_SHORT).show();
        }
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
}
