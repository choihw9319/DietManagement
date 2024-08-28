package com.example.dietmanagement;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {

    private EditText idET_singUp, nickNameET_singUp, pwET_singUp, pwCheckET_singUp, heightET_singUp, weightET_singUp;
    private Spinner genderSpinner_singUp;
    private Button idDoubleCheckBTN_singUp, signUpBTN_singUp, dismissBTN_singUp;
    private Boolean res;
    private Boolean isIdChecked; // 중복 체크 여부 확인

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
        res = false;
        isIdChecked = false; // 초기에는 중복 체크가 수행되지 않았음을 나타냄

        idDoubleCheckBTN_singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CheckUserIdTask().execute();
            }
        });

        // "가입하기" 버튼 클릭 이벤트 처리
        signUpBTN_singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = pwET_singUp.getText().toString().trim();
                String passwordCheck = pwCheckET_singUp.getText().toString().trim();

                if (!isIdChecked) {
                    Toast.makeText(SignUpActivity.this, "아이디 중복 체크를 해주세요.", Toast.LENGTH_LONG).show();
                } else if (!res) {
                    Toast.makeText(SignUpActivity.this, "아이디가 중복됩니다. 다른 아이디를 사용하세요.", Toast.LENGTH_LONG).show();
                }
                else if (!password.equals(passwordCheck)) {
                    // 비밀번호가 서로 다를 때의 처리
                    Toast.makeText(SignUpActivity.this, "비밀번호가 서로 다릅니다.", Toast.LENGTH_LONG).show();
                }
                else {
                    new SignupTask().execute();
                }
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
                if (responseCode == HttpURLConnection.HTTP_OK && res && pwET_singUp.getText().toString().trim().equals(pwCheckET_singUp.getText().toString().trim())) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
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

    private class CheckUserIdTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // 서버 URL 설정
                URL url = new URL("http://112.172.248.92:1057/checkUserId");

                // HttpURLConnection 객체 생성 및 설정
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                // 입력된 데이터를 JSON 객체로 생성
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("userid", idET_singUp.getText().toString().trim());

                // JSON 데이터를 OutputStream에 작성
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonParam.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // 서버 응답 코드 확인
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 서버의 응답을 읽어와서 처리
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // 서버 응답에 따라 로직 처리
                    String responseStr = response.toString().trim();
                    isIdChecked = true; // 중복 체크 수행 완료 표시

                    if (responseStr.equals("true")) { // 서버 응답이 "true"인 경우 중복 아이디 존재
                        res = false;
                        return "아이디가 중복됩니다. 다른 아이디를 사용하세요.";
                    } else {
                        res = true; // 사용 가능한 아이디
                        return "사용 가능한 아이디입니다.";
                    }
                } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    // 404 오류가 발생한 경우 - 사용 가능한 아이디로 간주
                    isIdChecked = true;
                    res = true;
                    return "사용 가능한 아이디입니다.";
                }
                else {
                    return "서버 오류: " + responseCode;
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
