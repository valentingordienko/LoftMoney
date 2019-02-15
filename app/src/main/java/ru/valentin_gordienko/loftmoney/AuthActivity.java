package ru.valentin_gordienko.loftmoney;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.material.button.MaterialButton;

import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = "AuthActivity";
    public static final String AUTH_PROPERTY = "auth_token";

    private MaterialButton enterButton;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if(getToken() != null){
            MainActivity.start(this);
            finish();
        }

        this.enterButton = findViewById(R.id.auth_enter_button);
        this.api = ((App) getApplication()).getApi();

        enterButton.setOnClickListener(v -> {
            auth();
        });
    }

    private void auth(){
        Call<AuthResponse> call = api.auth(UUID.randomUUID().toString());

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                AuthResponse authResponse = response.body();
                assert authResponse != null;
                String token = authResponse.getToken();
                saveToken(token);
                MainActivity.start(AuthActivity.this);
                finish();
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {

            }
        });
    }

    private void saveToken(String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putString(AUTH_PROPERTY, token).apply();
    }

    private String getToken() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        return preferences.getString(AUTH_PROPERTY, null);
    }
}
