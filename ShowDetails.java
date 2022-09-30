package com.NoGravity.HexPM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class ShowDetails extends AppCompatActivity {

    TextView title,name,password,website,comment;
    Button namecopy,passcopy,webcopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        title = findViewById(R.id.title);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        website = findViewById(R.id.website);
        comment = findViewById(R.id.comment);
        namecopy = findViewById(R.id.namecopy);
        passcopy = findViewById(R.id.passcopy);
        webcopy = findViewById(R.id.webcopy);

        String utitle = getIntent().getStringExtra("Title");
        String uname = getIntent().getStringExtra("Name");
        String upassword = getIntent().getStringExtra("Password");
        String uwebsite = getIntent().getStringExtra("Website");
        String ucomment = getIntent().getStringExtra("Comment");


        String key = uname;
        String decryptedpass = null;
        try {
            decryptedpass = decrypted(key, upassword);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        title.setText(utitle);
        name.setText(uname);
        password.setText(decryptedpass);
        website.setText(uwebsite);
        comment.setText(ucomment);

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        namecopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipData clip = ClipData.newPlainText("name", name.getText().toString());
                clipboardManager.setPrimaryClip(clip);

                Toast.makeText(ShowDetails.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        passcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipData clip = ClipData.newPlainText("password", password.getText().toString());
                clipboardManager.setPrimaryClip(clip);

                Toast.makeText(ShowDetails.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        webcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipData clip = ClipData.newPlainText("website", website.getText().toString());
                clipboardManager.setPrimaryClip(clip);

                Toast.makeText(ShowDetails.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String decrypted(String key,String password) throws GeneralSecurityException {
        String decrypt = AESCrypt.decrypt(key,password);
        return decrypt;
    }
}