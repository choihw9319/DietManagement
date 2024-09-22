package com.example.dietmanagement;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private List<CostomListView> itemList;
    private CustomAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = view.findViewById(R.id.foodlistLV_main);

        // 전달받은 리스트 데이터를 복원
        if (getArguments() != null) {
            itemList = getArguments().getParcelableArrayList("itemList");
        } else {
            itemList = new ArrayList<>();
        }

        adapter = new CustomAdapter(getContext(), itemList);
        listView.setAdapter(adapter);

        return view;
    }

    public void updateList(String foodName, float foodKcal, Uri imageUri) {
        CostomListView newItem = new CostomListView(foodName, foodKcal, imageUri);
        itemList.add(newItem);
        adapter.notifyDataSetChanged(); // 리스트뷰 업데이트
    }
}