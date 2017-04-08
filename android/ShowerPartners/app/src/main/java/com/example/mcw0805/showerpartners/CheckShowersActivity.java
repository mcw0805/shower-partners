package com.example.mcw0805.showerpartners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckShowersActivity extends AppCompatActivity {

    private Button backHomeButton;
    private ListView showersLv;
    private List<String> showersList;
    private ArrayAdapter<String> showerAdapter;
    private DatabaseReference mRootRef;
    private DatabaseReference mShowersRef;
    private DatabaseReference mIsOccupiedRef;
    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_showers);

        backHomeButton = (Button) findViewById(R.id.back_home_btn);
        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackHome();
            }
        });

        showersList = new ArrayList<>();
        showersLv = (ListView) findViewById(R.id.shower_list);

        showerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, showersList);
        showersLv.setAdapter(showerAdapter);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mShowersRef = mRootRef.child("showers");

        mShowersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> shower = (Map<String, Object>) dataSnapshot.getValue();
                String name = (String) shower.get("name");
                String status = null;
                Boolean occupation = (Boolean) shower.get("is_occupied");

                status = occupation ? "Occupied" : "Empty";

                showerAdapter.add(name + "           " + "Status: " + status);
                showerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //mIsOccupiedRef = mShowersRef.child("is_occupied");


    }

    private void goBackHome() {
        Intent goBackHomeIntent = new Intent(CheckShowersActivity.this, MainActivity.class);
        startActivity(goBackHomeIntent);
    }


}
