package com.example.dietmanagement;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;

public class LogoutDialogFragment extends DialogFragment {


    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater_logout = requireActivity().getLayoutInflater();
        View dialogView = inflater_logout.inflate(R.layout.fragment_logout, null);

        builder.setView(dialogView);

        return builder.create();
    }
}