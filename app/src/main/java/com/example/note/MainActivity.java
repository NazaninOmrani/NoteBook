package com.example.note;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.NoteViewEventCallback {
    private RecyclerView recyclerView;
    private Adapter noteAdapter;
    private View emptyState;
    private ImageView imageView;
    private Button buttonEmpty;
    private AppDataBase appDataBase;


    private View.OnClickListener addButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditDialog editDialog = new EditDialog();
            editDialog.setResultCallback(new EditDialog.ResultCallback() {
                @Override
                public void onSave(Note note) {
                    noteAdapter.addNote(note);
                    appDataBase.addNote(note);
                }
            });
            editDialog.show(getSupportFragmentManager(), null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onSetupViews();
    }

    private void onSetupViews() {
        emptyState = findViewById(R.id.ll_emtystate);
        appDataBase = new AppDataBase(MainActivity.this);
        recyclerView = findViewById(R.id.rv_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        noteAdapter = new Adapter(this);
        recyclerView.setAdapter(noteAdapter);
        List<Note> noteList = appDataBase.getNotes();
        if (noteList.size() > 0) {
            noteAdapter.addNote(noteList);
        }


        imageView = findViewById(R.id.iv_actionbtn);
        imageView.setOnClickListener(addButtonOnClickListener);
        buttonEmpty = findViewById(R.id.btn_main_empty);
        buttonEmpty.setOnClickListener(addButtonOnClickListener);


    }


    @Override
    public void onDeleteClicked(Note note) {
        noteAdapter.removeNote(note);
        appDataBase.delete(note);
    }

    @Override
    public void onEditClicked(Note note) {
        EditDialog editDialog = EditDialog.newInstance(note);
        editDialog.setCancelable(true);
        editDialog.setResultCallback(new EditDialog.ResultCallback() {
            @Override
            public void onSave(Note note) {
                noteAdapter.updateNote(note);
                appDataBase.update(note);
            }
        });
        editDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void getItemCountCalled(int count) {
        if (count > 0) {
            emptyState.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.VISIBLE);
        }
        if (emptyState.getVisibility() == View.VISIBLE) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

}
