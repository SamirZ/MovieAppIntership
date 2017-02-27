package com.example.zsamir.movieappintership.API;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.zsamir.movieappintership.BuildConfig;
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
import com.example.zsamir.movieappintership.Modules.Images;
import com.example.zsamir.movieappintership.Modules.MovieDetails;
import com.example.zsamir.movieappintership.Modules.TVShowDetails;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.Modules.MovieReviews;
import com.example.zsamir.movieappintership.Modules.SearchResult;
import com.example.zsamir.movieappintership.Modules.SeasonDetails;
import com.example.zsamir.movieappintership.Modules.TVShowList;
import com.example.zsamir.movieappintership.Modules.Videos;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiHandler {

    private static ApiHandler sInstance;

    private static String sApiKey = BuildConfig.MY_KEY;

    private static final String BASE_URL = "http://api.themoviedb.org/3/";

    public ApiHandler() {

    }

    public interface PostResponseListener{
        void success(PostResponse response);
    }

    public interface AccountListener {
        void success(Account response);
    }

    public interface TokenListener {
        void success(Token response);
    }

    public interface SessionListener {
        void success(Session response);
    }

    public interface VideosListener {
        void success(Videos response);
    }

    public interface EpisodeDetailsListener {
        void success(EpisodeDetails response);
    }

    public interface MovieReviewsListener {
        void success(MovieReviews response);
    }

    public interface MovieListListener {
        void success(MovieList response);
    }

    public interface MovieDetailsListener {
        void success(MovieDetails response);
    }

    public interface CreditsListener {
        void success(Credits response);
    }

    public interface EpisodeCreditsListener {
        void success(EpisodeCredits response);
    }

    public interface SearchResultListener {
        void success(SearchResult response);
    }

    public interface ActorDetailsListener{
        void success(Actor response);
    }

    public interface ImagesListener {
        void success(Images response);
    }

    public interface TvSeriesDetailsListener {
        void success(TVShowDetails response);
    }

    public interface TvSeriesListListener {
        void success(TVShowList response);
    }

    public interface TvSeriesSeasonListener {
        void success(SeasonDetails response);
    }

    public static ApiHandler getInstance() {

        if (sInstance == null) {
            sInstance = new ApiHandler();
        }

        return sInstance;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    public void requestMovieReviews(int id,@Nullable final MovieReviewsListener listener){

        getApiService().fetchMovieReviews(id, sApiKey).enqueue(new Callback<MovieReviews>() {
            @Override
            public void onResponse(Call<MovieReviews> call, Response<MovieReviews> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieReviews> call, Throwable t) {

            }
        });

    }

    public void requestMovie(int id, @Nullable final MovieDetailsListener listener) {

        getApiService().fetchMovie(id , sApiKey).enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
            }
        });
    }

    public void requestMostPopularMovies(int page, @Nullable final MovieListListener listener) {

        getApiService().fetchPopularMovies(sApiKey, page).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
            }
        });
    }

    public void requestUpcomingMovies(int page, @Nullable final MovieListListener listener) {

        getApiService().fetchUpcomingMovies(sApiKey,"US", page).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
            }
        });
    }

    public void requestHighestRatedMovies(int page, @Nullable final MovieListListener listener) {

        getApiService().fetchHighestRatedMovies(sApiKey, page).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
            }
        });
    }

    public void requestMovieCredits(int id, @Nullable final CreditsListener listener){
        getApiService().fetchMovieCredits(id,sApiKey).enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {
            }
        });
    }

    public void requestTVSeriesCredits(int id, @Nullable final CreditsListener listener){
        getApiService().fetchTVSeriesCredits(id,sApiKey).enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {
            }
        });
    }

    public void requestEpisodeCredits(int id, int s_num, int e_num, @Nullable final EpisodeCreditsListener listener){
        getApiService().fetchEpisodeCast(id,s_num,e_num,sApiKey).enqueue(new Callback<EpisodeCredits>() {
            @Override
            public void onResponse(Call<EpisodeCredits> call, Response<EpisodeCredits> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<EpisodeCredits> call, Throwable t) {

            }
        });
    }

    public void requestEpisodeDetails(int id, int s_num, int e_num, @Nullable final EpisodeDetailsListener listener){
        getApiService().fetchEpisodeDetails(id,s_num,e_num,sApiKey).enqueue(new Callback<EpisodeDetails>() {
            @Override
            public void onResponse(Call<EpisodeDetails> call, Response<EpisodeDetails> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<EpisodeDetails> call, Throwable t) {

            }
        });
    }

    public void requestActor(int id, @Nullable final ActorDetailsListener listener){
        getApiService().fetchActorProfile(id,sApiKey).enqueue(new Callback<Actor>() {
            @Override
            public void onResponse(Call<Actor> call, Response<Actor> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<Actor> call, Throwable t) {
            }
        });
    }

    public void requestMovieWithActor(int id,@Nullable final MovieListListener listener){
        getApiService().fetchMovieListWithActor("popularity.desc",Integer.toString(id),sApiKey).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
            }
        });
    }

    public void requestLatestMovies(int p,@Nullable final MovieListListener listener){

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String s = year + "-" + month + "-" + day;

        getApiService().fetchLatestMovies(sApiKey,"en-US", "primary_release_date.desc", p, s, 1).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                Log.d("CALL",call.request().toString());
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
            }
        });
    }

    public void requestMovieImages(int id, @Nullable final ImagesListener listener){
        getApiService().fetchMovieImages(id,sApiKey).enqueue(new Callback<Images>() {
            @Override
            public void onResponse(Call<Images> call, Response<Images> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<Images> call, Throwable t) {
            }
        });
    }

    public void requestTVSeriesImages(int id, @Nullable final ImagesListener listener){
        getApiService().fetchTVSeriesImages(id,sApiKey).enqueue(new Callback<Images>() {
            @Override
            public void onResponse(Call<Images> call, Response<Images> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<Images> call, Throwable t) {
            }
        });
    }

