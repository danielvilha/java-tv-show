package com.danielvilha.javatvshow.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by danielvilha on 18/10/20
 * https://github.com/danielvilha
 */
public class TopRated {
    @SerializedName("page")
    public Integer page;
    @SerializedName("total_results")
    public Integer total_results;
    @SerializedName("total_pages")
    public Integer total_pages;
    @SerializedName("results")
    public ArrayList<TopRatedResult> results;

    public TopRated(Integer page, Integer total_results, Integer total_pages, ArrayList<TopRatedResult> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    /**
     * Here presents a top rated result.
     */
    public static class TopRatedResult implements Serializable, Comparable<TopRatedResult> {
        @SerializedName("original_name")
        public final String original_name;
        @SerializedName("id")
        public final Integer id;
        @SerializedName("name")
        public final String name;
        @SerializedName("popularity")
        public final Float popularity;
        @SerializedName("vote_count")
        public final Integer vote_count;
        @SerializedName("vote_average")
        public final Float vote_average;
        @SerializedName("first_air_date")
        public final String first_air_date;
        @SerializedName("poster_path")
        public final String poster_path;
        @SerializedName("genre_ids")
        public final ArrayList<String> genre_ids;
        @SerializedName("original_language")
        public final String original_language;
        @SerializedName("backdrop_path")
        public final String backdrop_path;
        @SerializedName("overview")
        public final String overview;
        @SerializedName("origin_country")
        public final ArrayList<String> origin_country;

        public TopRatedResult(String original_name, Integer id, String name, Float popularity, Integer vote_count, Float vote_average, String first_air_date, String poster_path, ArrayList<String> genre_ids, String original_language, String backdrop_path, String overview, ArrayList<String> origin_country) {
            this.original_name = original_name;
            this.id = id;
            this.name = name;
            this.popularity = popularity;
            this.vote_count = vote_count;
            this.vote_average = vote_average;
            this.first_air_date = first_air_date;
            this.poster_path = poster_path;
            this.genre_ids = genre_ids;
            this.original_language = original_language;
            this.backdrop_path = backdrop_path;
            this.overview = overview;
            this.origin_country = origin_country;
        }

        @Override
        public int compareTo(TopRatedResult topRatedResult) {
            return this.name.compareTo(topRatedResult.name);
        }
    }
}
