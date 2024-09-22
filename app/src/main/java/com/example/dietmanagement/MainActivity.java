package com.example.dietmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddFragment.OnDataPass {

    private List<CostomListView> itemList = new ArrayList<>(); // 데이터를 유지할 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            // MainFragment를 생성할 때 저장된 리스트를 전달
            MainFragment mainFragment = new MainFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("itemList", new ArrayList<>(itemList));
            mainFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mainFragment)
                    .commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.main_button) {
                        // MainFragment를 생성할 때 저장된 리스트를 전달
                        MainFragment mainFragment = new MainFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("itemList", new ArrayList<>(itemList));
                        mainFragment.setArguments(bundle);
                        selectedFragment = mainFragment;
                    } else if (item.getItemId() == R.id.add_button) {
                        selectedFragment = new AddFragment();
                    } else if (item.getItemId() == R.id.profile_button) {
                        selectedFragment = new ProfileFragment();
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                    }

                    return true;
                }
            };

    @Override
    public void onDataPass(String foodName, float foodKcal, Uri imageUri) {
        // 데이터 리스트에 아이템 추가
        itemList.add(new CostomListView(foodName, foodKcal, imageUri));

        // MainFragment가 현재 화면에 존재하는지 확인
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment instanceof MainFragment) {
            // MainFragment가 이미 존재하면 리스트 업데이트
            ((MainFragment) fragment).updateList(foodName, foodKcal, imageUri);
        } else {
            // MainFragment로 화면 전환 후 데이터 전달
            MainFragment mainFragment = new MainFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("itemList", new ArrayList<>(itemList)); // 리스트 전달
            mainFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mainFragment)
                    .commit();
        }
    }
}