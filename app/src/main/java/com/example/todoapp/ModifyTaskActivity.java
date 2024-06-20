package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ModifyTaskActivity extends AppCompatActivity {

    private EditText editTaskName;
    private EditText editTaskDescription;
    private Button saveButton;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);

        // Initialiser les vues
        editTaskName = findViewById(R.id.editTaskName);
        editTaskDescription = findViewById(R.id.editTaskDescription);
        saveButton = findViewById(R.id.saveButton);

        // Récupérer le nom de la tâche, la description et la position depuis l'intent
        String taskName = getIntent().getStringExtra("taskName");
        String taskDescription = getIntent().getStringExtra("taskDescription");
        position = getIntent().getIntExtra("position", -1);

        // Afficher le nom de la tâche et la description dans les champs de texte
        editTaskName.setText(taskName);
        editTaskDescription.setText(taskDescription);

        // Ajouter un écouteur de clic au bouton de sauvegarde
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer le nouveau nom et la nouvelle description de la tâche modifiée
                String newTaskName = editTaskName.getText().toString().trim();
                String newTaskDescription = editTaskDescription.getText().toString().trim();
                if (!newTaskName.isEmpty() && !newTaskDescription.isEmpty()) {
                    // Créer un intent pour renvoyer le nouveau nom et la nouvelle description de la tâche à MainActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("newTaskName", newTaskName);
                    resultIntent.putExtra("newTaskDescription", newTaskDescription);
                    resultIntent.putExtra("position", position);
                    setResult(RESULT_OK, resultIntent);
                    finish(); // Fermer l'activité
                }
            }
        });
    }
}
