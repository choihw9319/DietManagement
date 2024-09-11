package com.example.dietmanagement;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    Button signUpBTN_login, loginBTN_login;
    TextView textView_login;
    EditText idET_login, pwET_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        signUpBTN_login = findViewById(R.id.signUpBTN_login);
        loginBTN_login = findViewById(R.id.loginBTN_login);
        textView_login = findViewById(R.id.textView_login);
        idET_login = findViewById(R.id.idET_login);
        pwET_login = findViewById(R.id.pwET_login);

        signUpBTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginBTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginTask().execute();
            }
        });
    }

    private class LoginTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String responseMessage = "";  // Initialize responseMessage

            try {
                // 서버 URL 설정
                URL url = new URL("http://112.172.248.92:1057/login");

                // HttpURLConnection 객체 생성 및 설정
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                // 입력된 데이터를 JSON 객체로 생성
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("userid", idET_login.getText().toString().trim());
                jsonParam.put("pw", pwET_login.getText().toString().trim());

                // JSON 데이터를 OutputStream에 작성
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonParam.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // 서버 응답 코드 확인
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 서버로부터 응답 데이터 읽기
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    reader.close();

                    // 응답 JSON 파싱
                    JSONObject jsonResponse = new JSONObject(responseBuilder.toString());

                    // Define 클래스에 저장
                    Define.ins().userId = jsonResponse.optString("userid");
                    Define.ins().pw = jsonResponse.optString("pw");
                    Define.ins().nickName = jsonResponse.optString("nick");
                    Define.ins().height = jsonResponse.optInt("height");
                    Define.ins().weight = jsonResponse.optInt("weight");
                    Define.ins().gender = jsonResponse.optString("gender");

                    // 로그에 출력
                    Log.d("DietManagement", "User Info: " + jsonResponse.toString());

                    responseMessage = "서버로부터의 성공적인 응답";  // 예시 응답 메시지

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else {
                    responseMessage = "로그인 실패: " + responseCode;
                }

            } catch (Exception e) {
                e.printStackTrace();
                responseMessage = "Exception: " + e.getMessage();
            }

            return responseMessage;
        }

        @Override
        protected void onPostExecute(String result) {
            // 로그인 성공 여부와 서버 응답값을 사용자에게 표시
            String message = "로그인 성공: " + result;
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();

            // Define 클래스의 값들을 로그에 출력
            Log.d("DietManagement", "Stored User Info in Define:");
            Log.d("DietManagement", "UserID: " + Define.ins().userId);
            Log.d("DietManagement", "Password: " + Define.ins().pw);
            Log.d("DietManagement", "Nickname: " + Define.ins().nickName);
            Log.d("DietManagement", "Height: " + Define.ins().height);
            Log.d("DietManagement", "Weight: " + Define.ins().weight);
            Log.d("DietManagement", "Gender: " + Define.ins().gender);
        }
    }
}
