package com.app.ticbook;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtUsername, edtPassword;

    public SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Konfigura status bar kor default ba DashboardFragment
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setContentView(R.layout.activity_login);


        session = new SessionManager(this);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(V->{
            if (edtUsername.getText().toString().isEmpty()){
                Toast.makeText(this, "Username labele mamuk", Toast.LENGTH_SHORT).show();
            } else if (edtPassword.getText().toString().isEmpty()) {
                Toast.makeText(this, "Password labele mamuk", Toast.LENGTH_SHORT).show();
            } else {
                login();
            }
        });
    }

    private void login(){
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonBody));
        apiService.login(body).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();

                    session.setToken(token);
                    session.setUsername(username);
                    session.setLogin(true);

                    Toast.makeText(LoginActivity.this, "Login susesu", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                } else {
                    Log.e("TAG", "Error fetching login: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("TAG", "Failure fetching login: " + t.getMessage());
            }
        });

    }
}