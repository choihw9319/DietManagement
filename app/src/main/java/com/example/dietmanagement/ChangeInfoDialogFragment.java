package com.example.dietmanagement;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChangeInfoDialogFragment extends DialogFragment {

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        // Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Inflate the layout for the dialog
        LayoutInflater inflater_changeinfo = requireActivity().getLayoutInflater();
        View dialogView = inflater_changeinfo.inflate(R.layout.fragment_change_info, null);

        // Set the layout for the dialog
        builder.setView(dialogView)
                .setTitle("정보 수정"); // Set dialog title

        // Create and return the AlertDialog
        return builder.create();
    }
}