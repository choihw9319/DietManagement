package com.example.dietmanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChangeInfoDialogFragment extends DialogFragment {

    private EditText changeHeight_changeInfo;
    private EditText changeWeight_changeInfo;
    private Button yes_changeInfo;
    private Button no_changeInfo;

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        // AlertDialog.Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // 다이얼로그 레이아웃을 인플레이트
        LayoutInflater inflater_changeinfo = requireActivity().getLayoutInflater();
        View dialogView = inflater_changeinfo.inflate(R.layout.fragment_change_info, null);

        // 레이아웃에서 뷰를 찾아 초기화
        changeHeight_changeInfo = dialogView.findViewById(R.id.changeHeight_changeInfo);
        changeWeight_changeInfo = dialogView.findViewById(R.id.changeWeight_changeInfo);
        yes_changeInfo = dialogView.findViewById(R.id.yes_changeInfo);
        no_changeInfo = dialogView.findViewById(R.id.no_changeInfo);

        // '예' 버튼 클릭 리스너 설정
        yes_changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입력된 키와 몸무게를 문자열로 가져옴
                String heightStr = changeHeight_changeInfo.getText().toString();
                String weightStr = changeWeight_changeInfo.getText().toString();

                // 입력 값이 비어있지 않을 경우 처리
                if (!heightStr.isEmpty() && !weightStr.isEmpty()) {
                    int height = Integer.parseInt(heightStr); // 키를 정수로 변환
                    int weight = Integer.parseInt(weightStr); // 몸무게를 정수로 변환
                    String userid = Define.ins().userId; // Define 클래스에서 사용자 ID를 가져옴
                    // 비동기 작업을 통해 서버로 POST 요청을 보냄
                    new UpdateUserInfoTask(requireContext()).execute(height, weight, userid);
                    Define.ins().height = height;
                    Define.ins().weight = weight;
                    Log.d("DietManagement", "Stored User Info in Define:");
                    Log.d("DietManagement", "Height: " + Define.ins().height);
                    Log.d("DietManagement", "Weight: " + Define.ins().weight);
                } else {
                    // 입력 값이 비어있을 경우 경고 메시지 표시
                    Toast.makeText(getActivity(), "모든 정보를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // '아니오' 버튼 클릭 리스너 설정
        no_changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // 대화 상자를 닫음
            }
        });

        // 다이얼로그 레이아웃과 제목 설정
        builder.setView(dialogView)
                .setTitle("정보 수정");

        // AlertDialog 객체 생성 및 반환
        return builder.create();
    }

    // 사용자 정보를 업데이트하기 위한 비동기 작업 클래스
    private class UpdateUserInfoTask extends AsyncTask<Object, Void, String> {

        private final Context context;

        // 생성자를 통해 Context를 받아옴
        public UpdateUserInfoTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Object... params) {
            // 전달받은 파라미터에서 키, 몸무게, 사용자 ID를 가져옴
            int height = (int) params[0];
            int weight = (int) params[1];
            String userid = (String) params[2];
            String urlString = "http://112.172.248.92:1057/updateUserInfo";

            try {
                // URL과 HttpURLConnection 객체 생성
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // 요청 속성 설정
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);

                // JSON 객체 생성 및 데이터 설정
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("height", height);
                jsonParam.put("userid", userid);
                jsonParam.put("weight", weight);

                // JSON 데이터를 출력 스트림에 작성
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonParam.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // 서버 응답 코드 확인
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "정보가 성공적으로 업데이트되었습니다.";
                } else {
                    return "정보 업데이트에 실패했습니다. 응답 코드: " + responseCode;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "서버 요청 중 오류가 발생했습니다.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // 작업 완료 후 결과를 Toast 메시지로 표시
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            // 성공 메시지일 경우 다이얼로그 닫기
            if (result.contains("성공적으로 업데이트되었습니다.")) {
                dismiss(); // UI 스레드에서 다이얼로그 닫기
            }
        }
    }
}
