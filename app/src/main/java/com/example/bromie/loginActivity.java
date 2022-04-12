package com.example.bromie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    EditText emailBox,passBox;
    Button loginBtn,signUpBtn;

    FirebaseAuth auth;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait.");
        auth = FirebaseAuth.getInstance();

        emailBox = findViewById(R.id.textBoxEmail);
        passBox = findViewById(R.id.textBoxPassword);

        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this,signupActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String email,pass;
                email = emailBox.getText().toString();
                pass = passBox.getText().toString();
                if(email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(loginActivity.this,"Both Email and Password field are necessary!",Toast.LENGTH_SHORT).show();
                }else {
                    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if(task.isSuccessful()){
                                //success
                                Toast.makeText(loginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(loginActivity.this,dashboardActivity.class));
                            }else{
                                Toast.makeText(loginActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}