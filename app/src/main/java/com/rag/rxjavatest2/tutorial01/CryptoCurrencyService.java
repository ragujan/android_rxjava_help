package com.rag.rxjavatest2.tutorial01;


import com.rag.rxjavatest2.pojo.Crypto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CryptoCurrencyService {
    String BASE_URL = "https://api.cryptonator.com/api/full/";

    @GET("{coin}-usd")
    Observable<Crypto> getCoinData(@Path("coin") String coin);
}
