package com.example.mcw0805.showerpartners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

//    private DatabaseReference mRootRef;
//    private DatabaseReference mShowersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mRootRef = FirebaseDatabase.getInstance().getReference();
//        mShowersRef = mRootRef.child("showers");

        Button checkShowersButton = (Button) findViewById(R.id.checkShowersButton);
        checkShowersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkShowersIntent = new Intent(MainActivity.this, CheckShowersActivity.class);
                startActivity(checkShowersIntent);
                finish();
            }
        });
    }
}
