package com.example.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends BaseAdapter {
    private Context context;
    private List<ToDoTask> tasks;

    public TaskAdapter(Context context, List<ToDoTask> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        }

        ToDoTask task = tasks.get(position);

        TextView taskNameTextView = convertView.findViewById(R.id.taskName);
        TextView taskDescriptionTextView = convertView.findViewById(R.id.taskDescription);
        TextView taskStatusTextView = convertView.findViewById(R.id.taskStatus);

        taskNameTextView.setText(task.getName());
        taskDescriptionTextView.setText(task.getDescription());
        taskStatusTextView.setText(task.getStatus());

        return convertView;
    }

    public void updateTasks(List<ToDoTask> updatedTasks) {
        tasks.clear();
        tasks.addAll(updatedTasks);
        notifyDataSetChanged();
    }
}
