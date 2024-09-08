package com.example.dietmanagement;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SignoutDialogFragment extends DialogFragment {


    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater_signout = requireActivity().getLayoutInflater();
        View dialogView = inflater_signout.inflate(R.layout.fragment_signout_dialog, null);

        builder.setView(dialogView);

        return builder.create();
    }
}