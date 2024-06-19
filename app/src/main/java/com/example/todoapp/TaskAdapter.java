package com.example.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<String> {

    private Context context;
    private int layoutId;
    private ArrayList<String> tasks;

    public TaskAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> tasks) {
        super(context, resource, tasks);
        this.context = context;
        layoutId = resource;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutId, parent, false);

            holder = new ViewHolder();
            holder.taskView = row.findViewById(R.id.task);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        String task = tasks.get(position);
        holder.taskView.setText(task);

        return row;
    }

    static class ViewHolder {
        TextView taskView;
    }
}
