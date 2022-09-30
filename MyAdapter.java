package com.NoGravity.HexPM;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolader> {

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<Details> DetailList;
    ArrayList<String> uid;
    DatabaseReference userdata;



    public MyAdapter(Context context, ArrayList<Details> DetailList ,RecyclerViewInterface recylerViewInterface) {
        this.context = context;
        this.DetailList=DetailList;
        this.recyclerViewInterface = recylerViewInterface;
        uid=new ArrayList<String>();
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolader onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new MyViewHolader(v,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolader holder, @SuppressLint("RecyclerView") int position) {

        Details details = DetailList.get(position);

        holder.title.setText(details.getUtitle());
        holder.website.setText(details.getUweb());

    }

    @Override
    public int getItemCount() {
        return DetailList.size();
    }

    public static class MyViewHolader extends RecyclerView.ViewHolder{

        TextView title;
        TextView website;

        public MyViewHolader(@NonNull View itemView,RecyclerViewInterface recylerViewInterface) {
            super(itemView);

            website = itemView.findViewById(R.id.website);
            title = itemView.findViewById(R.id.title);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recylerViewInterface != null){

                        int pos = getAdapterPosition();

                        if(pos !=  RecyclerView.NO_POSITION){

                            recylerViewInterface.onItemClick(pos);
                        }

                    }
                }
            });


        }
    }

}
