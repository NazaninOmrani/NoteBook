package com.example.note;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.NoteViewHolder> {
    private List<Note> noteList = new ArrayList<>();
    private NoteViewEventCallback noteViewEventCallback;


    public Adapter(NoteViewEventCallback noteViewEventCallback) {

        this.noteViewEventCallback = noteViewEventCallback;

    }




    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        return new NoteViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {
        noteViewHolder.bindNote(noteList.get(i));
    }

    @Override
    public int getItemCount() {
        int count = noteList.size();
        noteViewEventCallback.getItemCountCalled(count);
        return count;
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;
        private TextView descriptionTv;
        private ImageView imageViewDelete;
        private ImageView imageViewEdit;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_item_title);
            descriptionTv = itemView.findViewById(R.id.tv_item_note);
            imageViewDelete = itemView.findViewById(R.id.iv_item_delete);
            imageViewEdit = itemView.findViewById(R.id.iv_item_edit);
        }


        public void bindNote(final Note note) {
            titleTv.setText(note.getTextViewNote());
            descriptionTv.setText(note.getTvDescription());
            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noteViewEventCallback.onDeleteClicked(note);
                }
            });
            imageViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noteViewEventCallback.onEditClicked(note);
                }
            });
        }
    }

    public void addNote(Note note) {
        noteList.add(note);
        notifyItemInserted(noteList.indexOf(note));
    }
    public void addNote(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    public void removeNote(Note note) {
        int index = noteList.indexOf(note);
        noteList.remove(index);
        notifyItemRemoved(index);
    }

    public void updateNote(Note note) {
        int index = noteList.indexOf(note);
        noteList.set(index, note);
        notifyItemChanged(index);
    }

    public interface NoteViewEventCallback {
        void onDeleteClicked(Note note);

        void onEditClicked(Note note);

        void getItemCountCalled(int count);
    }

}
