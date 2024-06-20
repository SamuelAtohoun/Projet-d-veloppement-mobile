package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText newTaskEditText;
    private EditText newTaskDescriptionEditText;
    private Button confirmAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Récupération des références des éléments de l'interface utilisateur
        newTaskEditText = findViewById(R.id.newTask);
        newTaskDescriptionEditText = findViewById(R.id.newTaskDescription); // Nouveau champ pour la description
        confirmAddButton = findViewById(R.id.confirmAddButton);

        // Définition de l'événement au clic sur le bouton "Ajouter"
        confirmAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération de la tâche et de la description saisies par l'utilisateur
                String task = newTaskEditText.getText().toString().trim();
                String description = newTaskDescriptionEditText.getText().toString().trim();

                // Vérification si le champ de la tâche n'est pas vide
                if (!task.isEmpty()) {
                    // Retourner la nouvelle tâche et la description à MainActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("newTaskName", task);
                    resultIntent.putExtra("newTaskDescription", description);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    // Affichage d'un message d'erreur si le champ de la tâche est vide
                    Toast.makeText(AddTaskActivity.this, "Veuillez saisir une tâche", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
