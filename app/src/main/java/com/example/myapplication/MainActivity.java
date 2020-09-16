package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signInEmailEditText,signInPasswordEditText;
    private Button signInButton,signUpButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBarId);


        signInButton=(Button)findViewById(R.id.signInButtonId);
        signUpButton=(Button)findViewById(R.id.signUpHereButtonId);
        signInEmailEditText=(EditText)findViewById(R.id.signInEmailEditTextId);
        signInPasswordEditText=(EditText)findViewById(R.id.signInPasswordEditTextId);

        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.signUpHereButtonId)
        {
            Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.signInButtonId)
        {
            Login();
        }
    }

    public void Login()
    {
        email=signInEmailEditText.getText().toString().trim();
        password=signInPasswordEditText.getText().toString().trim();

        if(email.isEmpty())
        {
            signInEmailEditText.setError("Please enter an email");
            signInEmailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signInEmailEditText.setError("Please eneter a valid email");
            signInEmailEditText.requestFocus();
            return;
        }
        if(password.length()<5)
        {
            signInPasswordEditText.setError("passwrod length minimum 5 characters");
            signInPasswordEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            finish();
                            Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(MainActivity.this,VerifyActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Login unsuccessful",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
