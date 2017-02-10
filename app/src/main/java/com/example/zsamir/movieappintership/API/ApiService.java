package com.example.zsamir.movieappintership.API;

import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.LoginModules.Session;
import com.example.zsamir.movieappintership.LoginModules.Token;
import com.example.zsamir.movieappintership.Modules.Actor;
import com.example.zsamir.movieappintership.Modules.Credits;
import com.example.zsamir.movieappintership.Modules.EpisodeCredits;
import com.example.zsamir.movieappintership.Modules.EpisodeDetails;
import com.example.zsamir.movieappintership.Modules.MovieDetails;
import com.example.zsamir.movieappintership.Modules.Images;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.Modules.MovieReviews;
import com.example.zsamir.movieappintership.Modules.SearchResult;
import com.example.zsamir.movieappintership.Modules.SeasonDetails;
import com.example.zsamir.movieappintership.Modules.TvSeriesDetails;
import com.example.zsamir.movieappintership.Modules.TvSeriesList;
import com.example.zsamir.movieappintership.Modules.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface ApiService {

    @GET("movie/popular")
    Call<MovieList> fetchPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Call<MovieList> fetchHighestRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{id}")
    Call<MovieDetails> fetchMovie(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/credits")
    Call<Credits> fetchMovieCredits(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("tv/{id}/credits")
    Call<Credits> fetchTVSeriesCredits(@Path("id") int tvSeriesId, @Query("api_key") String apiKey);

    //GET person/{person_id}
    @GET("person/{id}")
    Call<Actor> fetchActorProfile(@Path("id") int actorId, @Query("api_key") String apiKey);

    //GET discover/movie
    @GET("discover/movie")
    Call<MovieList> fetchMovieListWithActor(@Query("sort_by") String sortBy ,@Query("with_people") String withPeople ,@Query("api_key") String apiKey);

    /// discover/movie?with_people=287,819&sort_by=vote_average.desc

    @GET("movie/{id}/images")
    Call<Images> fetchMovieImages(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("tv/{id}/images")
    Call<Images> fetchTVSeriesImages(@Path("id") int tvSeriesId, @Query("api_key") String apiKey);

    @GET ("discover/movie")
    Call<MovieList> fetchLatestMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("sort_by") String sortBy, @Query("page") int page, @Query("release_date.lte") String releaseDate, @Query("vote_average.gte") int voteAverage);

    @GET("tv/popular")
    Call<TvSeriesList> fetchPopularTvSeries(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/top_rated")
    Call<TvSeriesList> fetchHighestRatedTvSeries(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/airing_today")
    Call<TvSeriesList> fetchAiringTodayTvSeries(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("discover/tv")
    Call<TvSeriesList> fetchLatestTvSeries(@Query("api_key") String apiKey, @Query("language") String language, @Query("sort_by") String sortBy, @Query("page") int page, @Query("first_air_date.lte") String firstAirDate, @Query("include_null_first_air_dates") boolean include_null_first_air_dates);

    @GET("tv/{id}")
    Call<TvSeriesDetails> fetchTvSeriesProducers(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<MovieReviews> fetchMovieReviews(@Path("id") int id, @Query("api_key") String apiKey);




    @GET("tv/{id}/season/{season_number}/episode/{episode_number}/credits")
    Call<EpisodeCredits> fetchEpisodeCast(@Path("id") int tv_id,
                                          @Path("season_number") Integer season_number,
                                          @Path("episode_number") Integer episode_number,
                                          @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/season/{season_number}")
    Call<SeasonDetails> fetchSeason(@Path("tv_id") int tv_id,
                                    @Path("season_number") Integer season_number,
                                    @Query("api_key") String apiKey);

    @GET("tv/{id}/season/{season_number}/episode/{episode_number}")
    Call<EpisodeDetails> fetchEpisodeDetails(@Path("id") int tv_id,
                                          @Path("season_number") Integer season_number,
                                          @Path("episode_number") Integer episode_number,
                                          @Query("api_key") String apiKey);

    @GET("search/multi")
    Call<SearchResult> fetchSearch(@Query("query") String query, @Query("page") int page, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<Videos> fetchMovieVideos(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/videos")
    Call<Videos> fetchTVSeriesVideos(@Path("tv_id") int id, @Query("api_key") String apiKey);


    //// USER ACCOUNT

    @GET("authentication/token/new")
    Call<Token> fetchToken(@Query("api_key") String apiKey);

    @GET("authentication/token/validate_with_login")
    Call<Token> validateToken(@Query("api_key") String apiKey,@Query("username") String username,@Query("password") String password,@Query("request_token") String request_token);

    @GET("authentication/session/new")
    Call<Session> fetchSession(@Query("api_key") String apiKey,@Query("request_token") String request_token);

    @GET("account")
    Call<Account> fetchAccount(@Query("api_key") String apiKey,@Query("session_id") String session_id);

}
