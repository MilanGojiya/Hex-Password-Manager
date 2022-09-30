package com.NoGravity.HexPM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AddDetails extends AppCompatActivity {

    EditText title;
    EditText username;
    EditText userpass;
    EditText website;
    EditText comment;
    FloatingActionButton savebtn;
    DatabaseReference userdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        title = findViewById(R.id.title);
        username = findViewById(R.id.username);
        userpass= findViewById(R.id.userpass);
        website = findViewById(R.id.website);
        comment = findViewById(R.id.comment);
        savebtn = findViewById(R.id.savebtn);



        userdata = FirebaseDatabase.getInstance().getReference().child("User");
        //userdata.child("UserId");


        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertUserData();
            }
        });


    }

    private void insertUserData() {
        String utitle = title.getText().toString();
        String uname = username.getText().toString();
        String upass = userpass.getText().toString();
        String uweb = website.getText().toString();
        String ucomment = comment.getText().toString();


        if (TextUtils.isEmpty(utitle)) {
            title.setError("Tttle cannot be empty");
        } else if (TextUtils.isEmpty(uname)) {
            username.setError("User name cannot be empty");
        } else if (TextUtils.isEmpty(upass)) {
            userpass.setError("Password cannot be empty");
        } else {

            SharedPreferences sp = getApplicationContext().getSharedPreferences("Userprefs", Context.MODE_PRIVATE);
            String id = sp.getString("UserEmail", "");

            int index = id.indexOf('@');

            id = id.substring(0, index);

            String password = userpass.getText().toString();
            String key=username.getText().toString();
            String encryptedpass = null;
            try {
                encryptedpass = encrypted(key,password);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }

            String randomid = userdata.push().getKey();
            Users users = new Users(utitle, uname, encryptedpass, uweb, ucomment);
            userdata.child(id).child(randomid).setValue(users);

            Toast.makeText(AddDetails.this, "Data Inserted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddDetails.this,PasswordSave.class);
            startActivity(intent);

        }

    }

    private String encrypted(String key,String password) throws GeneralSecurityException {
        String encrypt = AESCrypt.encrypt(key,password);
        return encrypt;
    }


}
