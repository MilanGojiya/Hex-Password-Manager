package com.NoGravity.HexPM;

import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText pass;
    Button regbtn;
    Button logbtn;
    TextView regtext;
    TextView logtext;
    TextView donthave;
    TextView have;
    FirebaseAuth uAuth;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        regbtn = findViewById(R.id.regbtn);
        logbtn = findViewById(R.id.logbtn);
        regtext = findViewById(R.id.regtext);
        logtext = findViewById(R.id.logtext);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        donthave = findViewById(R.id.donthave);
        have = findViewById(R.id.have);

        sp = getSharedPreferences("Userprefs", Context.MODE_PRIVATE);

        uAuth = FirebaseAuth.getInstance();

        regbtn.setOnClickListener(view -> {
            createUser();
        });
        regtext.setOnClickListener(view -> {
            register();
        });
        logtext.setOnClickListener(view -> {
            login();
        });

        logbtn.setOnClickListener(view -> {
            loginUser();
        });

    }

    private void createUser(){
        String uemail = email.getText().toString();
        String upass = pass.getText().toString();

        if(TextUtils.isEmpty(uemail)){
            email.setError("Email cannot be empty");
            email.requestFocus();
        }
        else if(TextUtils.isEmpty(upass)){
            pass.setError("Password cannot be empty");
        }

        else {
            uAuth.createUserWithEmailAndPassword(uemail,upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("UserEmail",uemail);
                        editor.apply();

                        Toast.makeText(MainActivity.this,"User Registered Successfully",Toast.LENGTH_SHORT).show();
                        uAuth.signInWithEmailAndPassword(uemail,upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    startActivity(new Intent(MainActivity.this,PasswordSave.class));
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Registration Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = uAuth.getCurrentUser();

        if(user == null){
            uiVisible();
        }
        else {
            startActivity(new Intent(MainActivity.this,PasswordSave.class));
        }
    }
    private void uiVisible(){
        email.setVisibility(View.VISIBLE);
        pass.setVisibility(View.VISIBLE);
        logbtn.setVisibility(View.VISIBLE);
        regtext.setVisibility(View.VISIBLE);
        logtext.setVisibility(View.INVISIBLE);
        have.setVisibility(View.INVISIBLE);


    }
    private void register(){
        email.setVisibility(View.VISIBLE);
        email.setText("");
        email.requestFocus();
        pass.setVisibility(View.VISIBLE);
        pass.setText("");
        email.requestFocus();
        regbtn.setVisibility(View.VISIBLE);
        regtext.setVisibility(View.INVISIBLE);
        logbtn.setVisibility(View.INVISIBLE);
        donthave.setVisibility(View.INVISIBLE);
        have.setVisibility(View.VISIBLE);
        logtext.setVisibility(View.VISIBLE);
    }

    private void login(){
        email.setVisibility(View.VISIBLE);
        email.setText("");
        email.requestFocus();
        pass.setVisibility(View.VISIBLE);
        pass.setText("");
        email.requestFocus();
        regbtn.setVisibility(View.INVISIBLE);
        regtext.setVisibility(View.VISIBLE);
        logbtn.setVisibility(View.VISIBLE);
        donthave.setVisibility(View.VISIBLE);
        have.setVisibility(View.INVISIBLE);
        logtext.setVisibility(View.INVISIBLE);
    }

    private void loginUser(){

        String uemail = email.getText().toString();
        String upass = pass.getText().toString();

        if(TextUtils.isEmpty(uemail)){
            email.setError("Email cannot be empty");
            email.requestFocus();
        }
        else if(TextUtils.isEmpty(upass)){
            pass.setError("Password cannot be empty");
        }
        else{
            uAuth.signInWithEmailAndPassword(uemail,upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"User Logged in Successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,PasswordSave.class));
                    }
                }
            });
        }

    }
}