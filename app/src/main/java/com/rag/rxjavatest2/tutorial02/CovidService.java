package com.rag.rxjavatest2.tutorial02;

import com.rag.rxjavatest2.tutorial01.CountryStateResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CovidService {
    @GET("/statistics")
    public Observable<CountryStateResponse> getCountriesSet();
}
