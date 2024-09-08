package com.example.dietmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class AddFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        ImageButton image = view.findViewById(R.id.imageIB_add);
        EditText name = view.findViewById(R.id.foodnameET_add);
        EditText kcal = view.findViewById(R.id.kcalET_add);
        Button commit = view.findViewById(R.id.addBT_add);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditText
                String foodName = name.getText().toString();
                int foodKcal;
                try {
                    foodKcal = Integer.parseInt(kcal.getText().toString());
                } catch (NumberFormatException e) {
                    foodKcal = 0; // Default value if input is invalid
                }

                // Notify MainActivity to switch to MainFragment
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).showFragment(new MainFragment());
                }
            }
        });

        return view;
    }
}