package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView notVerified;
    private Button verifyButton;
    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        notVerified=(TextView)findViewById(R.id.notVerifiedId);
        verifyButton=(Button)findViewById(R.id.verifyButtonId);

        mAuth=FirebaseAuth.getInstance();

        verifyButton.setVisibility(View.INVISIBLE);
        verifyButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        user = mAuth.getCurrentUser();

        if (user.isEmailVerified())
        {
            Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            notVerified.setText("Your Email is not verified");
            verifyButton.setVisibility(View.VISIBLE);
        }

        super.onResume();
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.verifyButtonId)
        {
            user=mAuth.getCurrentUser();
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(getApplicationContext(),"Verification email sent to "+user.getEmail(),Toast.LENGTH_LONG).show();
                                FirebaseAuth.getInstance().signOut();
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Failed to sent verification email to "+user.getEmail(),Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(getIntent());
                            }
                        }
                    });
        }

    }
}
