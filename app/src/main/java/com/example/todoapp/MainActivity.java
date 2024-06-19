package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> tasks = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView;
    TaskDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TaskDatabaseHelper(this);

        // Récupérer les tâches depuis la base de données
        tasks = dbHelper.getAllTasks();

        listView = findViewById(R.id.tasks_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        listView.setAdapter(adapter);

        // Écouteur de clic sur les éléments de la liste
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Code à exécuter lorsqu'un élément de la liste est cliqué
                String selectedTask = tasks.get(position);
                // Vous pouvez ajouter ici le code pour afficher plus d'informations sur la tâche sélectionnée
            }
        });

        FloatingActionButton addButton = findViewById(R.id.addButton);
        // Écouteur de clic sur le bouton d'ajout de tâche
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code à exécuter lorsque le bouton d'ajout de tâche est cliqué
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Récupérer la nouvelle tâche ajoutée
            String newTask = data.getStringExtra("newTask");
            if (newTask != null) {
                // Ajouter la nouvelle tâche à la base de données
                dbHelper.addTask(newTask);
                // Mettre à jour la liste de tâches
                tasks.clear();
                tasks.addAll(dbHelper.getAllTasks());
                adapter.notifyDataSetChanged();
            }
        }
    }
}
