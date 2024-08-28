package com.example.dietmanagement;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {

    private EditText idET_singUp, nickNameET_singUp, pwET_singUp, pwCheckET_singUp, heightET_singUp, weightET_singUp;
    private Spinner genderSpinner_singUp;
    private Button idDoubleCheckBTN_singUp,signUpBTN_singUp, dismissBTN_singUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // View 초기화
        idDoubleCheckBTN_singUp = findViewById(R.id.idDoubleCheckBTN_singUp);
        idET_singUp = findViewById(R.id.idET_singUp);
        nickNameET_singUp = findViewById(R.id.nickNameET_singUp);
        pwET_singUp = findViewById(R.id.pwET_singUp);
        pwCheckET_singUp = findViewById(R.id.pwCheckET_singUp);
        heightET_singUp = findViewById(R.id.heightET_singUp);
        weightET_singUp = findViewById(R.id.weightET_singUp);
        genderSpinner_singUp = findViewById(R.id.genderSpinner_singUp);
        signUpBTN_singUp = findViewById(R.id.singUpBTN_singUp);
        dismissBTN_singUp = findViewById(R.id.dismissBTN_singUp);

        idDoubleCheckBTN_singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // "가입하기" 버튼 클릭 이벤트 처리
        signUpBTN_singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SignupTask().execute();
            }
        });

        // "취소" 버튼 클릭 이벤트 처리
        dismissBTN_singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 액티비티 종료
                finish();
            }
        });
    }

    // 비동기 작업을 통해 네트워크 작업 수행
    private class SignupTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // 서버 URL 설정
                URL url = new URL("http://112.172.248.92:1057/signup");

                // HttpURLConnection 객체 생성 및 설정
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                // 입력된 데이터를 JSON 객체로 생성
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("userid", idET_singUp.getText().toString().trim());
                jsonParam.put("nick", nickNameET_singUp.getText().toString().trim());
                jsonParam.put("pw", pwET_singUp.getText().toString().trim());
                jsonParam.put("height", Integer.parseInt(heightET_singUp.getText().toString().trim()));
                jsonParam.put("weight", Integer.parseInt(weightET_singUp.getText().toString().trim()));
                jsonParam.put("gender", genderSpinner_singUp.getSelectedItem().toString());

                // JSON 데이터를 OutputStream에 작성
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonParam.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // 서버 응답 코드 확인
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "Signup successful";
                } else {
                    return "Signup failed: " + responseCode;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // 서버 응답에 따른 처리
            Toast.makeText(SignUpActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }
}
