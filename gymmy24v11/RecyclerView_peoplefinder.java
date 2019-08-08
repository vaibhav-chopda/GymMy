package com.example.vaibhavchopda.gymmy24v11;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerView_peoplefinder extends RecyclerView.Adapter<RecyclerView_peoplefinder.MyViewHolder> {

    Context context;
    ArrayList<Profile> profiles;


    public RecyclerView_peoplefinder(Context c , ArrayList<Profile> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardviewprofile,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { //gets data by calling get methods
        holder.name.setText(profiles.get(position).getName()); //fetch from firebase
        holder.age.setText(profiles.get(position).getage());
        holder.workouts.setText(profiles.get(position).getWorkouts());
        holder.contact.setText(profiles.get(position).getContact());
        Picasso.get().load(profiles.get(position).getProfilePic()).into(holder.profilePic); //picasso library added to make photos look better
        if(profiles.get(position).getPermission()) { //allows signed in user to connect with other user if permission is true for connecting
            holder.btn.setVisibility(View.VISIBLE);
            holder.onClickcustom(position,profiles.get(position).getContact().toString());
            }



    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,age,workouts,contact;
        ImageView profilePic;
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            age = (TextView) itemView.findViewById(R.id.age);
            workouts= (TextView) itemView.findViewById(R.id.workouts);
            profilePic = (ImageView) itemView.findViewById(R.id.profilePic);
            contact=(TextView) itemView.findViewById(R.id.contact);
            btn = (Button) itemView.findViewById(R.id.checkDetails);
        }
        /*public void onClick(final int position)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //options intent on whatsapp


                    String url = "https://api.whatsapp.com/send?phone="+contact;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);


                    Toast.makeText(context," Connection sent!!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }*/

        public void onClickcustom(int position,final String no) {

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String url = "https://api.whatsapp.com/send?phone="+no;
                    Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                    i.setData(Uri.parse(url));
                    context.startActivity(i);

                   /*
                    Toast.makeText(context," Connection sent!!!!", Toast.LENGTH_SHORT).show();*/
                }
            });
        }
    }
}
