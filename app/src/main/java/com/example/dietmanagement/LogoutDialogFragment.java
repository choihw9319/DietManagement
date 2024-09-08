package com.example.dietmanagement;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class LogoutDialogFragment extends DialogFragment {

    Button yes;
    Button no;

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater_logout = requireActivity().getLayoutInflater();
        View dialogView = inflater_logout.inflate(R.layout.fragment_logout, null);

        yes = dialogView.findViewById(R.id.yes_logout);
        no = dialogView.findViewById(R.id.no_logout);

        builder.setView(dialogView);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Define.ins().userId = "";
                Define.ins().gender = "";
                Define.ins().pw = "";
                Define.ins().height = 0;
                Define.ins().weight = 0;
                Define.ins().nickName = "";

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                Log.d("DietManagement",Define.ins().userId);
            }
        });

        return builder.create();
    }
}