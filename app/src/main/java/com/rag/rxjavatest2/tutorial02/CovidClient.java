package com.rag.rxjavatest2.tutorial02;

import androidx.annotation.NonNull;

import com.rag.rxjavatest2.tutorial01.CountryStateResponse;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CovidClient {
    private CovidService covidService;

    public CovidClient() {
        covidService = new Retrofit.Builder().baseUrl("https://covid-193.p.rapidapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient())
                .build().create(CovidService.class);

    }
    private static CovidClient INSTANCE;

    public static CovidClient getInstance(){
        if(INSTANCE ==null){
            INSTANCE = new CovidClient();
        }
        return INSTANCE;
    }
    public Observable<CountryStateResponse> getCountriesStats(){
        return covidService.getCountriesSet();
    }

    private OkHttpClient getHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request();
                        Request newRequest = request.newBuilder()
                                .addHeader("x-rapidapi-host", "covid-193.p.rapidapi.com")
                                .addHeader("x-rapidapi-key", "ff63e42a9emshd0c9b93c8a3378bp1b89c9jsnf8b65c48fa5e")
                                .build();
                        return chain.proceed(newRequest);
                    }
                }).build();
    }

}
