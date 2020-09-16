package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button editButton,addPostButton,myPostButton,signOutButton,allPostButton,allProfileButton;
    private TextView nameTextView,insTextView;
    FirebaseAuth mAuth;
    private String name,ins,uid;
    DocumentReference docRef;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth=FirebaseAuth.getInstance();

        editButton = (Button) findViewById(R.id.editButtonId);
        myPostButton=(Button)findViewById(R.id.myPostButtonId);
        signOutButton=(Button)findViewById(R.id.signOutButtonId);
        addPostButton=(Button)findViewById(R.id.addPostButtonId);
        allProfileButton=(Button)findViewById(R.id.allProfileButtonId);
        allPostButton=(Button)findViewById(R.id.allPostButtonId);
        nameTextView =(TextView)findViewById(R.id.nameTextViewId);
        insTextView =(TextView)findViewById(R.id.insTextViewId);


        editButton.setOnClickListener(this);
        addPostButton.setOnClickListener(this);
        myPostButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);
        allPostButton.setOnClickListener(this);
        allProfileButton.setOnClickListener(this);
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

                nameTextView.setText(name);
                insTextView.setText(ins);
            }
        });

        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.editButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),EditActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("ins",ins);
            startActivity(intent);
        }
        else if(view.getId()==R.id.addPostButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),PostActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.myPostButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),MyPostActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.signOutButtonId)
        {
            mAuth.signOut();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.allPostButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),AllPostsActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.allProfileButtonId)
        {
            Intent intent=new Intent(getApplicationContext(),AllProfilesActivity.class);
            startActivity(intent);
        }
    }
}
