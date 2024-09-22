package com.example.dietmanagement;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class AddFragment extends Fragment {

    public interface OnDataPass {
        void onDataPass(String foodName, float foodKcal, Uri imageUri);
    }

    private OnDataPass dataPasser;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_PERMISSION = 100;
    private Uri imageUri;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dataPasser = (OnDataPass) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        ImageButton image = view.findViewById(R.id.imageIB_add);
        EditText name = view.findViewById(R.id.foodnameET_add);
        EditText kcal = view.findViewById(R.id.kcalET_add);
        Button commit = view.findViewById(R.id.addBT_add);

        image.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            } else {
                openGallery();
            }
        });

        commit.setOnClickListener(v -> {
            String foodName = name.getText().toString();
            String kcalStr = kcal.getText().toString();
            float foodKcal = 0.0f;

            try {
                foodKcal = Float.parseFloat(kcalStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if (dataPasser != null) {
                dataPasser.onDataPass(foodName, foodKcal, imageUri);
            }
        });

        return view;
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(getContext(), "사진 권한이 거부됨", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ImageButton imageButton = getView().findViewById(R.id.imageIB_add);
            imageButton.setImageURI(imageUri);
        }
    }
}