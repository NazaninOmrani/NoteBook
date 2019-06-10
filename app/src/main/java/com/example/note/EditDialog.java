package com.example.note;

import android.os.Bundle;
import android.os.Parcelable;
import android.service.carrier.CarrierMessagingService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditDialog extends android.support.v4.app.DialogFragment {
    private static final String EXTRA_KEY_NOTE = "key";
    private Button buttonSave;
    private Button buttonCancel;
    private EditText editTextDescription;
    private EditText editTextTitle;
    private ResultCallback resultCallback;
    private Note note;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(EXTRA_KEY_NOTE);
        }
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.edit_dialog, null);
        editTextDescription = view.findViewById(R.id.et_dialog_description);
        editTextTitle = view.findViewById(R.id.et_dialog_title);

        if (note != null) {
            editTextDescription.setText(note.getTvDescription());
            editTextTitle.setText(note.getTextViewNote());
        }

        buttonCancel = view.findViewById(R.id.btn_cancel_dialog);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        buttonSave = view.findViewById(R.id.btn_save_dialog);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextTitle.length()>0 && editTextDescription.length() > 0) {

                    if (note == null) {
                        note = new Note();
                    }
                    note.setTvDescription(editTextDescription.getText().toString());
                    note.setTextViewNote(editTextTitle.getText().toString());
                    resultCallback.onSave(note);
                    dismiss();

                } else {
                    if (editTextDescription.length() == 0) {
                        editTextDescription.setError("Empty Description");
                    }else {
                        if(editTextTitle.length()==0){
                         editTextTitle.setError("Empty Title");
                        }
                    }
                }
            }

        });
        builder.setView(view);
        return builder.create();

    }


    public static EditDialog newInstance(Note note) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_KEY_NOTE, (Parcelable) note);
        EditDialog fragment = new EditDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setResultCallback(ResultCallback resultCallback) {
        this.resultCallback = resultCallback;
    }

    public interface ResultCallback {
        void onSave(Note note);
    }
}
