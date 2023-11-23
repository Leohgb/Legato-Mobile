package com.example.legato.UserRequisicao;

import com.example.legato.objects.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface IUserService {
    @GET("/")
    Call<UserResponse> getUser(@Body User user);
}
