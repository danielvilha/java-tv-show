package com.danielvilha.javatvshow.service;

import com.danielvilha.javatvshow.models.TopRated;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by danielvilha on 18/10/20
 * https://github.com/danielvilha
 */
public interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("3/tv/top_rated?api_key=0c33118ab8b3541f4d5cd67e147206a5&language=en-US")
    Observable<TopRated> getTopRated(@Query("page") Integer page);
}
