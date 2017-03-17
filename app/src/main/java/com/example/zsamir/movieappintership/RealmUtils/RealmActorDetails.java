package com.example.zsamir.movieappintership.RealmUtils;

import com.example.zsamir.movieappintership.Modules.Actor;
import com.example.zsamir.movieappintership.Modules.Cast;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmActorDetails extends RealmObject {

    @PrimaryKey
    private int id;
    private Cast cast;
    private Actor actor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cast getCast() {
        return cast;
    }

    public void setCast(Cast cast) {
        this.cast = cast;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