////////////////////////////////////////////////////////////////////////////////////////////////////


    public void requestMostPopularTvSeries(int page, @Nullable final TvSeriesListListener listener) {

        getApiService().fetchPopularTvSeries(sApiKey , page).enqueue(new Callback<TVShowList>() {
            @Override
            public void onResponse(Call<TVShowList> call, Response<TVShowList> response) {
                Log.d("CALL",call.request().toString());
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<TVShowList> call, Throwable t) {
            }
        });
    }

    public void requestHighestRatedTvSeries(int page, @Nullable final TvSeriesListListener listener) {

        getApiService().fetchHighestRatedTvSeries(sApiKey , page).enqueue(new Callback<TVShowList>() {
            @Override
            public void onResponse(Call<TVShowList> call, Response<TVShowList> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<TVShowList> call, Throwable t) {
            }
        });
    }

    public void requestAiringTodayTvSeries(int page, @Nullable final TvSeriesListListener listener) {

        getApiService().fetchAiringTodayTvSeries(sApiKey , page).enqueue(new Callback<TVShowList>() {
            @Override
            public void onResponse(Call<TVShowList> call, Response<TVShowList> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<TVShowList> call, Throwable t) {
            }
        });
    }

    public void requestLatestTvSeries(int p,@Nullable final TvSeriesListListener listener){

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String s = year + "-" + month + "-" + day;

        getApiService().fetchLatestTvSeries(sApiKey,"en-US", "first_air_date.desc", p, s, false).enqueue(new Callback<TVShowList>() {
            @Override
            public void onResponse(Call<TVShowList> call, Response<TVShowList> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<TVShowList> call, Throwable t) {
            }
        });
    }

    public void requestTVSeriesDetails(int id, @Nullable final TvSeriesDetailsListener listener){


        getApiService().fetchTvSeriesProducers(id,sApiKey).enqueue(new Callback<TVShowDetails>() {
            @Override
            public void onResponse(Call<TVShowDetails> call, Response<TVShowDetails> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<TVShowDetails> call, Throwable t) {
            }
        });
    }

    public void requestTVSeriesSeasons(int id, int season_num, @Nullable final TvSeriesSeasonListener listener){
        getApiService().fetchSeason(id,season_num,sApiKey).enqueue(new Callback<SeasonDetails>() {
            @Override
            public void onResponse(Call<SeasonDetails> call, Response<SeasonDetails> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<SeasonDetails> call, Throwable t) {
            }
        });
    }

    public void requestSearch(String query, int page, @Nullable final SearchResultListener listener){
        getApiService().fetchSearch(query,page,sApiKey).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (listener != null) {
                    listener.success(response.body());
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
            }
        });
    }

    public void requestMovieVideos(int id, @Nullable final VideosListener listener){
        getApiService().fetchMovieVideos(id,sApiKey).enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {
            }
        });
    }

    public void requestTVSeriesVideos(int id, @Nullable final VideosListener listener){
        getApiService().fetchTVSeriesVideos(id,sApiKey).enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {
                Log.d("URLError",t.getMessage());

            }
        });
    }

    // USER ACCOUNT REQUESTS

    public void requestToken(@Nullable final TokenListener listener){
        getApiService().fetchToken(sApiKey).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
    }

    public void validateToken(String username,String password,String token,@Nullable final TokenListener listener){
        getApiService().validateToken(sApiKey,username,password,token).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
    }

    public void requestSession(String token ,@Nullable final SessionListener listener){

        getApiService().fetchSession(sApiKey , token).enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {

            }
        });

    }

    public void requestAccount(String sessionId ,@Nullable final AccountListener listener){

        getApiService().fetchAccount(sApiKey , sessionId).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        });

    }

    public void requestAccountFavoriteMovies(Integer accId, String sessionId,Integer page, @Nullable final MovieListListener listener){
        getApiService().fetchUserFavoriteMovies(accId,sApiKey,sessionId,"created_at.asc",page).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

            }
        });
    }

    public void requestAccountFavoriteTVSeries(Integer accId, String sessionId,Integer page, @Nullable final TvSeriesListListener listener){
        getApiService().fetchUserFavoriteTVSeries(accId,sApiKey,sessionId,"created_at.asc",page).enqueue(new Callback<TVShowList>() {
            @Override
            public void onResponse(Call<TVShowList> call, Response<TVShowList> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<TVShowList> call, Throwable t) {

            }
        });
    }

    public void requestAccountWatchlistMovies(Integer accId, String sessionId,Integer page, @Nullable final MovieListListener listener){
        getApiService().fetchUserWatchlistMovies(accId,sApiKey,sessionId,"created_at.asc",page).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

            }
        });
    }

    public void requestAccountWatchlistTVSeries(Integer accId, String sessionId,Integer page, @Nullable final TvSeriesListListener listener){
        getApiService().fetchUserWatchlistTVSeries(accId,sApiKey,sessionId,"created_at.asc",page).enqueue(new Callback<TVShowList>() {
            @Override
            public void onResponse(Call<TVShowList> call, Response<TVShowList> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<TVShowList> call, Throwable t) {

            }
        });
    }

    public void requestAccountRatedMovies(Integer accId, String sessionId,Integer page, @Nullable final MovieListListener listener){
        getApiService().fetchUserRatedMovies(accId,sApiKey,sessionId,"created_at.asc",page).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

            }
        });
    }

    public void requestAccountRatedTVSeries(Integer accId, String sessionId,Integer page, @Nullable final TvSeriesListListener listener){
        getApiService().fetchUserRatedTVSeries(accId,sApiKey,sessionId,"created_at.asc",page).enqueue(new Callback<TVShowList>() {
            @Override
            public void onResponse(Call<TVShowList> call, Response<TVShowList> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<TVShowList> call, Throwable t) {

            }
        });
    }





    public void sendFavorite(Integer accId, String sessionId, Favorite favorite,@Nullable final PostResponseListener listener){
        getApiService().addFavorite(accId,sApiKey,sessionId,favorite).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    Log.d("RESPONSE", response.message());
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });
    }

    public void sendToWatchlist(Integer accId, String sessionId, Watchlist watchlist, @Nullable final PostResponseListener listener){
        getApiService().addOnWatchlist(accId,sApiKey,sessionId,watchlist).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    Log.d("RESPONSE", response.message());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });
    }

    public void rateMovie(Integer accId, String sessionId, Rating rating,@Nullable final PostResponseListener listener){
        getApiService().rateMovie(accId,sApiKey,sessionId,rating).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    Log.d("RESPONSE", response.message());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });
    }

    public void rateTVShow(Integer accId, String sessionId, Rating rating,@Nullable final PostResponseListener listener){
        getApiService().rateTVSeries(accId,sApiKey,sessionId,rating).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    Log.d("RESPONSE", response.message());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });
    }

    public void deleteRatingMovie(Integer accId, String sessionId,@Nullable final PostResponseListener listener){
        getApiService().deleteRatingMovie(accId,sApiKey,sessionId).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    Log.d("RESPONSE", response.message());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });
    }

    public void deleteRatingTVShow(Integer accId, String sessionId,@Nullable final PostResponseListener listener){
        getApiService().deleteRatingTVSeries(accId,sApiKey,sessionId).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (listener != null) {
                    Log.d("CALL",call.request().toString());
                    Log.d("CODE", String.valueOf(response.code()));
                    Log.d("RESPONSE", response.message());
                    Log.d("CODE", String.valueOf(response.code()));
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });
    }



    // SERVICES
    private ApiService getApiService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }

}
