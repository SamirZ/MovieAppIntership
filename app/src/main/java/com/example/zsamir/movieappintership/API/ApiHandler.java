package com.example.zsamir.movieappintership.API;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.zsamir.movieappintership.BuildConfig;
import com.example.zsamir.movieappintership.Modules.Actor;
import com.example.zsamir.movieappintership.Modules.Credits;
import com.example.zsamir.movieappintership.Modules.Images;
import com.example.zsamir.movieappintership.Modules.MovieList;
import com.example.zsamir.movieappintership.Modules.TvSeries;
import com.example.zsamir.movieappintership.Modules.TvSeriesList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiHandler {

    private static ApiHandler sInstance;

    private static String sApiKey = BuildConfig.MY_KEY;

    private static ApiService sService;

    public static final String BASE_URL = "http://api.themoviedb.org/3/";


    public ApiHandler() {

    }

    public interface MovieListListener {
        public void success(MovieList response);
    }

    public interface MovieCreditsListener{
        public void success(Credits response);
    }

    public interface ActorDetailsListener{
        public void success(Actor response);
    }

    public interface MovieImagesListener{
        public void success(Images response);
    }

    public interface TvSeriesListener {
        public void success(TvSeries response);
    }

    public interface TvSeriesListListener {
        public void success(TvSeriesList response);
    }

    public static ApiHandler getInstance() {

        if (sInstance == null) {
            sInstance = new ApiHandler();
        }

        return sInstance;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    public void requestMostPopularMovies(@Nullable final MovieListListener listener) {

        getApiService().fetchPopularMovies(sApiKey).enqueue(new Callback<MovieList>() {
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

    public void requestHighestRatedMovies(@Nullable final MovieListListener listener) {

        getApiService().fetchHighestRatedMovies(sApiKey).enqueue(new Callback<MovieList>() {
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

    public void requestMovieCredits(int id, @Nullable final MovieCreditsListener listener){
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
        getApiService().fetchMovieListWithActor("sort_by=popularity.desc",Integer.toString(id),sApiKey).enqueue(new Callback<MovieList>() {
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

    public void requestMovieImages(int id, @Nullable final MovieImagesListener listener){
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

////////////////////////////////////////////////////////////////////////////////////////////////////


    public void requestMostPopularTvSeries(@Nullable final TvSeriesListListener listener) {

        getApiService().fetchPopularTvSeries(sApiKey).enqueue(new Callback<TvSeriesList>() {
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

    public void requestHighestRatedTvSeries(@Nullable final TvSeriesListListener listener) {

        getApiService().fetchHighestRatedTvSeries(sApiKey).enqueue(new Callback<TvSeriesList>() {
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
    public void requestAiringTodayTvSeries(@Nullable final TvSeriesListListener listener) {

        getApiService().fetchAiringTodayTvSeries(sApiKey).enqueue(new Callback<TvSeriesList>() {
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
