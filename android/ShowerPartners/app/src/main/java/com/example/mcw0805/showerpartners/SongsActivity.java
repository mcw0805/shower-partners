package com.example.mcw0805.showerpartners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class SongsActivity extends AppCompatActivity {

    private ListView songsLv;
    private List<String> songsList;
    private ArrayAdapter<String> songsAdaptor;
    private Button backHomeButton;
    private DatabaseReference mRootRef;
    private DatabaseReference mMusicRef;
    private DatabaseReference mMainSongRef;
    private Map<String, String> musicMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        backHomeButton = (Button) findViewById(R.id.back_home_btn2);
        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackHome();
            }
        });

        songsLv = (ListView) findViewById(R.id.songs_list);

        songsList = new ArrayList<>();

        songsAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsList);
        songsLv.setAdapter(songsAdaptor);

        musicMap = new HashMap<>();

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mMusicRef = mRootRef.child("music");
        mMainSongRef = mRootRef.child("main_song_path");

        mMusicRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> currSong = (Map<String, String>) dataSnapshot.getValue();
                String songName = currSong.get("name");
                String songPath = currSong.get("path");
                musicMap.put(songName, songPath);

                //songsList.add(songName);

                songsAdaptor.add(songName);
                songsAdaptor.notifyDataSetChanged();
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

    }

    private void goBackHome() {
        Intent goBackHomeIntent = new Intent(SongsActivity.this, MainActivity.class);
        startActivity(goBackHomeIntent);
    }


}
