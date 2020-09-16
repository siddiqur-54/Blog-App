package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editNameEditText,editInsEditText;
    private Button updateButton;
    FirebaseAuth mAuth;
    private String name,ins,editName,editIns,uid,date,post;
    FirebaseFirestore db;
    DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mAuth = FirebaseAuth.getInstance();

        editNameEditText=(EditText)findViewById(R.id.editNameEditTextId);
        editInsEditText=(EditText)findViewById(R.id.editInsEditTextId);
        updateButton=(Button)findViewById(R.id.updateButtonId);

        name=getIntent().getStringExtra("name");
        ins=getIntent().getStringExtra("ins");

        editNameEditText.setText(name);
        editInsEditText.setText(ins);


        updateButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {

        uid=mAuth.getCurrentUser().getUid();
        db=FirebaseFirestore.getInstance();
        docRef = db.collection("posts").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Post addedPost = documentSnapshot.toObject(Post.class);
                date=addedPost.getDate();
                post=addedPost.getPost();
            }
        });

        super.onResume();
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.updateButtonId)
        {
            editName=editNameEditText.getText().toString().trim();
            editIns=editInsEditText.getText().toString().trim();

            if(editName.isEmpty())
            {
                editNameEditText.setError("Please write something");
                editNameEditText.requestFocus();
                return;
            }
            if(editIns.isEmpty())
            {
                editInsEditText.setError("Please write something");
                editInsEditText.requestFocus();
                return;
            }

            saveProfile();
            Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);

        }
    }
    public void saveProfile()
    {
        Profile profile=new Profile(editName,editIns);
        Post editedPost=new Post(editName,editIns,date,post);

        uid=mAuth.getCurrentUser().getUid();
        db=FirebaseFirestore.getInstance();

        db.collection("users").document(uid).set(profile);
        db.collection("posts").document(uid).set(editedPost);

        Toast.makeText(getApplicationContext(),"Profile is Updated",Toast.LENGTH_LONG).show();
    }
}
