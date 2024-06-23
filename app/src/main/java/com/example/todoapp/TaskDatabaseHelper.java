package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class TaskDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_STATUS = "status"; // Ajout de la colonne "status"

    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_TASKS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_STATUS + " TEXT)"; // Création de la colonne "status"
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public void addTask(String name, String description, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_STATUS, status); // Insertion de la valeur "status"
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    public void updateTask(int id, String name, String description, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_STATUS, status); // Mise à jour de la valeur "status"
        db.update(TABLE_TASKS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<ToDoTask> getAllTasks() {
        ArrayList<ToDoTask> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TASKS, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                int statusIndex = cursor.getColumnIndex(COLUMN_STATUS); // Index de la colonne "status"
                if (idIndex >= 0 && nameIndex >= 0 && descriptionIndex >= 0 && statusIndex >= 0) {
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    String description = cursor.getString(descriptionIndex);
                    String status = cursor.getString(statusIndex); // Récupération de la valeur "status"
                    tasks.add(new ToDoTask(id, name, description, status));
                }
            }
            cursor.close();
        }
        db.close();
        return tasks;
    }
}
