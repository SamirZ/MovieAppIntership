package com.example.zsamir.movieappintership.API;

import com.example.zsamir.movieappintership.Modules.Actor;
import com.example.zsamir.movieappintership.Modules.Credits;
import com.example.zsamir.movieappintership.Modules.MovieDetails;
import com.example.zsamir.movieappintership.Modules.Images;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.Modules.TvSeriesList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/popular")
    Call<MovieList> fetchPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieList> fetchPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Call<MovieList>fetchHighestRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieList> fetchHighestRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{id}")
    Call<MovieDetails> fetchMovie(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/credits")
    Call<Credits> fetchMovieCredits(@Path("id") int movieId, @Query("api_key") String apiKey);

    //GET person/{person_id}
    @GET("person/{id}")
    Call<Actor> fetchActorProfile(@Path("id") int actorId, @Query("api_key") String apiKey);

    //GET discover/movie
    @GET("discover/movie")
    Call<MovieList> fetchMovieListWithActor(@Query("sort_by") String sortBy ,@Query("with_people") String withPeople ,@Query("api_key") String apiKey);

    /// discover/movie?with_people=287,819&sort_by=vote_average.desc

    @GET("movie/{movie_id}/images")
    Call<Images> fetchMovieImages(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<TvSeriesList> fetchPopularTvSeries(@Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<TvSeriesList> fetchPopularTvSeries(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/top_rated")
    Call<TvSeriesList>fetchHighestRatedTvSeries(@Query("api_key") String apiKey);

    @GET("tv/top_rated")
    Call<TvSeriesList> fetchHighestRatedTvSeries(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/airing_today")
    Call<TvSeriesList> fetchAiringTodayTvSeries(@Query("api_key") String apiKey);

    @GET("tv/airing_today")
    Call<TvSeriesList> fetchAiringTodayTvSeries(@Query("api_key") String apiKey, @Query("page") int page);
}
