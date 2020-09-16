package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signUpEmailEditText,signUpPasswordEditText;
    private Button signUpButton;
    private ProgressBar progressBar;
    FirebaseAuth mAuth;
    private String name,ins,email,password,uid,date,post;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth=FirebaseAuth.getInstance();

        progressBar=findViewById(R.id.progressBarId);
        signUpButton=findViewById(R.id.signUpButtonId);
        signUpEmailEditText=findViewById(R.id.signUpEmailEditTextId);
        signUpPasswordEditText=findViewById(R.id.signUpPasswordEditTextId);

        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.signUpButtonId)
        {
            Register();
        }
    }

    public void Register()
    {
        email=signUpEmailEditText.getText().toString().trim();
        password=signUpPasswordEditText.getText().toString().trim();

        if(email.isEmpty())
        {
            signUpEmailEditText.setError("Please enter an email");
            signUpEmailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signUpEmailEditText.setError("Please eneter a valid email");
            signUpEmailEditText.requestFocus();
            return;
        }
        if(password.length()<5)
        {
            signUpPasswordEditText.setError("password length minimum 5 characters");
            signUpPasswordEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Registration is successful",Toast.LENGTH_LONG).show();
                            saveProfile();
                            Intent intent=new Intent(getApplicationContext(),VerifyActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(getApplicationContext(),"User is already registered",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Registration is unsuccessful",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });


    }
    public void saveProfile()
    {

        name="";
        ins="";
        date="";
        post="";
        Profile profile=new Profile(name,ins);
        Post addedPost=new Post(name,ins,date,post);

        uid=mAuth.getCurrentUser().getUid();
        db=FirebaseFirestore.getInstance();

        db.collection("users").document(uid).set(profile);
        db.collection("posts").document(uid).set(addedPost);

        Toast.makeText(getApplicationContext(),"Profile is added",Toast.LENGTH_LONG).show();
    }
}
