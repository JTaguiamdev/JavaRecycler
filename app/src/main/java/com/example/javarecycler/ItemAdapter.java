package com.example.javarecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

    private Context context;
    private ArrayList<Student> studentList;
    private LayoutInflater inflater;

    public ItemAdapter(ArrayList<Student> studentList, Context context) {
        this.studentList = studentList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(inflater.inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Student currentStudent = studentList.get(position);
        holder.bind(currentStudent);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
