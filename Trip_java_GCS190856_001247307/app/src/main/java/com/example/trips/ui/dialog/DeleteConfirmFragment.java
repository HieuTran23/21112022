package com.example.trips.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DeleteConfirmFragment extends DialogFragment {
    protected String _message;

    public DeleteConfirmFragment() {
        _message = "";
    }

    public DeleteConfirmFragment(String message) {
        _message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        return new AlertDialog.Builder(getContext())
                .setTitle("Confirm")
                .setMessage(_message)
                .setNegativeButton("Cancel", (dialog, id) -> dismiss())
                .setPositiveButton("Confirm", (dialog, id) -> delete())
                .create();
    }

    protected void delete() {
        FragmentListener listener = (FragmentListener) getParentFragment();
        listener.sendFromDeleteConfirmFragment(1);

        dismiss();
    }

    public interface FragmentListener {
        void sendFromDeleteConfirmFragment(int status);
    }
}