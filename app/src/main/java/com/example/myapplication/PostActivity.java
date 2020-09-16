package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText postEditText;
    private Button postButton;
    private ProgressBar progressBar;
    FirebaseAuth mAuth;
    private String uid,post,name,date,ins;
    FirebaseFirestore db;
    DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        progressBar=findViewById(R.id.progressBarId);
        mAuth = FirebaseAuth.getInstance();

        postButton=findViewById(R.id.postButtonId);
        postEditText=findViewById(R.id.postEditTextId);

        postEditText.setVisibility(View.INVISIBLE);
        postButton.setVisibility(View.INVISIBLE);

        postButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {

        uid=mAuth.getCurrentUser().getUid();
        db=FirebaseFirestore.getInstance();
        docRef = db.collection("users").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Profile profile = documentSnapshot.toObject(Profile.class);
                name=profile.getName();
                ins=profile.getIns();
                if(name.isEmpty()&&ins.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please set your name and institution first",Toast.LENGTH_LONG).show();
                }
                else
                {
                    postEditText.setVisibility(View.VISIBLE);
                    postButton.setVisibility(View.VISIBLE);
                }
            }
        });

        super.onResume();
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.postButtonId)
        {
            post=postEditText.getText().toString();
            if(post.isEmpty())
            {
                postEditText.setError("Please write something");
                postEditText.requestFocus();
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd--HH:mm:ss", Locale.getDefault());
            date = sdf.format(new Date());

            Post newPost=new Post(name,ins,date,post);

            db.collection("posts").document(uid).set(newPost);

            Toast.makeText(getApplicationContext(),"Post is added",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);
        }
    }
}
