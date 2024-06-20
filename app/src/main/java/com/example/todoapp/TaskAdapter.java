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

public class TaskAdapter extends ArrayAdapter<ToDoTask> {

    private Context context;
    private int layoutId;
    private ArrayList<ToDoTask> tasks;

    public TaskAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ToDoTask> tasks) {
        super(context, resource, tasks);
        this.context = context;
        this.layoutId = resource;
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
            holder.taskNameView = row.findViewById(R.id.task);
            holder.taskDescriptionView = row.findViewById(R.id.description);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ToDoTask task = tasks.get(position);
        holder.taskNameView.setText(task.getName());
        holder.taskDescriptionView.setText(task.getDescription());

        return row;
    }

    static class ViewHolder {
        TextView taskNameView;
        TextView taskDescriptionView;
    }
}
