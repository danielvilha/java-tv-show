package com.danielvilha.javatvshow.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by danielvilha on 18/10/20
 * https://github.com/danielvilha
 */
public class RetrofitBuilder {

    private final OkHttpClient client = new OkHttpClient.Builder().build();

    public Retrofit retrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        String BASE_URL = "https://api.themoviedb.org/";
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
