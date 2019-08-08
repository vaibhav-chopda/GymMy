package com.example.vaibhavchopda.gymmy24v11;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileViewer extends AppCompatActivity {

    //you will need to make changes here as well if needed.
    CircleImageView image_profile;
    TextView mUsername;
    FloatingActionButton edit;

    FirebaseUser fuser;
    DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_viewer);

        edit = findViewById(R.id.editButton);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openProfileEditor = new Intent(UserProfileViewer.this, ProfileEditorActivity.class);
                startActivity(openProfileEditor);
            }
        });

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        dref = FirebaseDatabase.getInstance().getReference().child("Profiles");

        //add your code for firebase database Sam
    }
}
