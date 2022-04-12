package com.example.bromie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class signupActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore database;
    EditText emailBox,passBox,nameBox;
    Button signUpBtn,accExitsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        nameBox = findViewById(R.id.textBoxName);

        emailBox = findViewById(R.id.signUpTextBoxEmail);
        passBox = findViewById(R.id.signUpTextBoxPassword);

        signUpBtn = findViewById(R.id.signUpLoginBtn);
        accExitsBtn = findViewById(R.id.accountExistBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,pass,name;
                email = emailBox.getText().toString();
                pass = passBox.getText().toString();
                name = nameBox.getText().toString();

                User user = new User();
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name);

                if(name.isEmpty() || email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(signupActivity.this,"Please fill all the credentials!",Toast.LENGTH_SHORT).show();
                }else{
                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //success
                                Toast.makeText(signupActivity.this,"Account Created Successfully",Toast.LENGTH_SHORT).show();
                                database.collection("Users")
                                        .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        startActivity(new Intent(signupActivity.this,loginActivity.class));
                                    }
                                });
                            }else{
                                Toast.makeText(signupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        accExitsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signupActivity.this,loginActivity.class));
            }
        });
    }
}