package com.example.vaibhavchopda.gymmy24v11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileEditorActivity extends AppCompatActivity {

    EditText newName, newAge, newContact, newWorkouts;
    Button saveData;

    DatabaseReference dbaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editor);

        newName = findViewById(R.id.nameEditor);
        newAge = findViewById(R.id.ageEditor);
        newContact = findViewById(R.id.contactEditor);
        newWorkouts = findViewById(R.id.workoutsEditor);

        dbaseReference = FirebaseDatabase.getInstance().getReference("Profiles");

        saveData = findViewById(R.id.saveButton);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateName = newName.getText().toString();
                String updateAge = newAge.getText().toString();
                String updateContact = newContact.getText().toString();
                String updateWorkouts = newWorkouts.getText().toString();

                //checking if the fields are empty. if not then push the data to the database
                if(!TextUtils.isEmpty(updateName) && !TextUtils.isEmpty(updateAge) && !TextUtils.isEmpty(updateContact) &&!TextUtils.isEmpty(updateWorkouts)) {
                    String id = dbaseReference.push().getKey();

                    profilePage profilepage = new profilePage(updateName,updateContact,updateAge, updateWorkouts);
                    dbaseReference.child(id).setValue(profilepage);
                }
                else{
                    Toast.makeText(ProfileEditorActivity.this, "Error, please recheck your data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
