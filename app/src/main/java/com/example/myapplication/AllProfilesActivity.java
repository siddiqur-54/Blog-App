package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class AllProfilesActivity extends AppCompatActivity {
    private ListView listView;
    private List<Profile> profileList;
    private AllProfilesAdapter allProfilesAdapter;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_posts);

        profileList = new ArrayList<>();
        allProfilesAdapter = new AllProfilesAdapter(AllProfilesActivity.this, profileList);
        listView = findViewById(R.id.listViewId);
    }


    @Override
    protected void onResume() {

        db=FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Profile profile=document.toObject(Profile.class);

                                if(!profile.getName().isEmpty()&&!profile.getIns().isEmpty())
                                {
                                    profileList.add(profile);
                                }
                            }

                            listView.setAdapter(allProfilesAdapter);

                        } else {
                        }
                    }
                });

        super.onResume();
    }


}
