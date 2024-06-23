package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ModifyTaskActivity extends AppCompatActivity {

    private EditText editTaskName;
    private EditText editTaskDescription;
    private Spinner editTaskStatus;
    private Button saveButton;
    private int position;
    private String[] statuses = {"Todo", "In Progress", "Done", "Bug"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);

        editTaskName = findViewById(R.id.editTaskName);
        editTaskDescription = findViewById(R.id.editTaskDescription);
        editTaskStatus = findViewById(R.id.editTaskStatus);
        saveButton = findViewById(R.id.saveButton);

        Intent intent = getIntent();
        String taskName = intent.getStringExtra("taskName");
        String taskDescription = intent.getStringExtra("taskDescription");
        String taskStatus = intent.getStringExtra("taskStatus");
        position = intent.getIntExtra("position", -1);

        editTaskName.setText(taskName);
        editTaskDescription.setText(taskDescription);

        // Configurer le Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTaskStatus.setAdapter(adapter);

        if (taskStatus != null) {
            int spinnerPosition = adapter.getPosition(taskStatus);
            editTaskStatus.setSelection(spinnerPosition);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTaskName = editTaskName.getText().toString().trim();
                String updatedTaskDescription = editTaskDescription.getText().toString().trim();
                String updatedTaskStatus = editTaskStatus.getSelectedItem().toString();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedTaskName", updatedTaskName);
                resultIntent.putExtra("updatedTaskDescription", updatedTaskDescription);
                resultIntent.putExtra("updatedTaskStatus", updatedTaskStatus);
                resultIntent.putExtra("position", position);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
