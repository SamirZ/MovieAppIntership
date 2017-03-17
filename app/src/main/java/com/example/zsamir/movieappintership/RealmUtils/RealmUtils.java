package com.example.zsamir.movieappintership.RealmUtils;

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

    public ArrayList<Movie> readUserMoviesFromRealm(String type){

        realm.beginTransaction();

        ArrayList<Movie> movies  = new ArrayList<>(realm.where(Movie.class).equalTo("type",type).findAll());

        ArrayList<Movie> realmMovies = new ArrayList<>();

        for (Movie m:movies) {
            realmMovies.add(new Movie(m.getPosterPath(),m.getOverview(),m.getOrgReleaseDate(),m.getId(),m.getTitle(),m.getBackdropPath(),m.getVoteAverage(),m.allGenres,m.getType()));
        }

        realm.commitTransaction();

        return realmMovies;

    }

    public ArrayList<Movie> readActorMoviesFromRealm(int id){

        realm.beginTransaction();

        ArrayList<Movie> movies  = new ArrayList<>(realm.where(Movie.class).equalTo("type",String.valueOf(id)).findAll());

        ArrayList<Movie> realmMovies = new ArrayList<>();

        for (Movie m:movies) {
            realmMovies.add(new Movie(m.getPosterPath(),m.getOverview(),m.getOrgReleaseDate(),m.getId(),m.getTitle(),m.getBackdropPath(),m.getVoteAverage(),m.allGenres,m.getType()));
        }

        realm.commitTransaction();

        return realmMovies;

    }

    public ArrayList<Movie> readPopularMoviesFromRealm(){

        realm.beginTransaction();

        ArrayList<Movie> movies  = new ArrayList<>(realm.where(Movie.class).equalTo("type","POPULAR").findAll());

        ArrayList<Movie> realmMovies = new ArrayList<>();

        for (Movie m:movies) {
            realmMovies.add(new Movie(m.getPosterPath(),m.getOverview(),m.getOrgReleaseDate(),m.getId(),m.getTitle(),m.getBackdropPath(),m.getVoteAverage(),m.allGenres,m.getType()));
        }

        realm.commitTransaction();

        return realmMovies;

    }

    public ArrayList<Movie> readLatestMoviesFromRealm(){

        realm.beginTransaction();

        ArrayList<Movie> movies  = new ArrayList<>(realm.where(Movie.class).equalTo("type","LATEST").findAll());

        ArrayList<Movie> realmMovies = new ArrayList<>();

        for (Movie m:movies) {
            realmMovies.add(new Movie(m.getPosterPath(),m.getOverview(),m.getOrgReleaseDate(),m.getId(),m.getTitle(),m.getBackdropPath(),m.getVoteAverage(),m.allGenres,m.getType()));
        }

        realm.commitTransaction();

        return realmMovies;

    }

    public ArrayList<Movie> readHighestRatedMoviesFromRealm(){

        realm.beginTransaction();

        ArrayList<Movie> movies  = new ArrayList<>(realm.where(Movie.class).equalTo("type","HIGHEST").findAll());

        ArrayList<Movie> realmMovies = new ArrayList<>();

        for (Movie m:movies) {
            realmMovies.add(new Movie(m.getPosterPath(),m.getOverview(),m.getOrgReleaseDate(),m.getId(),m.getTitle(),m.getBackdropPath(),m.getVoteAverage(),m.allGenres,m.getType()));
        }

        realm.commitTransaction();

        return realmMovies;

    }

    public ArrayList<TVShow> readPopularTVShowsFromRealm() {
        realm.beginTransaction();

        ArrayList<TVShow> tvShows  = new ArrayList<>(realm.where(TVShow.class).equalTo("type","POPULAR").findAll());

        ArrayList<TVShow> realmTVShows = new ArrayList<>();

        for (TVShow t:tvShows) {
            realmTVShows.add(new TVShow(t.getPosterPath(),t.getPopularity(),t.getId(),t.getBackdropPath(),t.getVoteAverage(),t.getOverview(),t.getOrgFirstAirDate(),t.getName(),t.getRating(),t.type,t.allGenres));
        }

        realm.commitTransaction();

        return realmTVShows;
    }

    public ArrayList<TVShow> readAiringTodayTVShowsFromRealm() {
        realm.beginTransaction();

        ArrayList<TVShow> tvShows  = new ArrayList<>(realm.where(TVShow.class).equalTo("type","AIRING").findAll());

        ArrayList<TVShow> realmTVShows = new ArrayList<>();

        for (TVShow t:tvShows) {
            realmTVShows.add(new TVShow(t.getPosterPath(),t.getPopularity(),t.getId(),t.getBackdropPath(),t.getVoteAverage(),t.getOverview(),t.getOrgFirstAirDate(),t.getName(),t.getRating(),t.type,t.allGenres));
        }

        realm.commitTransaction();

        return realmTVShows;
    }

    public ArrayList<TVShow> readLatestTVShowsFromRealm() {
        realm.beginTransaction();

        ArrayList<TVShow> tvShows  = new ArrayList<>(realm.where(TVShow.class).equalTo("type","LATEST").findAll());

        ArrayList<TVShow> realmTVShows = new ArrayList<>();

        for (TVShow t:tvShows) {
            realmTVShows.add(new TVShow(t.getPosterPath(),t.getPopularity(),t.getId(),t.getBackdropPath(),t.getVoteAverage(),t.getOverview(),t.getOrgFirstAirDate(),t.getName(),t.getRating(),t.type,t.allGenres));
        }

        realm.commitTransaction();

        return realmTVShows;
    }

    public ArrayList<TVShow> readHighestRatedTVShowsFromRealm() {
        realm.beginTransaction();

        ArrayList<TVShow> tvShows  = new ArrayList<>(realm.where(TVShow.class).equalTo("type","HIGHEST").findAll());

        ArrayList<TVShow> realmTVShows = new ArrayList<>();

        for (TVShow t:tvShows) {
            realmTVShows.add(new TVShow(t.getPosterPath(),t.getPopularity(),t.getId(),t.getBackdropPath(),t.getVoteAverage(),t.getOverview(),t.getOrgFirstAirDate(),t.getName(),t.getRating(),t.type,t.allGenres));
        }

        realm.commitTransaction();

        return realmTVShows;
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
        RealmMovieDetails movieDetails = realm.createObject(RealmMovieDetails.class,id);
        realm.commitTransaction();
    }

    public void createRealmTVShowDetails(int id) {
        deleteTVShowDetails(id);
        realm.beginTransaction();
        RealmTVShowDetails tvDetails = realm.createObject(RealmTVShowDetails.class,id);
        realm.commitTransaction();
    }

    public void createRealmSeasonDetails(String id) {
        deleteSeasonDetails(id);
        realm.beginTransaction();
        RealmSeasonDetails seasonDetails = realm.createObject(RealmSeasonDetails.class,id);
        realm.commitTransaction();
    }

    public void createRealmActorDetails(int id,Cast c,Actor a) {
        deleteActor(a.getId());
        deleteCast(c.getId());
        deleteActorDetails(id);

        realm.beginTransaction();
        RealmActorDetails actorDetails = realm.createObject(RealmActorDetails.class,id);
        actorDetails.setActor(realm.copyToRealmOrUpdate(a));
        actorDetails.setCast(realm.copyToRealmOrUpdate(c));
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


    public void addRealmMovieDetailsActors(int id, ArrayList<Cast> actors) {
        realm.beginTransaction();
        RealmMovieDetails movieDetails = realm.where(RealmMovieDetails.class).equalTo("id",id).findFirst();
        for (Cast c:actors) {
            movieDetails.getActors().add(realm.copyToRealmOrUpdate(c));
        }
        realm.commitTransaction();
    }

    public void addRealmTVShowDetailsActors(int id, ArrayList<Cast> actors) {
        realm.beginTransaction();
        RealmTVShowDetails tvDetails = realm.where(RealmTVShowDetails.class).equalTo("id",id).findFirst();
        for (Cast c:actors) {
            tvDetails.getActors().add(realm.copyToRealmOrUpdate(c));
        }
        realm.commitTransaction();
    }

    public void addRealmMovieDetailsWriters(int id, ArrayList<Crew> writers) {

        realm.beginTransaction();
        RealmMovieDetails movieDetails = realm.where(RealmMovieDetails.class).equalTo("id",id).findFirst();
        for (Crew c:writers) {
            movieDetails.getWriters().add(realm.copyToRealmOrUpdate(c));
        }
        realm.commitTransaction();
    }

    public void addRealmTVShowDetailsWriters(int id, List<CreatedBy> writers) {

        realm.beginTransaction();
        RealmTVShowDetails tvDetails = realm.where(RealmTVShowDetails.class).equalTo("id",id).findFirst();
        for (CreatedBy c:writers) {
            tvDetails.getWriters().add(realm.copyToRealmOrUpdate(c));
        }
        realm.commitTransaction();
    }

    public void addRealmTVShowDetailsBackdrops(int id, ArrayList<Backdrop> backdrops) {
        realm.beginTransaction();
        RealmTVShowDetails tvDetails = realm.where(RealmTVShowDetails.class).equalTo("id",id).findFirst();
        for (Backdrop b:backdrops) {
            tvDetails.getBackdrops().add(realm.copyToRealmOrUpdate(b));
        }
        realm.commitTransaction();
    }

    public void addRealmMovieDetailsBackrops(int id, ArrayList<Backdrop> backdrops) {
        realm.beginTransaction();
        RealmMovieDetails movieDetails = realm.where(RealmMovieDetails.class).equalTo("id",id).findFirst();
        for (Backdrop b:backdrops) {
            movieDetails.getBackdrops().add(realm.copyToRealmOrUpdate(b));
        }
        realm.commitTransaction();
    }

    public void addRealmMovieDetailsReviews(int id, List<MovieReview> reviews) {
        realm.beginTransaction();
        RealmMovieDetails movieDetails = realm.where(RealmMovieDetails.class).equalTo("id",id).findFirst();
        for (MovieReview r:reviews) {
            movieDetails.getMovieReviews().add(realm.copyToRealmOrUpdate(r));
        }
        realm.commitTransaction();
    }

    public void addRealmTVShowDetailsSeasons(int id, List<Season> seasons) {
        realm.beginTransaction();
        RealmTVShowDetails tvDetails = realm.where(RealmTVShowDetails.class).equalTo("id",id).findFirst();
        for (Season s:seasons) {
            tvDetails.getSeasons().add(realm.copyToRealmOrUpdate(s));
        }
        realm.commitTransaction();
    }

    public void addRealmSeasonsDetailsEpisodes(String id, List<Episode> episodes) {
        realm.beginTransaction();
        RealmSeasonDetails seasonDetails = realm.where(RealmSeasonDetails.class).equalTo("id",id).findFirst();
        for (Episode s:episodes) {
            seasonDetails.getEpisodes().add(realm.copyToRealmOrUpdate(s));
        }
        realm.commitTransaction();
    }


    public void addNewsFeedItemsToRealm(ArrayList<FeedItem> feedItems) {

        for (FeedItem f:feedItems) {
            realm.beginTransaction();
            FeedItem i = realm.createObject(FeedItem.class);
            i.setAuthor(f.getAuthor());
            i.setLink(f.getLink());
            i.setDescription(f.getDescription());
            i.setTitle(f.getTitle());
            i.setPubDate(f.getPubDate());
            realm.commitTransaction();
        }

    }

    public void addMoviesToRealm(ArrayList<Movie> moviesList) {

        for ( Movie m : moviesList) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(m);
            realm.commitTransaction();
        }

    }

    public void addMoviesToRealm(List<Movie> moviesList) {

        for ( Movie m : moviesList) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(m);
            realm.commitTransaction();
        }

    }

    public void addTVShowsToRealm(ArrayList<TVShow> tvShowList) {

        for ( TVShow t : tvShowList) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(t);
            realm.commitTransaction();
        }
    }

    public void addTVShowsToRealm(List<TVShow> tvShowList) {

        for ( TVShow t : tvShowList) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(t);
            realm.commitTransaction();
        }
    }



    public void deleteAllNewsFeed(){
        final RealmResults<FeedItem> results = realm.where(FeedItem.class).findAll();
        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public void deleteAllPopularMovies(){
        final RealmResults<Movie> results = realm.where(Movie.class).equalTo("type","POPULAR").findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteAllLatestMovies(){
        final RealmResults<Movie> results = realm.where(Movie.class).equalTo("type","LATEST").findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteAllHighestRatedMovies(){
        final RealmResults<Movie> results = realm.where(Movie.class).equalTo("type","HIGHEST").findAll();
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

    public void deleteAllPopularTVShows() {
        final RealmResults<TVShow> results = realm.where(TVShow.class).equalTo("type","POPULAR").findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteAllAiringTodayTVShows() {
        final RealmResults<TVShow> results = realm.where(TVShow.class).equalTo("type","AIRING").findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteAllHighestRatedTVShows() {
        final RealmResults<TVShow> results = realm.where(TVShow.class).equalTo("type","HIGHEST").findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void deleteAllLatestTVShows() {
        final RealmResults<TVShow> results = realm.where(TVShow.class).equalTo("type","LATEST").findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
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

    public void deleteEpisodeDetails(int id) {
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

    public void createRealmEpisodeDetails(int id,int seasonNumber,int episodeNumber) {
        deleteEpisodeDetails(id);
        realm.beginTransaction();
        RealmEpisodeDetails episodeDetails = realm.createObject(RealmEpisodeDetails.class, id);
        episodeDetails.setSeasonNumber(seasonNumber);
        episodeDetails.setEpisodeNumber(episodeNumber);
        realm.commitTransaction();
    }

    public void addRealmEpisodeDetailsCast(int id, ArrayList<Cast> actors) {
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


    public RealmEpisodeDetails readEpisodeDetails(Integer id) {
        realm.beginTransaction();

        RealmEpisodeDetails result = realm.where(RealmEpisodeDetails.class)
                .equalTo("id",id)
                .findFirst();

        realm.commitTransaction();
        return result;
    }

    public void addRealmEpisodeDetailsData(Integer id,EpisodeDetails response) {
        realm.beginTransaction();
        RealmEpisodeDetails tvDetails = realm.where(RealmEpisodeDetails.class)
                .equalTo("id",id)
                .findFirst();

        tvDetails.setEpisodeDetails(realm.copyToRealmOrUpdate(response));

        realm.commitTransaction();
    }
}
