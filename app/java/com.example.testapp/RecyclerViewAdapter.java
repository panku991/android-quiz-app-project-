package com.example.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<ModelForFetch> fetchModel;


    public RecyclerViewAdapter(Context context, ArrayList<ModelForFetch> fetchModel) {
        this.context = context;
        this.fetchModel = fetchModel;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerviewitem,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.t1.setText(fetchModel.get(position).getTestName());
        holder.t2.setText(fetchModel.get(position).getTestId());
        holder.t3.setText(fetchModel.get(position).getTOTQuestions());

    }

    @Override
    public int getItemCount() {
        return fetchModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView t1,t2,t3;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            t1=itemView.findViewById(R.id.fetchTestName);
            t2=itemView.findViewById(R.id.fetchTestId);
            t3=itemView.findViewById(R.id.fetchTotalQuestions);


        }
    }




}
