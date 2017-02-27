package com.example.zsamir.movieappintership.Modules;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieDetails
{
    @SerializedName("production_countries")
    @Expose
    private List<ProductionCountry> productionCountries = null;

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }
}