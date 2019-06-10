package com.example.note;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AppDataBase extends SQLiteOpenHelper {


    public AppDataBase(Context context) {
        super(context, "notes", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL("CREATE TABLE notes(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "textNote TEXT," +
                    "textDescription TEXT);");
        } catch (SQLiteException e) {
            Log.e("NOTE", "onCreate: " + e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("textNote", note.getTextViewNote());
        contentValues.put("textDescription",note.getTvDescription());
        db.insert("notes", null, contentValues);
    }

    public List<Note> getNotes() {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notes ",null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(0));
                note.setTextViewNote(cursor.getString(1));
                note.setTvDescription(cursor.getString(2));
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return noteList;

    }

    public void delete(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("notes", "id=?", new String[]{String.valueOf(note.getId())});
    }

    public void update(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("textNote", note.getTextViewNote());
        contentValues.put("textDescription",note.getTvDescription());
        db.update("notes", contentValues, "id=?", new String[]{String.valueOf(note.getId())});
    }
}
