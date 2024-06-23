package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.todoapp.AddTaskActivity;
import com.example.todoapp.ModifyTaskActivity;
import com.example.todoapp.TaskAdapter;
import com.example.todoapp.ToDoTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_TASK = 1;
    private static final int REQUEST_CODE_MODIFY_TASK = 2;

    private ListView listView;
    private FloatingActionButton fab;
    private TaskAdapter adapter;
    private List<ToDoTask> taskList;
    private List<ToDoTask> filteredTaskList;
    private String currentFilter = "Tous"; // Filtre par défaut

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listView);
        fab = findViewById(R.id.fab);
        ImageView filterIcon = findViewById(R.id.icon); // ImageView for filter icon

        taskList = new ArrayList<>();
        filteredTaskList = new ArrayList<>();
        adapter = new TaskAdapter(this, filteredTaskList);

        listView.setAdapter(adapter);

        // Click listener for list item
        listView.setOnItemClickListener((parent, view, position, id) -> {
            ToDoTask task = filteredTaskList.get(position);
            Intent intent = new Intent(MainActivity.this, ModifyTaskActivity.class);
            intent.putExtra("taskName", task.getName());
            intent.putExtra("taskDescription", task.getDescription());
            intent.putExtra("taskStatus", task.getStatus());
            intent.putExtra("position", position);
            startActivityForResult(intent, REQUEST_CODE_MODIFY_TASK);
        });

        // Click listener for FAB
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_TASK);
        });

        // Click listener for filter icon
        filterIcon.setOnClickListener(v -> applyFilter(currentFilter));

        // Initially apply filter to show all tasks
        applyFilter(currentFilter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE_ADD_TASK) {
                String newTaskName = data.getStringExtra("newTaskName");
                String newTaskDescription = data.getStringExtra("newTaskDescription");
                String newTaskStatus = data.getStringExtra("newTaskStatus");

                ToDoTask newTask = new ToDoTask(taskList.size(), newTaskName, newTaskDescription, newTaskStatus);
                taskList.add(newTask);
                applyFilter(currentFilter);
            } else if (requestCode == REQUEST_CODE_MODIFY_TASK) {
                String updatedTaskName = data.getStringExtra("updatedTaskName");
                String updatedTaskDescription = data.getStringExtra("updatedTaskDescription");
                String updatedTaskStatus = data.getStringExtra("updatedTaskStatus");
                int position = data.getIntExtra("position", -1);

                if (position != -1) {
                    ToDoTask task = filteredTaskList.get(position);
                    task.setName(updatedTaskName);
                    task.setDescription(updatedTaskDescription);
                    task.setStatus(updatedTaskStatus);
                    applyFilter(currentFilter);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_all) {
            currentFilter = "Tous";
        } else if (id == R.id.filter_todo) {
            currentFilter = "À faire";
        } else if (id == R.id.filter_in_progress) {
            currentFilter = "En cours";
        } else if (id == R.id.filter_done) {
            currentFilter = "Fait";
        } else if (id == R.id.filter_bug) {
            currentFilter = "Bug";
        }

        applyFilter(currentFilter);
        return true;
    }

    private void applyFilter(String filter) {
        filteredTaskList.clear();
        for (ToDoTask task : taskList) {
            if (filter.equals("Tous") || task.getStatus().equalsIgnoreCase(filter)) {
                filteredTaskList.add(task);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
