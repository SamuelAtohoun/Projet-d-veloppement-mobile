package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_MODIFY_TASK = 101;

    ArrayList<String> tasks = new ArrayList<>();
    TaskAdapter adapter;
    ListView listView;
    TaskDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new TaskDatabaseHelper(this);

        // Récupérer les tâches depuis la base de données
        tasks = dbHelper.getAllTasks();

        listView = findViewById(R.id.tasks_list);
        adapter = new TaskAdapter(this, R.layout.row, tasks);
        listView.setAdapter(adapter);

        // Écouteur de clic sur les éléments de la liste
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Ouvrir ModifyTaskActivity pour modifier la tâche sélectionnée
                Intent intent = new Intent(MainActivity.this, ModifyTaskActivity.class);
                intent.putExtra("taskName", tasks.get(position));
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE_MODIFY_TASK);
            }
        });

        FloatingActionButton addButton = findViewById(R.id.addButton);
        // Écouteur de clic sur le bouton d'ajout de tâche
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir AddTaskActivity pour ajouter une nouvelle tâche
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MODIFY_TASK && resultCode == RESULT_OK) {
            // Mettre à jour la tâche modifiée dans la liste et dans la base de données
            if (data != null) {
                String newTaskName = data.getStringExtra("newTaskName");
                int position = data.getIntExtra("position", -1);
                if (position != -1 && newTaskName != null) {
                    tasks.set(position, newTaskName);
                    adapter.notifyDataSetChanged();
                    // Mettre à jour la tâche dans la base de données
                    dbHelper.updateTask(tasks.get(position), newTaskName);
                }
            }
        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            // Ajouter une nouvelle tâche à la liste et à la base de données
            String newTask = data.getStringExtra("newTask");
            if (newTask != null) {
                dbHelper.addTask(newTask);
                tasks.clear();
                tasks.addAll(dbHelper.getAllTasks());
                adapter.notifyDataSetChanged();
            }
        }
    }

}
