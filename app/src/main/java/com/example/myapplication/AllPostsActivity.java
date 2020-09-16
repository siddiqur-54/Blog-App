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


public class AllPostsActivity extends AppCompatActivity {
    private ListView listView;
    private List<Post> postList;
    private AllPostsAdapter allPostsAdapter;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_posts);

        postList = new ArrayList<>();
        allPostsAdapter = new AllPostsAdapter(AllPostsActivity.this, postList);
        listView = findViewById(R.id.listViewId);
    }


    @Override
    protected void onResume() {

        db=FirebaseFirestore.getInstance();
        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Post allPosts=document.toObject(Post.class);

                                if(!allPosts.getPost().isEmpty())
                                {
                                    postList.add(allPosts);
                                }
                            }

                            listView.setAdapter(allPostsAdapter);

                        } else {
                        }
                    }
                });

        super.onResume();
    }


}
