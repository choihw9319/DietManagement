package com.example.dietmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignoutDialogFragment extends DialogFragment {

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("회원 탈퇴")
                .setMessage("정말로 탈퇴하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendDeleteRequest(); // 확인 버튼을 눌렀을 때 회원 탈퇴 요청을 보냄
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 취소 버튼을 눌렀을 때 아무 작업도 하지 않음
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

    // 회원 탈퇴 DELETE 요청을 보내는 메서드
    private void sendDeleteRequest() {
        // Retrofit 클라이언트 생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://yourserver.com/") // 서버의 URL로 변경 필요
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        // Define.ins().userid 값을 담아 요청 생성
        UserIdRequest request = new UserIdRequest(Define.ins().userId);

        // DELETE 요청 실행
        apiService.deleteUser(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 회원 탈퇴 성공 처리
                    Toast.makeText(requireContext(), "회원 탈퇴 성공", Toast.LENGTH_SHORT).show();
                    Log.d("SignOut", "회원 탈퇴 성공");
                } else {
                    // 실패 처리
                    Toast.makeText(requireContext(), "회원 탈퇴 실패: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("SignOut", "회원 탈퇴 실패: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // 네트워크 오류 처리
                Toast.makeText(requireContext(), "오류 발생: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("SignOut", "오류 발생: " + t.getMessage());
            }
        });
    }
}
