package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText newTaskEditText;
    private EditText newTaskDescriptionEditText;
    private Spinner statusSpinner;
    private Button confirmAddButton;
    private String[] statuses = {"Todo", "In Progress", "Done", "Bug"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        newTaskEditText = findViewById(R.id.newTask);
        newTaskDescriptionEditText = findViewById(R.id.newTaskDescription);
        statusSpinner = findViewById(R.id.statusSpinner);
        confirmAddButton = findViewById(R.id.confirmAddButton);

        // Configurer le Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);

        confirmAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = newTaskEditText.getText().toString().trim();
                String description = newTaskDescriptionEditText.getText().toString().trim();
                String status = statusSpinner.getSelectedItem().toString();

                if (!task.isEmpty()) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("newTaskName", task);
                    resultIntent.putExtra("newTaskDescription", description);
                    resultIntent.putExtra("newTaskStatus", status); // Inclure le statut
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(AddTaskActivity.this, "Veuillez saisir une tâche", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
