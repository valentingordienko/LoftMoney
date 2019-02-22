package ru.valentin_gordienko.loftmoney;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {

    public static final String AUTH_PROPERTY = "auth_token";

    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if(getToken(this) != null){
            MainActivity.start(this);
            finish();
        }

        api = ((App) getApplication()).getApi();

        findViewById(R.id.auth_enter_button).setOnClickListener(v -> {
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

    public static String getToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(AUTH_PROPERTY, null);
    }
}
