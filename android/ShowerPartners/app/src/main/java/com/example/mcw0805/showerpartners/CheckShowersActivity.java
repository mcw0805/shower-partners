package com.example.mcw0805.showerpartners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class CheckShowersActivity extends AppCompatActivity {

    private Button backHomeButton;
    private ListView showerOccupiedLv;
    private ListView showerEmptyLv;
    private List<String> occupiedShowersList;
    private List<String> emptyShowersList;
    private ArrayAdapter<String> showerOccupiedAdaptor;
    private ArrayAdapter<String> showerEmptyAdaptor;
    private DatabaseReference mRootRef;
    private DatabaseReference mShowersRef;
    private Map<String, String> showerMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_showers);

        showerMap = new HashMap<>();

        //instantiates Button, set listener
        backHomeButton = (Button) findViewById(R.id.back_home_btn);
        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackHome();
            }
        });

        //instantiates ArrayList
        occupiedShowersList = new ArrayList<>();
        emptyShowersList = new ArrayList<>();

        //instantiates ListView
        showerOccupiedLv = (ListView) findViewById(R.id.occupied_shower_list);
        showerEmptyLv = (ListView) findViewById(R.id.empty_shower_list);

        //instantiates and sets Adaptor
        showerOccupiedAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, occupiedShowersList);
        showerOccupiedLv.setAdapter(showerOccupiedAdaptor);

        showerEmptyAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, emptyShowersList);
        showerEmptyLv.setAdapter(showerEmptyAdaptor);

        //Retreive data from Firebase
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mShowersRef = mRootRef.child("showers");

        mShowersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> shower = (Map<String, Object>) dataSnapshot.getValue();
                String name = (String) shower.get("name");
                Boolean occupation = (Boolean) shower.get("is_occupied");
                showerMap.put(name, dataSnapshot.getKey());

                if (occupation) {
                    if (!occupiedShowersList.contains(name)) {
                        try {
                            occupiedShowersList.remove(name);
                        } catch (NoSuchElementException ne) {

                        }
                        showerOccupiedAdaptor.add(name);
                        showerOccupiedAdaptor.notifyDataSetChanged();
                    }
                } else {
                    if (!emptyShowersList.contains(name)) {
                        showerEmptyAdaptor.add(name);
                        showerEmptyAdaptor.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> shower = (Map<String, Object>) dataSnapshot.getValue();
                String name = (String) shower.get("name");
                Boolean occupation = (Boolean) shower.get("is_occupied");
                Boolean out = (Boolean) shower.get("get_out");

                if (!occupation) {
                    if (!emptyShowersList.contains(name)) {
                        showerEmptyAdaptor.add(name);
                        showerEmptyAdaptor.notifyDataSetChanged();
                        showerOccupiedAdaptor.remove(name);
                        showerOccupiedAdaptor.notifyDataSetChanged();
                    }

                } else {
                    if (!occupiedShowersList.contains(name)) {
                        showerOccupiedAdaptor.add(name);
                        showerOccupiedAdaptor.notifyDataSetChanged();
                        showerEmptyAdaptor.remove(name);
                        showerEmptyAdaptor.notifyDataSetChanged();
                    }
                }

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

        showerOccupiedLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String shower = occupiedShowersList.get(position);
//                occupiedShowersList.remove(shower);
                mShowersRef.getRef().child(showerMap.get(shower)).child("get_out").setValue(true);
                showerOccupiedAdaptor.notifyDataSetChanged();
            }
        });


    }

    private void goBackHome() {
        Intent goBackHomeIntent = new Intent(CheckShowersActivity.this, MainActivity.class);
        startActivity(goBackHomeIntent);
    }

}
