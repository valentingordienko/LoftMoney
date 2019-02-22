package ru.valentin_gordienko.loftmoney;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    private String status;

    @SerializedName("auth_token")
    private String token;

    private int id;

    public AuthResponse(String status, String token, int id) {
        this.status = status;
        this.token = token;
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(int id) {
        this.id = id;
    }
}
