package ru.valentin_gordienko.loftmoney;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("auth")
    Call<AuthResponse> auth(@Query("social_user_id") String userId);

    @GET("items")
    Call<List<TransactionListItem>> getTransactions(@Query("type") String type, @Query("auth-token") String token);
}
