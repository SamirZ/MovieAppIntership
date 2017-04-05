package com.example.zsamir.movieappintership.RealmUtils;

import android.util.Log;

import com.example.zsamir.movieappintership.Modules.Actor;
import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.CreatedBy;
import com.example.zsamir.movieappintership.Modules.Crew;
import com.example.zsamir.movieappintership.Modules.Episode;
import com.example.zsamir.movieappintership.Modules.EpisodeDetails;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.MovieReview;
import com.example.zsamir.movieappintership.Modules.ProductionCountry;
import com.example.zsamir.movieappintership.Modules.Season;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.Modules.TVShowDetails;
import com.example.zsamir.movieappintership.NewsFeed.FeedItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmUtils {


    private static RealmUtils instance = null;

    public RealmUtils() {
    }

    public static RealmUtils getInstance() {
        if(instance == null) {
            instance = new RealmUtils();
        }
        return instance;
    }

    public Realm realm = Realm.getDefaultInstance();

    public ArrayList<FeedItem> readNewsFeedItemsFromRealm(){

        realm.beginTransaction();

        ArrayList<FeedItem> feedItems  = new ArrayList<>(realm.where(FeedItem.class).findAll());

        realm.commitTransaction();

        return feedItems;

    }



    public ArrayList<Movie> readActorMoviesFromRealm(int id){

        realm.beginTransaction();

        RealmActorDetails actorDetails = realm.where(RealmActorDetails.class).equalTo("id",id).findFirst();

        realm.commitTransaction();

        return new ArrayList<>(actorDetails.getMovies());

    }

    public Movie readMovieFromRealm(int id){

        return realm.where(Movie.class).equalTo("id",id).findFirst();

    }

    public ArrayList<Movie> readPopularMoviesFromRealm(){

        realm.beginTransaction();

        ArrayList<Movie> movies  = new ArrayList<>(realm.where(Movie.class).equalTo("popular",true).findAll());

        realm.commitTransaction();

        return movies;

    }

    public ArrayList<Movie> readLatestMoviesFromRealm(){

        realm.beginTransaction();

        ArrayList<Movie> movies  = new ArrayList<>(realm.where(Movie.class).equalTo("latest",true).findAll());

        realm.commitTransaction();

        return movies;

    }

    public ArrayList<Movie> readHighestRatedMoviesFromRealm(){

        realm.beginTransaction();

        ArrayList<Movie> movies  = new ArrayList<>(realm.where(Movie.class).equalTo("highestrated",true).findAll());

        realm.commitTransaction();

        return movies;

    }

    public ArrayList<TVShow> readPopularTVShowsFromRealm() {
        realm.beginTransaction();

        ArrayList<TVShow> tvShows  = new ArrayList<>(realm.where(TVShow.class).equalTo("popular",true).findAll());

        realm.commitTransaction();

        return tvShows;
    }

    public ArrayList<TVShow> readAiringTodayTVShowsFromRealm() {
        realm.beginTransaction();

        ArrayList<TVShow> tvShows  = new ArrayList<>(realm.where(TVShow.class).equalTo("airing",true).findAll());

        realm.commitTransaction();

        return tvShows;
    }

    public ArrayList<TVShow> readLatestTVShowsFromRealm() {
        realm.beginTransaction();

        ArrayList<TVShow> tvShows  = new ArrayList<>(realm.where(TVShow.class).equalTo("latest",true).findAll());

        realm.commitTransaction();

        return tvShows;
    }

    public ArrayList<TVShow> readHighestRatedTVShowsFromRealm() {
        realm.beginTransaction();

        ArrayList<TVShow> tvShows  = new ArrayList<>(realm.where(TVShow.class).equalTo("highestrated",true).findAll());

        realm.commitTransaction();

        return tvShows;
    }

    public RealmMovieDetails readRealmMovieDetails(int id) {
        realm.beginTransaction();

        RealmMovieDetails result = realm.where(RealmMovieDetails.class)
                .equalTo("id", id)
                .findFirst();

        realm.commitTransaction();
        return result;
    }

    public RealmTVShowDetails readRealmTVShowDetails(int id) {
        realm.beginTransaction();

        ArrayList<RealmTVShowDetails> result = new ArrayList<>(realm.where(RealmTVShowDetails.class)
                .equalTo("id", id)
                .findAll());

        realm.commitTransaction();
        if(result.size()>0)
            return result.get(0);
        return null;
    }

    public RealmSeasonDetails readRealmSeasonDetails(String id) {
        realm.beginTransaction();

        ArrayList<RealmSeasonDetails> result = new ArrayList<>(realm.where(RealmSeasonDetails.class)
                .equalTo("id", id)
                .findAll());

        realm.commitTransaction();
        if(result.size()>0)
            return result.get(0);
        return null;
    }

    public RealmActorDetails readRealmActorDetails(int id) {
        realm.beginTransaction();

        ArrayList<RealmActorDetails> result = new ArrayList<>(realm.where(RealmActorDetails.class)
                .equalTo("id", id)
                .findAll());

        realm.commitTransaction();
        if(result.size()>0)
            return result.get(0);
        return null;
    }


    public void createRealmMovieDetails(int id) {
        deleteMovieDetails(id);
        realm.beginTransaction();
        realm.createObject(RealmMovieDetails.class,id);
        realm.commitTransaction();
    }

    public void createRealmTVShowDetails(int id) {
        deleteTVShowDetails(id);
        realm.beginTransaction();
        realm.createObject(RealmTVShowDetails.class,id);
        realm.commitTransaction();
    }

    public void createRealmSeasonDetails(String id) {
        deleteSeasonDetails(id);
        realm.beginTransaction();
        realm.createObject(RealmSeasonDetails.class,id);
        realm.commitTransaction();
    }

    public void addRealmActorDetailsMovies(int id,ArrayList<Movie> movies){

        realm.beginTransaction();

        RealmActorDetails actorDetails = realm.where(RealmActorDetails.class).equalTo("id",id).findFirst();

        RealmList<Movie> movieRealmList = new RealmList<>();

        for (Movie m:movies) {
            Movie realmMovie = realm.where(Movie.class).equalTo("id", m.getId()).findFirst();

            if(realmMovie==null){
                movieRealmList.add(realm.copyToRealmOrUpdate(m));
            }
            else{
                movieRealmList.add(realmMovie);
            }
        }

        actorDetails.setMovies(movieRealmList);

        realm.commitTransaction();

    }

    public void createRealmActorDetails(int id,Cast c,Actor a) {

        deleteActorDetails(id);

        realm.beginTransaction();

        RealmActorDetails actorDetails = realm.createObject(RealmActorDetails.class,id);

        //CAST DATA
        actorDetails.setCast(realm.copyToRealmOrUpdate(c));

        //ACTOR DATA
        actorDetails.setActor(realm.copyToRealmOrUpdate(a));

        realm.commitTransaction();
    }


    public void addRealmMovieDetailsDirector(int id, Crew director) {
        realm.beginTransaction();
        RealmMovieDetails movieDetails = realm.where(RealmMovieDetails.class).equalTo("id",id).findFirst();
        movieDetails.setDirector(realm.copyToRealmOrUpdate(director));
        realm.commitTransaction();
    }

    public void addRealmTVShowDetails(int id, TVShowDetails tvShowDetails) {
        realm.beginTransaction();
        RealmTVShowDetails tvDetails = realm.where(RealmTVShowDetails.class).equalTo("id",id).findFirst();
        tvDetails.setTvShowDetails(realm.copyToRealmOrUpdate(tvShowDetails));
        realm.commitTransaction();
    }

    public void addRealmTVShowDetailsCreatedBy(int id, CreatedBy director) {
        deleteCreatedBy(director);
        realm.beginTransaction();
        RealmTVShowDetails tvDetails = realm.where(RealmTVShowDetails.class).equalTo("id",id).findFirst();
        tvDetails.setCreatedBy(realm.copyToRealmOrUpdate(director));
        realm.commitTransaction();
    }

    public void addRealmTVShowDetailsLastAirDate(int id, String lastAirDate) {
        realm.beginTransaction();
        RealmTVShowDetails tvDetails = realm.where(RealmTVShowDetails.class).equalTo("id",id).findFirst();
        tvDetails.setLastAirDate(lastAirDate);
        realm.commitTransaction();
    }

    public void addRealmMovieDetailsProductionCountry(int id, ProductionCountry p) {
        deleteProductionCountry(p);
        realm.beginTransaction();
        RealmMovieDetails movieDetails = realm.where(RealmMovieDetails.class).equalTo("id",id).findFirst();
        movieDetails.setProductionCountry(realm.copyToRealmOrUpdate(p));
        realm.commitTransaction();
    }


    public void addRealmMovieDetailsActors(final int id, final ArrayList<Cast> actors) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmMovieDetails movieDetails = realm.where(RealmMovieDetails.class).equalTo("id",id).findFirst();
                for (Cast c:actors) {
                    movieDetails.getActors().add(realm.copyToRealmOrUpdate(c));
                }
            }
        });
    }

    public void addRealmTVShowDetailsActors(final int id, final ArrayList<Cast> actors) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmTVShowDetails tvDetails = realm.where(RealmTVShowDetails.class).equalTo("id",id).findFirst();
                for (Cast c:actors) {
                    tvDetails.getActors().add(realm.copyToRealmOrUpdate(c));
                }
            }
        });
    }

    public void addRealmMovieDetailsWriters(final int id, final ArrayList<Crew> writers) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmMovieDetails movieDetails = realm.where(RealmMovieDetails.class).equalTo("id",id).findFirst();
                for (Crew c:writers) {
                    movieDetails.getWriters().add(realm.copyToRealmOrUpdate(c));
                }
            }
        });
    }

    public void addRealmTVShowDetailsWriters(final int id, final List<CreatedBy> writers) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmTVShowDetails tvDetails = realm.where(RealmTVShowDetails.class).equalTo("id",id).findFirst();
                for (CreatedBy c:writers) {
                    tvDetails.getWriters().add(realm.copyToRealmOrUpdate(c));
                }
            }
        });
    }

    public void addRealmTVShowDetailsBackdrops(final int id, final ArrayList<Backdrop> backdrops) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmTVShowDetails tvDetails = realm.where(RealmTVShowDetails.class).equalTo("id",id).findFirst();
                for (Backdrop b:backdrops) {
                    tvDetails.getBackdrops().add(realm.copyToRealmOrUpdate(b));
                }
            }
        });
    }

    public void addRealmMovieDetailsBackrops(final int id, final ArrayList<Backdrop> backdrops) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmMovieDetails movieDetails = realm.where(RealmMovieDetails.class).equalTo("id",id).findFirst();
                for (Backdrop b:backdrops) {
                    movieDetails.getBackdrops().add(realm.copyToRealmOrUpdate(b));
                }
            }
        });
    }

    public void addRealmMovieDetailsReviews(final int id, final List<MovieReview> reviews) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmMovieDetails movieDetails = realm.where(RealmMovieDetails.class).equalTo("id",id).findFirst();
                for (MovieReview r:reviews) {
                    movieDetails.getMovieReviews().add(realm.copyToRealmOrUpdate(r));
                }
            }
        });
    }

    public void addRealmTVShowDetailsSeasons(final int id, final List<Season> seasons) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmTVShowDetails tvDetails = realm.where(RealmTVShowDetails.class).equalTo("id",id).findFirst();
                for (Season s:seasons) {
                    tvDetails.getSeasons().add(realm.copyToRealmOrUpdate(s));
                }
            }
        });
    }

    public void addRealmSeasonsDetailsEpisodes(final String id, final List<Episode> episodes) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmSeasonDetails seasonDetails = realm.where(RealmSeasonDetails.class).equalTo("id",id).findFirst();
                for (Episode s:episodes) {
                    seasonDetails.getEpisodes().add(realm.copyToRealmOrUpdate(s));
                }
            }
        });
    }


    public void addNewsFeedItemsToRealm(ArrayList<FeedItem> feedItems) {

        for (final FeedItem f:feedItems) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(f);
                }
            });
        }

    }

    public void addMoviesToRealm(ArrayList<Movie> moviesList, final String t) {
        if(moviesList!=null)
            for ( final Movie m : moviesList) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(m);
                    }
                });
            }

    }

    public void addTVShowsToRealm(ArrayList<TVShow> tvShowList) {

        for ( final TVShow t : tvShowList) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(t);
                }
            });
        }
    }

    public void deleteAllNewsFeed(){
        final RealmResults<FeedItem> results = realm.where(FeedItem.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteAllMovies(){
        final RealmResults<Movie> results = realm.where(Movie.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteRealmAccount(){
        final RealmResults<RealmAccount> results = realm.where(RealmAccount.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteMovieDetails(int id){
        final RealmResults<RealmMovieDetails> results = realm.where(RealmMovieDetails.class).equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteTVShowDetails(int id){
        final RealmResults<RealmTVShowDetails> results = realm.where(RealmTVShowDetails.class).equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteAllTVShows() {
        final RealmResults<TVShow> results = realm.where(TVShow.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm)
            {
                results.deleteAllFromRealm();
            }
        });
    }

    private void deleteCreatedBy(CreatedBy director) {
        final RealmResults<CreatedBy> results = realm.where(CreatedBy.class).equalTo("id",director.getId()).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    private void deleteProductionCountry(ProductionCountry productionCountry) {
        final RealmResults<ProductionCountry> results = realm.where(ProductionCountry.class).equalTo("iso31661",productionCountry.getIso31661()).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteCast(int id){
        final RealmResults<Cast> results = realm.where(Cast.class).equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteActor(int id) {
        final RealmResults<Actor> results = realm.where(Actor.class).equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }
    private void deleteActorDetails(int id) {
        final RealmResults<RealmActorDetails> results = realm.where(RealmActorDetails.class).equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteSeasonDetails(String id) {
        final RealmResults<RealmSeasonDetails> results = realm.where(RealmSeasonDetails.class)
                .equalTo("id",id)
                .findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteEpisodeDetails(String id) {
        final RealmResults<RealmEpisodeDetails> results = realm.where(RealmEpisodeDetails.class)
                .equalTo("id",id)
                .findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void createRealmEpisodeDetails(String id,int seasonNumber,int episodeNumber) {
        deleteEpisodeDetails(id);
        realm.beginTransaction();
        RealmEpisodeDetails episodeDetails = realm.createObject(RealmEpisodeDetails.class, id);
        episodeDetails.setSeasonNumber(seasonNumber);
        episodeDetails.setEpisodeNumber(episodeNumber);
        realm.commitTransaction();
    }

    public void addRealmEpisodeDetailsCast(String id, ArrayList<Cast> actors) {
        realm.beginTransaction();
        RealmEpisodeDetails tvDetails = realm.where(RealmEpisodeDetails.class)
                .equalTo("id",id)
                .findFirst();

        if(actors!=null)
            for (Cast c:actors) {
                tvDetails.getEpisodeCast().add(realm.copyToRealmOrUpdate(c));
            }
        realm.commitTransaction();
    }


    public RealmEpisodeDetails readEpisodeDetails(String id) {
        realm.beginTransaction();

        RealmEpisodeDetails result = realm.where(RealmEpisodeDetails.class)
                .equalTo("id",id)
                .findFirst();

        realm.commitTransaction();
        return result;
    }

    public void addRealmEpisodeDetailsData(String id,EpisodeDetails response) {
        realm.beginTransaction();
        RealmEpisodeDetails tvDetails = realm.where(RealmEpisodeDetails.class)
                .equalTo("id",id)
                .findFirst();

        tvDetails.setEpisodeDetails(realm.copyToRealmOrUpdate(response));

        realm.commitTransaction();
    }

    public void createRealmAccount() {
        deleteRealmAccount();
        realm.beginTransaction();
        realm.createObject(RealmAccount.class);
        realm.commitTransaction();
    }

    public RealmAccount readRealmAccount() {
        realm.beginTransaction();
        RealmAccount result = realm.where(RealmAccount.class).findFirst();
        realm.commitTransaction();
        return result;
    }

    public void addRealmAccountFavMovies(final List<Integer> integers) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmAccount realmAccount = realm.where(RealmAccount.class).findFirst();
                for (Integer i:integers) {
                    RealmInteger object = new RealmInteger(i);
                    realmAccount.getFavMovieList().add(realm.copyToRealmOrUpdate(object));
                }
            }
        });
    }

    public void removeRealmAccountFavMovie(final int id) {
        final RealmResults<RealmInteger> results = realm.where(RealmInteger.class).equalTo("i",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void addRealmAccountFavTVShow(final List<Integer> integers) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmAccount realmAccount = realm.where(RealmAccount.class).findFirst();
                for (Integer i:integers) {
                    RealmInteger object = new RealmInteger(i);
                    realmAccount.getFavTVSeriesList().add(realm.copyToRealmOrUpdate(object));
                }
            }
        });
    }

    public void removeRealmAccountFavTVShow(final int id) {
        final RealmResults<RealmInteger> results = realm.where(RealmInteger.class).equalTo("i",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void addRealmAccountWatchlistMovies(final List<Integer> integers) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmAccount realmAccount = realm.where(RealmAccount.class).findFirst();
                for (Integer i:integers) {
                    RealmInteger object = new RealmInteger(i);
                    realmAccount.getWatchlistMovieList().add(realm.copyToRealmOrUpdate(object));
                }
            }
        });
    }

    public void removeRealmAccountWatchlistMovies(final int id) {
        final RealmResults<RealmInteger> results = realm.where(RealmInteger.class).equalTo("i",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void addRealmAccountWatchlistTVShow(final List<Integer> integers) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmAccount realmAccount = realm.where(RealmAccount.class).findFirst();
                for (Integer i:integers) {
                    RealmInteger object = new RealmInteger(i);
                    realmAccount.getWatchlistTVSeriesList().add(realm.copyToRealmOrUpdate(object));
                }
            }
        });
    }

    public void removeRealmAccountWatchlistTVShow(final int id) {
        final RealmResults<RealmInteger> results = realm.where(RealmInteger.class).equalTo("i",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void addRealmAccountRatedMovies(final List<Integer> integers) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmAccount realmAccount = realm.where(RealmAccount.class).findFirst();
                for (Integer i:integers) {
                    RealmInteger object = new RealmInteger(i);
                    realmAccount.getRatedMovieList().add(realm.copyToRealmOrUpdate(object));
                }
            }
        });
    }

    public void removeRealmAccountRatedMovies(final int id) {
        final RealmResults<RealmInteger> results = realm.where(RealmInteger.class).equalTo("i",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void addRealmAccountRatedTVShow(final List<Integer> integers) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmAccount realmAccount = realm.where(RealmAccount.class).findFirst();
                for (Integer i:integers) {
                    RealmInteger object = new RealmInteger(i);
                    realmAccount.getRatedTVSeriesList().add(realm.copyToRealmOrUpdate(object));
                }
            }
        });
    }

    public void removeRealmAccountRatedTVShow(final int id) {
        final RealmResults<RealmInteger> results = realm.where(RealmInteger.class).equalTo("i",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public TVShow readTVShowFromRealm(int id) {
        return realm.where(TVShow.class).equalTo("id",id).findFirst();
    }

    public void createOrUpdatePostModel(final PostModel p){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(p);
            }
        });
    }

    public ArrayList<PostModel> readPostModels(){
        realm.beginTransaction();

        ArrayList<PostModel> feedItems  = new ArrayList<>(realm.where(PostModel.class).findAll());

        realm.commitTransaction();

        return feedItems;
    }

    public PostModel readPostModel(int id){
        realm.beginTransaction();
        PostModel p = realm.where(PostModel.class).equalTo("id",id).findFirst();
        realm.commitTransaction();
        return p;
    }

    public void deletePostModel(int id){
        final RealmResults<PostModel> postModels = realm.where(PostModel.class).equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                postModels.deleteAllFromRealm();
            }
        });
    }

    public void deletePostModels(){
        final RealmResults<PostModel> postModels = realm.where(PostModel.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                postModels.deleteAllFromRealm();
            }
        });
    }

    public void setRating(PostModel postModel, boolean b) {
        realm.beginTransaction();
        if(postModel!=null)
            postModel.setFav(b);
        realm.commitTransaction();
    }

    public void setWatch(PostModel postModel, boolean b) {
        realm.beginTransaction();
        if(postModel!=null)
            postModel.setWatch(b);
        realm.commitTransaction();
    }

    public void setRating(PostModel postModel, String r) {
        realm.beginTransaction();
        if(postModel!=null)
            postModel.setRating(r);
        realm.commitTransaction();
    }

}