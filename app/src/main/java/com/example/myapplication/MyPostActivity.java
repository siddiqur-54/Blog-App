package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyPostActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView namePost,insPost,datePost,postPost,emptyPost;
    private Button deletePostButton;
    private String name,ins,date,post,uid;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        namePost=(TextView)findViewById(R.id.namePostId);
        insPost=(TextView)findViewById(R.id.insPostId);
        datePost=(TextView)findViewById(R.id.datePostId);
        postPost=(TextView)findViewById(R.id.postPostId);
        emptyPost=(TextView)findViewById(R.id.emptyPostId);
        deletePostButton=(Button)findViewById(R.id.deletePostButtonId);

        mAuth=FirebaseAuth.getInstance();
        deletePostButton.setVisibility(View.INVISIBLE);
        deletePostButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {

        uid=mAuth.getCurrentUser().getUid();
        db=FirebaseFirestore.getInstance();
        docRef = db.collection("posts").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Post myPost = documentSnapshot.toObject(Post.class);
                name=myPost.getName();
                ins=myPost.getIns();
                date=myPost.getDate();
                post=myPost.getPost();

                if(!post.isEmpty())
                {
                    deletePostButton.setVisibility(View.VISIBLE);
                    namePost.setText(name);
                    insPost.setText(ins);
                    datePost.setText(date);
                    postPost.setText(post);
                }
                else
                {
                    emptyPost.setText("No Post Available");
                }
            }
        });

        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.deletePostButtonId)
        {
            name="";
            ins="";
            date="";
            post="";

            Post deletePost=new Post(name,ins,date,post);

            db.collection("posts").document(uid).set(deletePost);

            deletePostButton.setVisibility(View.INVISIBLE);

            Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);

        }
    }
}
