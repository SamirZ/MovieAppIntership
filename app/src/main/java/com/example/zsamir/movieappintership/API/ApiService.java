package com.example.zsamir.movieappintership.API;

import com.example.zsamir.movieappintership.LoginModules.Account;
import com.example.zsamir.movieappintership.LoginModules.Favorite;
import com.example.zsamir.movieappintership.LoginModules.PostResponse;
import com.example.zsamir.movieappintership.LoginModules.Rating;
import com.example.zsamir.movieappintership.LoginModules.Session;
import com.example.zsamir.movieappintership.LoginModules.Token;
import com.example.zsamir.movieappintership.LoginModules.Watchlist;
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
import com.example.zsamir.movieappintership.Modules.TVShowList;
import com.example.zsamir.movieappintership.Modules.TVShowDetails;
import com.example.zsamir.movieappintership.Modules.Videos;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface ApiService {

    @GET("movie/popular")
    Call<MovieList> fetchPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Call<MovieList> fetchHighestRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/upcoming")
    Call<MovieList> fetchUpcomingMovies(@Query("api_key") String apiKey,@Query("region") String region, @Query("page") int page);

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
    Call<TVShowList> fetchPopularTvSeries(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/top_rated")
    Call<TVShowList> fetchHighestRatedTvSeries(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/airing_today")
    Call<TVShowList> fetchAiringTodayTvSeries(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("discover/tv")
    Call<TVShowList> fetchLatestTvSeries(@Query("api_key") String apiKey, @Query("language") String language, @Query("sort_by") String sortBy, @Query("page") int page, @Query("first_air_date.lte") String firstAirDate, @Query("include_null_first_air_dates") boolean include_null_first_air_dates);

    @GET("tv/{id}")
    Call<TVShowDetails> fetchTvSeriesProducers(@Path("id") int id, @Query("api_key") String apiKey);

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


    //// USER ACCOUNT GET

    @GET("authentication/token/new")
    Call<Token> fetchToken(@Query("api_key") String apiKey);

    @GET("authentication/token/validate_with_login")
    Call<Token> validateToken(@Query("api_key") String apiKey,@Query("username") String username,@Query("password") String password,@Query("request_token") String request_token);

    @GET("authentication/session/new")
    Call<Session> fetchSession(@Query("api_key") String apiKey,@Query("request_token") String request_token);

    @GET("account")
    Call<Account> fetchAccount(@Query("api_key") String apiKey,@Query("session_id") String session_id);

    @GET("account/{account_id}/favorite/movies")
    Call<MovieList> fetchUserFavoriteMovies(@Path("account_id") Integer id,@Query("api_key") String apiKey,@Query("session_id") String session_id, @Query("sort_by") String sortBy, @Query("page") int page);

    @GET("account/{account_id}/favorite/tv")
    Call<TVShowList> fetchUserFavoriteTVSeries(@Path("account_id") Integer id, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Query("sort_by") String sortBy, @Query("page") int page);

    @GET("account/{account_id}/watchlist/movies")
    Call<MovieList> fetchUserWatchlistMovies(@Path("account_id") Integer id,@Query("api_key") String apiKey,@Query("session_id") String session_id, @Query("sort_by") String sortBy, @Query("page") int page);

    @GET("account/{account_id}/watchlist/tv")
    Call<TVShowList> fetchUserWatchlistTVSeries(@Path("account_id") Integer id, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Query("sort_by") String sortBy, @Query("page") int page);

    @GET("account/{account_id}/rated/movies")
    Call<MovieList> fetchUserRatedMovies(@Path("account_id") Integer id,@Query("api_key") String apiKey,@Query("session_id") String session_id, @Query("sort_by") String sortBy, @Query("page") int page);

    @GET("account/{account_id}/rated/tv")
    Call<TVShowList> fetchUserRatedTVSeries(@Path("account_id") Integer id, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Query("sort_by") String sortBy, @Query("page") int page);

    //// USER ACCOUNT POST

    @POST("account/{account_id}/favorite")
    Call<PostResponse> addFavorite(@Path("account_id") Integer id, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Body Favorite favorite);

    @POST("account/{account_id}/watchlist")
    Call<PostResponse> addOnWatchlist(@Path("account_id") Integer id, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Body Watchlist watchlist);

    //https://api.themoviedb.org/3/movie/328111/rating?api_key=bc269ac4441457a0c9182c49437eaf89&session_id=213

    @POST("movie/{id}/rating")
    Call<PostResponse> rateMovie(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Body Rating rating);

    @POST("tv/{id}/rating")
    Call<PostResponse> rateTVSeries(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Body Rating rating);

    //DELETE RATING

    @DELETE("movie/{id}/rating")
    Call<PostResponse> deleteRatingMovie(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("session_id") String session_id);

    @DELETE("tv/{id}/rating")
    Call<PostResponse> deleteRatingTVSeries(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("session_id") String session_id);

}
