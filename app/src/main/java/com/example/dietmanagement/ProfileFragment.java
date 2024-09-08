package com.example.dietmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageButton profile = view.findViewById(R.id.profileIB_profile);
        Button changeinfo = view.findViewById(R.id.changeinfoBT_profile);
        Button logout = view.findViewById(R.id.logoutBT_profile);
        Button signout = view.findViewById(R.id.signoutBT_profile);

        changeinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeInfoDialogFragment changeInfoDialogFragment = new ChangeInfoDialogFragment();
                changeInfoDialogFragment.show(getParentFragmentManager(), "ChangeInfoDialogFragmentTag");
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutDialogFragment logoutDialogFragment = new LogoutDialogFragment();
                logoutDialogFragment.show(getParentFragmentManager(), "LogoutDialogFragmentTag");
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignoutDialogFragment SignoutDialogFragment = new SignoutDialogFragment();
                SignoutDialogFragment.show(getParentFragmentManager(), "SignoutDialogFragmentTag");
            }
        });

        return view;
    }
}