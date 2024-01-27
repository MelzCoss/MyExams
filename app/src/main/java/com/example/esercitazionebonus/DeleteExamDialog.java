package com.example.esercitazionebonus;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DeleteExamDialog extends AppCompatDialogFragment {

    Exam exam;
    DeleteExamDialogListener listener;

    public DeleteExamDialog(Exam exam, DeleteExamDialogListener listener) {
        this.exam = exam;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sei sicuro di voler eliminare l'esame?")
                .setMessage("Clicca 'Conferma' per eliminarlo, 'Annulla' per annullare")
                .setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.cancelExam(exam);
                    }
                })
                .setNeutralButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        return builder.create();
    }
}
