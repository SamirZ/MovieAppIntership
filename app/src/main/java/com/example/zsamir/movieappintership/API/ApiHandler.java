package com.example.zsamir.movieappintership.API;

import android.support.annotation.Nullable;

import com.example.zsamir.movieappintership.BuildConfig;
import com.example.zsamir.movieappintership.Modules.Actor;
import com.example.zsamir.movieappintership.Modules.Credits;
import com.example.zsamir.movieappintership.Modules.Images;
import com.example.zsamir.movieappintership.Modules.MovieDetails;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.Modules.TvSeries;
import com.example.zsamir.movieappintership.Modules.TvSeriesDetails;
import com.example.zsamir.movieappintership.Modules.TvSeriesList;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiHandler {

    private static ApiHandler sInstance;

    private static String sApiKey = BuildConfig.MY_KEY;

    private static ApiService sService;

    private static final String BASE_URL = "http://api.themoviedb.org/3/";


    public ApiHandler() {

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

    public interface ActorDetailsListener{
        void success(Actor response);
    }

    public interface ImagesListener {
        void success(Images response);
    }

    public interface TvSeriesListener {
        void success(TvSeries response);
    }

    public interface TvSeriesDetailsListener {
        void success(TvSeriesDetails response);
    }

    public interface TvSeriesListListener {
        void success(TvSeriesList response);
    }

    public static ApiHandler getInstance() {

        if (sInstance == null) {
            sInstance = new ApiHandler();
        }

        return sInstance;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

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
                if (listener != null) {
                }
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
                if (listener != null) {
                }
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
                if (listener != null) {
                }
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
                if (listener != null) {
                }
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
                if (listener != null) {
                }
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
                if (listener != null) {
                }
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
                if (listener != null) {
                }
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
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                if (listener != null) {
                }
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
                if (listener != null) {
                }
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
                if (listener != null) {
                }
            }
        });
    }

////////////////////////////////////////////////////////////////////////////////////////////////////


    public void requestMostPopularTvSeries(int page, @Nullable final TvSeriesListListener listener) {

        getApiService().fetchPopularTvSeries(sApiKey , page).enqueue(new Callback<TvSeriesList>() {
            @Override
            public void onResponse(Call<TvSeriesList> call, Response<TvSeriesList> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvSeriesList> call, Throwable t) {
                if (listener != null) {
                }
            }
        });
    }

    public void requestHighestRatedTvSeries(int page, @Nullable final TvSeriesListListener listener) {

        getApiService().fetchHighestRatedTvSeries(sApiKey , page).enqueue(new Callback<TvSeriesList>() {
            @Override
            public void onResponse(Call<TvSeriesList> call, Response<TvSeriesList> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvSeriesList> call, Throwable t) {
                if (listener != null) {
                }
            }
        });
    }

    public void requestAiringTodayTvSeries(int page, @Nullable final TvSeriesListListener listener) {

        getApiService().fetchAiringTodayTvSeries(sApiKey , page).enqueue(new Callback<TvSeriesList>() {
            @Override
            public void onResponse(Call<TvSeriesList> call, Response<TvSeriesList> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvSeriesList> call, Throwable t) {
                if (listener != null) {
                }
            }
        });
    }

    public void requestLatestTvSeries(int p,@Nullable final TvSeriesListListener listener){

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String s = year + "-" + month + "-" + day;

        getApiService().fetchLatestTvSeries(sApiKey,"en-US", "first_air_date.desc", p, s, false).enqueue(new Callback<TvSeriesList>() {
            @Override
            public void onResponse(Call<TvSeriesList> call, Response<TvSeriesList> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvSeriesList> call, Throwable t) {
                if (listener != null) {
                }
            }
        });
    }

    public void requestTVSeriesDetails(int id, @Nullable final TvSeriesDetailsListener listener){


        getApiService().fetchTvSeriesProducers(id,sApiKey).enqueue(new Callback<TvSeriesDetails>() {
            @Override
            public void onResponse(Call<TvSeriesDetails> call, Response<TvSeriesDetails> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvSeriesDetails> call, Throwable t) {
                if (listener != null) {
                }
            }
        });
    }

    // SERVICES
    private ApiService getApiService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sService = retrofit.create(ApiService.class);

        return sService;
    }

}
