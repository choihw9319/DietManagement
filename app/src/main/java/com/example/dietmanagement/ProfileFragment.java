package com.example.dietmanagement;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button changeinfo = view.findViewById(R.id.changeinfoBT_profile);
        Button logout = view.findViewById(R.id.logoutBT_profile);
        Button signout = view.findViewById(R.id.signoutBT_profile);

        // 정보 변경 버튼 클릭 리스너
        changeinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeInfoDialogFragment changeInfoDialogFragment = new ChangeInfoDialogFragment();
                changeInfoDialogFragment.show(getParentFragmentManager(), "ChangeInfoDialogFragmentTag");
            }
        });

        // 로그아웃 버튼 클릭 리스너
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutDialogFragment logoutDialogFragment = new LogoutDialogFragment();
                logoutDialogFragment.show(getParentFragmentManager(), "LogoutDialogFragmentTag");
            }
        });

        // 회원 탈퇴 버튼 클릭 리스너
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SignoutDialogFragment 호출
                SignoutDialogFragment signoutDialogFragment = new SignoutDialogFragment();
                signoutDialogFragment.show(getParentFragmentManager(), "SignoutDialogFragmentTag");
            }
        });

        return view;
    }
}
