package com.NoGravity.HexPM;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PasswordSave extends AppCompatActivity implements RecyclerViewInterface{

     FloatingActionButton addbtn;
     RecyclerView recyclerView;
     ArrayList<Details> DetailList;
     MyAdapter myAdapter;
     DatabaseReference userdata;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PasswordSave.this,MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_save);

        addbtn = findViewById(R.id.addbtn);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Userprefs", Context.MODE_PRIVATE);
        String id = sp.getString("UserEmail","");

        int index = id.indexOf('@');

        id = id.substring(0,index);

        userdata = FirebaseDatabase.getInstance().getReference().child("User").child(id);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DetailList = new ArrayList<>();
        myAdapter = new MyAdapter(PasswordSave.this,DetailList,this);
        recyclerView.setAdapter(myAdapter);


        addbtn.setOnClickListener(view -> {
            startActivity(new Intent(PasswordSave.this,AddDetails.class));
        });

        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DetailList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Details details = dataSnapshot.getValue(Details.class);
                    DetailList.add(details);

                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(PasswordSave.this,ShowDetails.class);

        intent.putExtra("Title",DetailList.get(position).getUtitle());

        intent.putExtra("Name",DetailList.get(position).getUname());

        intent.putExtra("Password",DetailList.get(position).getUpass());

        intent.putExtra("Website",DetailList.get(position).getUweb());

        intent.putExtra("Comment",DetailList.get(position).getUcomment());

        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(PasswordSave.this);
        alert.setTitle("Exit App");
        alert.setMessage("Do you want to exit app");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }
}

